package com.example.truckercore.model.infrastructure.security.authentication.use_cases

import com.example.truckercore.model.infrastructure.database.firebase.repository.FirebaseAuthRepository
import com.example.truckercore.model.infrastructure.security.authentication.entity.EmailAuthCredential
import com.example.truckercore.model.infrastructure.security.authentication.entity.NewEmailResult
import com.example.truckercore.model.infrastructure.security.authentication.errors.NullFirebaseUserException
import com.example.truckercore.model.shared.utils.expressions.logError
import com.example.truckercore.model.shared.utils.other.EarlyExit
import com.example.truckercore.model.shared.utils.other.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll

internal class CreateUserAndVerifyEmailUseCaseImpl(
    private val authRepository: FirebaseAuthRepository
) : CreateUserAndVerifyEmailUseCase {

    private val newUserTaskManager = NewUserAuthTaskManager()
    private val updateNameTaskManager = UpdateNameTaskManager()
    private val sendEmailTaskManager = SendEmailTaskManager()
    private val fbUser get() = manager.getFirebaseUser()


    override suspend fun invoke(credential: EmailAuthCredential): NewEmailResult {
        return try {

            val earlyExit = createUser(credential)
            if (earlyExit) return manager.getResult()

            updateNameAndSendEmailAsync(credential)

            manager.getResult()

        } catch (e: Exception) {
            logError("An error ocurred while authenticating and verifying a new Email: $e.")
            manager.getResult()
        }
    }

    private suspend fun createUser(credential: EmailAuthCredential): EarlyExit {
        return authRepository.createUserWithEmail(credential.email, credential.password)
            .handleResponseAndConsume(
                success = { fbUser ->
                    manager.setTaskResult(UserCreated(fbUser))
                    false
                },
                empty = {
                    manager.setTaskResult(UserCreationEmpty)
                    true
                },
                error = { e ->
                    manager.setTaskResult(UserCreationFailed(e))
                    true
                }
            )
    }

    private suspend fun updateNameAndSendEmailAsync(credential: EmailAuthCredential): Unit =
        coroutineScope {
            val nameDef = async { updateName(credential) }
            val emailDef = async { sendEmail() }
            joinAll(nameDef, emailDef)
        }

    private suspend fun updateName(credential: EmailAuthCredential) {
        val request = userProfileChangeRequest { displayName = credential.name }
        authRepository.updateUserProfile(fbUser, request)
            .handleResult(
                success = { manager.setTaskResult(NameUpdated) },
                error = { e -> manager.setTaskResult(UpdateNameFailed(e)) }
            )
    }

    private suspend fun sendEmail() {
        authRepository.sendEmailVerification(fbUser)
            .handleResult(
                success = { manager.setTaskResult(EmailSent) },
                error = { e -> manager.setTaskResult(EmailFailed(e)) }
            )
    }

}

class SendEmailTaskManager: TaskManager<FirebaseUser>() {

    override fun setTaskResult(receivedTask: TaskResult) {
        TODO("Not yet implemented")
    }

}

class UpdateNameTaskManager {

    private var task: Task<Unit>? = null


}

class NewUserAuthTaskManager {

    private var task: Task<Unit>? = null


}

abstract class TaskManager<T> {

    private var _task: Task<T>? = null
    val task get() = _task

    abstract fun setTaskResult(receivedTask: TaskResult)

}

sealed class SendEmailTask: TaskResult {
    data object Success : SendEmailTask()
    data class Error(val exception: Exception) : SendEmailTask()
}

sealed interface TaskResult

private class MyClassManager {

    private var userTask: Task<FirebaseUser>? = null
    private var nameTask: Task<Unit>? = null
    private var emailTask: Task<Unit>? = null

    fun setTaskResult(entry: TaskEntry) {
        when (entry) {
            is UserCreated -> {
                userTask = Task(
                    result = entry.fbUser,
                    isSuccess = true
                )
            }

            is UserCreationEmpty -> {
                userTask = Task(
                    exception = NullFirebaseUserException(
                        "Firebase user was not found while authenticating an email."
                    )
                )
            }

            is UserCreationFailed -> {
                userTask = Task(exception = entry.exception)
            }

            is NameUpdated -> {
                nameTask = Task(isSuccess = true)
            }

            is UpdateNameFailed -> {
                nameTask = Task(exception = entry.exception)
            }

            is EmailSent -> {
                emailTask = Task(isSuccess = true)
            }

            is EmailFailed -> {
                emailTask = Task(exception = entry.exception)
            }

        }
    }

    fun getResult(): NewEmailResult = try {
        val errorSet = mutableSetOf<Exception>().apply {
            userTask?.exception?.let { add(it) }
            nameTask?.exception?.let { add(it) }
            emailTask?.exception?.let { add(it) }
        }

        NewEmailResult(
            firebaseUser = userTask?.result,
            userTaskSucceed = userTask?.isSuccess ?: false,
            nameTaskSucceed = nameTask?.isSuccess ?: false,
            emailTaskSucceed = emailTask?.isSuccess ?: false,
            errors = errorSet
        )

    } catch (e: Exception) {
        logError("An error ocurred while creating a EmailTask result: $e.")
        NewEmailResult(errors = setOf(e))

    } finally {
        clear()
    }

    fun getFirebaseUser() = userTask?.result ?: throw NullFirebaseUserException(
        "Firebase user is not defined in UseCase Manager while authenticating email."
    )

