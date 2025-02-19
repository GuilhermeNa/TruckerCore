package com.example.truckercore.infrastructure.security.authentication.repository

import com.example.truckercore.infrastructure.database.firebase.repository.FirebaseRepository
import com.example.truckercore.infrastructure.security.authentication.entity.UserRegistration
import com.example.truckercore.infrastructure.security.authentication.use_cases.CheckEmailAlreadyRegisteredUseCase
import com.example.truckercore.shared.utils.sealeds.Response

internal class AuthenticationRepositoryImpl(
    private val firebaseRepository: FirebaseRepository,
    private val checkEmailExistence: CheckEmailAlreadyRegisteredUseCase,

): AuthenticationRepository {

    override fun registerSingleUser(userReg: UserRegistration): Response<Unit> {
        TODO("Not yet implemented")
    }

    private fun checkEmailExistence(email: String) {
        checkEmailExistence.execute(email)
    }

}