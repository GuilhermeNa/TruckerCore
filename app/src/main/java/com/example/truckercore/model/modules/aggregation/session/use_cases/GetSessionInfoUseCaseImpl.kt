package com.example.truckercore.model.modules.aggregation.session.use_cases

import com.example.truckercore._utils.classes.AppResponse
import com.example.truckercore._utils.classes.AppResult
import com.example.truckercore._utils.expressions.extractError
import com.example.truckercore._utils.expressions.getName
import com.example.truckercore._utils.expressions.getOrElse
import com.example.truckercore.model.errors.technical.TechnicalException
import com.example.truckercore.model.infrastructure.security.data.enums.Role
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.model.modules.aggregation.session.data.Session
import com.example.truckercore.model.modules.authentication.data.UID
import com.example.truckercore.model.modules.company.data.Company
import com.example.truckercore.model.modules.company.data.CompanyID
import com.example.truckercore.model.modules.company.specification.CompanySpec
import com.example.truckercore.model.modules.company.use_cases.GetCompanyUseCase
import com.example.truckercore.model.modules.employee.admin.specification.AdminSpec
import com.example.truckercore.model.modules.employee.admin.use_cases.GetAdminUseCase
import com.example.truckercore.model.modules.employee.autonomous.specification.AutonomousSpec
import com.example.truckercore.model.modules.employee.autonomous.use_cases.GetAutonomousUseCase
import com.example.truckercore.model.modules.employee.driver.specification.DriverSpec
import com.example.truckercore.model.modules.employee.driver.use_cases.GetDriverUseCase
import com.example.truckercore.model.modules.user._contracts.UserEligible
import com.example.truckercore.model.modules.user.data.User
import com.example.truckercore.model.modules.user.data.UserID
import com.example.truckercore.model.modules.user.specification.UserSpec
import com.example.truckercore.model.modules.user.use_cases.GetUserUserCase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetSessionInfoUseCaseImpl(
    private val getUserUseCase: GetUserUserCase,
    private val getCompanyUseCase: GetCompanyUseCase,
    private val getAdminUseCase: GetAdminUseCase,
    private val getAutonomousUseCase: GetAutonomousUseCase,
    private val getDriverUseCase: GetDriverUseCase,
    private val errorHandler: GetSessionInfoErrorHandler
) : GetSessionInfoUseCase {

    override suspend fun invoke(uid: UID): AppResult<Session> = try {
        // Get User
        val user = getUser(uid).getOrElse {
            errorHandler.logError(USER_ERROR_MSG, uid, it)
            return@invoke AppResult.Error(it.extractError())
        }

        // Search Async
        getCompanyAndEligibleAsync(user).awaitAndMap { companyResponse, eligibleResponse ->
            val company = companyResponse.getOrElse {
                errorHandler.logError(COMPANY_ERROR_MSG, uid, it)
                return@invoke AppResult.Error(it.extractError())
            }
            val eligible = eligibleResponse.getOrElse {
                errorHandler.logError(ELIGIBLE_ERROR_MSG, uid, it)
                return@invoke AppResult.Error(it.extractError())
            }

            AppLogger.d(getName(), SUCCESS_MSG)
            AppResult.Success(
                Session(
                    uid = uid,
                    user = user,
                    company = company,
                    person = eligible
                )
            )
        }

    } catch (e: Exception) {
        AppResult.Error(errorHandler(e))
    }

    private suspend fun getUser(uid: UID): AppResponse<User> {
        val spec = UserSpec(uid = uid)
        return getUserUseCase(spec)
    }

    private suspend fun getCompanyAndEligibleAsync(user: User) = coroutineScope {
        AsyncResponses(
            companyDef = async { getCompany(user.companyId) },
            eligibleDef = async { getEligible(user.id, user.profileRole) }
        )
    }

    private suspend fun getCompany(companyId: CompanyID): AppResponse<Company> {
        val spec = CompanySpec(entityId = companyId)
        return getCompanyUseCase(spec)
    }

    private suspend fun getEligible(userId: UserID, role: Role): AppResponse<UserEligible<*>> {
        return when (role) {
            Role.AUTONOMOUS -> {
                val spec = AutonomousSpec(userId = userId)
                getAutonomousUseCase(spec)
            }

            Role.ADMIN -> {
                val spec = AdminSpec(userId = userId)
                getAdminUseCase(spec)
            }

            Role.DRIVER -> {
                val spec = DriverSpec(userId = userId)
                getDriverUseCase(spec)
            }

            Role.MANAGER -> AppResponse.Error(
                TechnicalException.NotImplemented(NOT_IMPLEMENTED_MSG)
            )
        }
    }

    companion object {
        private const val USER_ERROR_MSG = "Failed to load session: user not found."
        private const val ELIGIBLE_ERROR_MSG =
            "Failed to load session: eligibility information not found."
        private const val COMPANY_ERROR_MSG = "Failed to load session: company data not found."
        private const val SUCCESS_MSG = "Session Loaded."
        private const val NOT_IMPLEMENTED_MSG = "MANAGER role handling not implemented"
    }

}