    private fun clear() {
        userTask = null
        nameTask = null
        emailTask = null
    }

}

sealed class CreateUserTask {
    data class Success(val fbUser: FirebaseUser) : CreateUserTask()
    data object Empty : CreateUserTask()
    data class Error(val exception: Exception) : CreateUserTask()
}

sealed class UpdateNameTask {
    data object Success : UpdateNameTask()
    data class Error(val exception: Exception) : UpdateNameTask()
}



/*
private typealias UserCreated = TaskEntry.CreateUser.Success
private typealias UserCreationFailed = TaskEntry.CreateUser.Error
private typealias UserCreationEmpty = TaskEntry.CreateUser.Empty

private typealias NameUpdated = TaskEntry.UpdateName.Success
private typealias UpdateNameFailed = TaskEntry.UpdateName.Error

private typealias EmailSent = TaskEntry.SendEmail.Success
private typealias EmailFailed = TaskEntry.SendEmail.Error

internal class CreateUserAndVerifyEmailUseCaseImpl(
    private val authRepository: FirebaseAuthRepository
) : CreateUserAndVerifyEmailUseCase {

    private val manager = MyClassManager()
    private val fbUser get() = manager.getFirebaseUser()

    override suspend fun invoke(credential: EmailAuthCredential): NewEmailResult {
        return try {

            val earlyExit = createUser(credential)
            if (earlyExit) return manager.getResult()

            updateNameAndSendEmailAsync(credential)

            manager.getResult()

        } catch (e: Exception) {
            logError("An error ocurred while authenticating and verifying a new Email: $e.")
            manager.getResult()
        }
    }

    private suspend fun createUser(credential: EmailAuthCredential): EarlyExit {
        return authRepository.createUserWithEmail(credential.email, credential.password)
            .handleResponseAndConsume(
                success = { fbUser ->
                    manager.setTaskResult(UserCreated(fbUser))
                    false
                },
                empty = {
                    manager.setTaskResult(UserCreationEmpty)
                    true
                },
                error = { e ->
                    manager.setTaskResult(UserCreationFailed(e))
                    true
                }
            )
    }

    private suspend fun updateNameAndSendEmailAsync(credential: EmailAuthCredential): Unit =
        coroutineScope {
            val nameDef = async { updateName(credential) }
            val emailDef = async { sendEmail() }
            joinAll(nameDef, emailDef)
        }

    private suspend fun updateName(credential: EmailAuthCredential) {
        val request = userProfileChangeRequest { displayName = credential.name }
        authRepository.updateUserProfile(fbUser, request)
            .handleResult(
                success = { manager.setTaskResult(NameUpdated) },
                error = { e -> manager.setTaskResult(UpdateNameFailed(e)) }
            )
    }

    private suspend fun sendEmail() {
        authRepository.sendEmailVerification(fbUser)
            .handleResult(
                success = { manager.setTaskResult(EmailSent) },
                error = { e -> manager.setTaskResult(EmailFailed(e)) }
            )
    }

}

private class MyClassManager {

    private var userTask: Task<FirebaseUser>? = null
    private var nameTask: Task<Unit>? = null
    private var emailTask: Task<Unit>? = null

    fun setTaskResult(entry: TaskEntry) {
        when (entry) {
            is UserCreated -> {
                userTask = Task(
                    result = entry.fbUser,
                    isSuccess = true
                )
            }

            is UserCreationEmpty -> {
                userTask = Task(
                    exception = NullFirebaseUserException(
                        "Firebase user was not found while authenticating an email."
                    )
                )
            }

            is UserCreationFailed -> {
                userTask = Task(exception = entry.exception)
            }

            is NameUpdated -> {
                nameTask = Task(isSuccess = true)
            }

            is UpdateNameFailed -> {
                nameTask = Task(exception = entry.exception)
            }

            is EmailSent -> {
                emailTask = Task(isSuccess = true)
            }

            is EmailFailed -> {
                emailTask = Task(exception = entry.exception)
            }

        }
    }

    fun getResult(): NewEmailResult = try {
        val errorSet = mutableSetOf<Exception>().apply {
            userTask?.exception?.let { add(it) }
            nameTask?.exception?.let { add(it) }
            emailTask?.exception?.let { add(it) }
        }

        NewEmailResult(
            firebaseUser = userTask?.result,
            userTaskSucceed = userTask?.isSuccess ?: false,
            nameTaskSucceed = nameTask?.isSuccess ?: false,
            emailTaskSucceed = emailTask?.isSuccess ?: false,
            errors = errorSet
        )

    } catch (e: Exception) {
        logError("An error ocurred while creating a EmailTask result: $e.")
        NewEmailResult(errors = setOf(e))

    } finally {
        clear()
    }

    fun getFirebaseUser() = userTask?.result ?: throw NullFirebaseUserException(
        "Firebase user is not defined in UseCase Manager while authenticating email."
    )

    private fun clear() {
        userTask = null
        nameTask = null
        emailTask = null
    }

}

private sealed class TaskEntry {

    sealed class CreateUser {
        data class Success(val fbUser: FirebaseUser) : TaskEntry()
        data object Empty : TaskEntry()
        data class Error(val exception: Exception) : TaskEntry()
    }

    sealed class UpdateName {
        data object Success : TaskEntry()
        data class Error(val exception: Exception) : TaskEntry()
    }

    sealed class SendEmail {
        data object Success : TaskEntry()
        data class Error(val exception: Exception) : TaskEntry()
    }

}
*/

