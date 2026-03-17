package com.example.truckercore.business_admin.layers.domain.use_case.session

import android.util.Log
import com.example.truckercore.core.error.DomainException
import com.example.truckercore.core.my_lib.expressions.isSuccess
import com.example.truckercore.core.my_lib.expressions.requireOrFailure
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.repository.auth.AuthenticationRepository
import com.example.truckercore.layers.data_2.repository.interfaces.AccessRepository
import com.example.truckercore.layers.data_2.repository.interfaces.AdminRepository
import com.example.truckercore.layers.data_2.repository.interfaces.DriverRepository
import com.example.truckercore.layers.data_2.repository.interfaces.UserRepository
import com.example.truckercore.layers.domain.base.contracts.Employee
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.session.Session
import com.example.truckercore.layers.domain.model.session.SessionFactory
import com.example.truckercore.layers.domain.use_case._base.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ObserveSessionUseCase(
    private val authRepository: AuthenticationRepository,
    private val userRepository: UserRepository,
    private val accessRepository: AccessRepository,
    private val adminRepository: AdminRepository,
    private val driverRepository: DriverRepository
) : UseCase() {

    // Melhorar funções.
    // Aplicar mapeadores.
    // Remover casts.

    override val classTag: String = "ObserveSessionUseCase"

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<DataOutcome<Session>> {
        // Step 1: Retrieve the current user's UID.
        // If UID is missing or invalid, convert it to failure and return immediately.
        val uidOutcome = authRepository.getUid().requireOrFailure(EMPTY_UID)
        if (uidOutcome is DataOutcome.Failure) return flowOf(uidOutcome)
        val uid = (uidOutcome as DataOutcome.Success).data

        // Step 2: Start observing the user data using the UID.
        // If is missing, convert Empty to Failure using map.
        return userRepository.observe(uid)
            .map { it.requireOrFailure(EMPTY_USER) }
            .flatMapLatest { userOutcome ->
                // Step 3: React to user updates.
                // If is missing or invalid, return immediately.
                if (userOutcome is DataOutcome.Failure) return@flatMapLatest flowOf(userOutcome)
                val userDraft = (userOutcome as DataOutcome.Success).data

                // Step 4: Combine Access and Employee streams for this user.
                // If is missing, convert Empty to Failure using map.
                combine(
                    accessRepository.observe(userDraft.id)
                        .map { it.requireOrFailure(EMPTY_ACCESS) },
                    combineAdminAndDriverFlows(userDraft.id).map {
                        it.requireOrFailure(
                            EMPTY_EMPLOYEE
                        )
                    }
                ) { accessOutcome, employeeOutcome ->
                    // Step 5: Validate combined results.
                    // Propagate any failure or Session.
                    when {
                        accessOutcome is DataOutcome.Failure -> accessOutcome
                        employeeOutcome is DataOutcome.Failure -> employeeOutcome
                        else -> DataOutcome.Success(
                            SessionFactory.toEntity(
                                userDraft,
                                (accessOutcome as DataOutcome.Success).data,
                                (employeeOutcome as DataOutcome.Success).data
                            )
                        )
                    }
                }
            }.catch { throwable ->
                Log.e(classTag, MAIN_FLOW_ERROR, throwable)
                emit(throwable.toOutcome())
            }
    }

    private fun combineAdminAndDriverFlows(userId: UserID): Flow<DataOutcome<Employee>> =
        combine(
            adminRepository.observe(userId),
            driverRepository.observe(userId)
        ) { adminOutcome, driverOutcome ->
            when {
                adminOutcome.isSuccess() && driverOutcome.isSuccess() ->
                    DataOutcome.Failure(DomainException.RuleViolation(MULTIPLE_EMPLOYEES))

                adminOutcome is DataOutcome.Success -> adminOutcome

                driverOutcome is DataOutcome.Success -> driverOutcome

                else -> DataOutcome.Empty
            }
        }

    private companion object {
        private const val MAIN_FLOW_ERROR =
            "Error occurred while tried to observe session"
        private const val MULTIPLE_EMPLOYEES =
            "Only one employee is expected for a user. Two employees were found."
        private const val EMPTY_ACCESS =
            "No Access was found for the given User."
        private const val EMPTY_EMPLOYEE =
            "No Employee was found for the given User."
        private const val EMPTY_USER =
            "No User was found for the given UID."
        private const val EMPTY_UID =
            "UID should be valid at this point in the app, but no UID was found."
    }

}