package com.example.truckercore.business_admin.layers.domain.use_case.session

import com.example.truckercore.core.my_lib.expressions.allEmpty
import com.example.truckercore.core.my_lib.expressions.allSuccess
import com.example.truckercore.core.my_lib.expressions.firstSuccess
import com.example.truckercore.core.my_lib.expressions.fold
import com.example.truckercore.core.my_lib.expressions.required
import com.example.truckercore.core.my_lib.expressions.zip
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.repository.auth.AuthenticationRepository
import com.example.truckercore.layers.data_2.repository.interfaces.AccessRepository
import com.example.truckercore.layers.data_2.repository.interfaces.AdminRepository
import com.example.truckercore.layers.data_2.repository.interfaces.DriverRepository
import com.example.truckercore.layers.data_2.repository.interfaces.UserRepository
import com.example.truckercore.layers.domain.base.contracts.Employee
import com.example.truckercore.layers.domain.base.ids.UID
import com.example.truckercore.layers.domain.base.ids.UserID
import com.example.truckercore.layers.domain.model.session.Session
import com.example.truckercore.layers.domain.model.session.SessionFactory
import com.example.truckercore.layers.domain.model.user.UserDraft
import com.example.truckercore.layers.domain.use_case._base.UseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

class ObserveSessionUseCase(
    private val authRepository: AuthenticationRepository,
    private val userRepository: UserRepository,
    private val accessRepository: AccessRepository,
    private val adminRepository: AdminRepository,
    private val driverRepository: DriverRepository
) : UseCase() {

    operator fun invoke(): Flow<DataOutcome<Session>> =
        authRepository.getUid().fold(
            onSuccess = ::observeUserFlow,
            onFailure = ::toFailureOutcomeFlow,
            onEmpty = { ruleViolatedOutcomeFlow(UID_NOT_FOUND) }
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeUserFlow(uid: UID): Flow<DataOutcome<Session>> =
        userRepository.observe(uid).flatMapLatest { userOutcome ->
            userOutcome.fold(
                onSuccess = ::combineAccessAndEmployeesFlow,
                onFailure = ::toFailureOutcomeFlow,
                onEmpty = { ruleViolatedOutcomeFlow(USER_NOT_FOUND) }
            )
        }.catch { throwable ->
            emit(failureOutcome(throwable, UNKNOWN_ERROR))
        }

    private fun combineAccessAndEmployeesFlow(it: UserDraft): Flow<DataOutcome<Session>> =
        combine(
            accessRepository.observe(it.id),
            combineAdminAndDriverFlows(it.id)
        ) { accessOutcome, employeeOutcome ->
            zip(accessOutcome, employeeOutcome) { a, e ->
                SessionFactory.toEntity(it, a, e)
            }.required(ACCESS_XOR_EMPLOYEE_NOT_FOUND)
        }

    private fun combineAdminAndDriverFlows(userId: UserID): Flow<DataOutcome<Employee>> =
        combine(
            adminRepository.observe(userId),
            driverRepository.observe(userId)
        ) { aOutcome, dOutcome ->
            when {
                allSuccess(aOutcome, dOutcome) -> ruleViolatedOutcome(MULTIPLE_EMPLOYEES)
                allEmpty(aOutcome, dOutcome) -> ruleViolatedOutcome(EMPLOYEE_NOT_FOUND)
                else -> firstSuccess(aOutcome, dOutcome) { it }
            }
        }

    private companion object {
        private const val UID_NOT_FOUND = "UID is missing; unable to retrieve session."

        private const val USER_NOT_FOUND = "User is missing; unable to retrieve session."

        private const val EMPLOYEE_NOT_FOUND = "Employee is missing; unable to retrieve session."

        private const val ACCESS_XOR_EMPLOYEE_NOT_FOUND =
            "Access and/or Employee are missing; unable to retrieve session."

        private const val UNKNOWN_ERROR = "An Unknown error occurred while tried to observe session"

        private const val MULTIPLE_EMPLOYEES =
            "Only one employee is expected for a user. Multiple employees were found."
    }

}