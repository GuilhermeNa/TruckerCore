package com.example.truckercore.business_admin.layers.domain.use_case.employee

import android.util.Log
import com.example.truckercore.core.error.DomainException
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.core.my_lib.classes.Cpf
import com.example.truckercore.core.my_lib.expressions.getOrThrow
import com.example.truckercore.core.my_lib.expressions.getTag
import com.example.truckercore.infra.security.PermissionService
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data_2.cache.SessionCache
import com.example.truckercore.layers.data_2.repository.interfaces.AdminRepository
import com.example.truckercore.layers.data_2.repository.interfaces.DriverRepository
import com.example.truckercore.layers.data_2.repository.interfaces.UserRepository
import com.example.truckercore.layers.domain.base.contracts.Employee

class RegisterEmployeeUseCase(
    private val userRepository: UserRepository,
    private val adminRepository: AdminRepository,
    private val driverRepository: DriverRepository
) {

    suspend operator fun invoke(employee: Employee): OperationOutcome {
        return try {
            // Early return if user
            // has no permission
            if (!hasPermission()) {
                return unauthorized()
            }

            // Check employee CPF
            // is already registered
            if (checkRegistered(employee.cpf)) {
                return alreadyRegistered(employee.cpf)
            }

            TODO("Talvez validar o funcionario antes de salvar")

            // Save employee on Database
            save(employee)

            OperationOutcome.Completed

        } catch (e: Exception) {
            failure(e)
        }
    }



    private suspend fun hasPermission(): Boolean {
        val actor = userRepository.completeFetch(SessionCache.userID).getOrThrow()
        return PermissionService.canHire(actor)
    }

    private fun unauthorized() = OperationOutcome.Failure(
        DomainException.UnauthorizedAccess(
            "User must be an Admin to hire an Employee. Role found: ${SessionCache.role}"
        )
    )

    private fun checkRegistered(cpf: Cpf): Boolean {
        val adminOutcome = adminRepository.fetch(cpf)
        if (adminOutcome is DataOutcome.Failure) {
            throw adminOutcome.exception
        }

        val driverOutcome = driverRepository.fetch(cpf)
        if (driverOutcome is DataOutcome.Failure) {
            throw driverOutcome.exception
        }

        return adminOutcome is DataOutcome.Success || driverOutcome is DataOutcome.Success
    }

    private fun alreadyRegistered(cpf: Cpf) = OperationOutcome.Failure(
        DomainException.CpfRegistered(
            "Trying to register an CPF($cpf) existent: ${SessionCache.role}"
        )
    )

    private suspend fun save(employee: Employee) {
        TODO("SALVAR USUÁRIO")
    }

    private fun failure(throwable: Throwable): OperationOutcome.Failure {
        Log.e(getTag, "An error occurred while registering an Employee", throwable)

        // Return when its an known AppException
        if (throwable is AppException) {
            return OperationOutcome.Failure(throwable)
        }

        // Build an AppException if error is unknown
        val exception = AppException(throwable.message, throwable)
        return OperationOutcome.Failure(exception)
    }

}