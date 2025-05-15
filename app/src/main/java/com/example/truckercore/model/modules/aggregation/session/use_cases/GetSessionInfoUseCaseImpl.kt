package com.example.truckercore.model.modules.aggregation.session.use_cases

import com.example.truckercore._utils.classes.AppResponse
import com.example.truckercore._utils.expressions.getOrNull
import com.example.truckercore._utils.expressions.getOrReturn
import com.example.truckercore.model.errors.technical.TechnicalException
import com.example.truckercore.model.infrastructure.security.data.enums.Role
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
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetSessionInfoUseCaseImpl(
    private val getUserUseCase: GetUserUserCase,
    private val getCompanyUseCase: GetCompanyUseCase,
    private val getAdminUseCase: GetAdminUseCase,
    private val getAutonomousUseCase: GetAutonomousUseCase,
    private val getDriverUseCase: GetDriverUseCase
) : GetSessionInfoUseCase {

    override suspend fun invoke(uid: UID): AppResponse<Session> = coroutineScope {
        // Get User
        val user = getUser(uid).getOrReturn { unsuccessful -> return@coroutineScope unsuccessful }

        // Search Async
        val asyncResponses = getCompanyAndEligibleAsync(user)
        asyncResponses.awaitAndMap { companyResponse, eligibleResponse ->
            val company = companyResponse.getOrNull()
            val eligible = eligibleResponse.getOrNull()

            AppResponse.Success(
                Session(
                    uid = uid,
                    user = user,
                    company = company,
                    person = eligible
                )
            )
        }
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

            Role.MANAGER -> throw TechnicalException.NotImplemented(
                "MANAGER role handling not implemented"
            )
        }
    }

    private data class AsyncResponses(
        val companyDef: Deferred<AppResponse<Company>>,
        val eligibleDef: Deferred<AppResponse<UserEligible<*>>>
    ) {

        suspend inline fun awaitAndMap(
            block: (AppResponse<Company>, AppResponse<UserEligible<*>>) -> AppResponse<Session>
        ): AppResponse<Session> {
            val companyResponse = companyDef.await()
            val eligibleResponse = eligibleDef.await()
            return block(companyResponse, eligibleResponse)
        }

    }

}

