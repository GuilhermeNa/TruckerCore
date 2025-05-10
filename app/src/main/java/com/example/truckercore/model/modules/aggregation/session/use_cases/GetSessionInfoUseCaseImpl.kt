package com.example.truckercore.model.modules.aggregation.session.use_cases

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
import com.example.truckercore.model.modules.user._contracts.UserEligible
import com.example.truckercore.model.modules.user.data.User
import com.example.truckercore.model.modules.user.data.UserID
import com.example.truckercore.model.modules.user.specification.UserSpec
import com.example.truckercore.model.modules.user.use_cases.GetUserUserCase
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import com.example.truckercore.model.shared.utils.sealeds.getOrReturn
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetSessionInfoUseCaseImpl(
    private val getUserUseCase: GetUserUserCase,
    private val getCompanyUseCase: GetCompanyUseCase,
    private val getAdminUseCase: GetAdminUseCase,
    private val getAutonomousUseCase: GetAutonomousUseCase
) : GetSessionInfoUseCase {

    override suspend fun invoke(uid: UID): AppResponse<Session> = coroutineScope {
        // Get User
        val user = getUser(uid).getOrReturn { return@coroutineScope it }

        // Search Async
        val asyncResponses = getCompanyAndEligibleAsync(user)
        asyncResponses.awaitAndMap { companyResponse, eligibleResponse ->
            val company = companyResponse.getOrReturn { return@coroutineScope it }
            val eligible = eligibleResponse.getOrReturn { return@coroutineScope it }

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

    private suspend fun getCompanyAndEligibleAsync(user: User) = coroutineScope {
        AsyncResponses(
            companyDef = async { getCompany(user.companyId) },
            eligibleDef = async { getEligible(user.id, user.profileRole) }
        )
    }

    private suspend fun getUser(uid: UID): AppResponse<User> {
        val spec = UserSpec(uid = uid)
        return getUserUseCase(spec)
    }

    private suspend fun getCompany(companyId: CompanyID): AppResponse<Company> {
        val spec = CompanySpec(entityId = companyId)
        return getCompanyUseCase(spec)
    }

    private suspend fun getEligible(userId: UserID, role: Role): AppResponse<UserEligible> {
        return when (role) {
            Role.AUTONOMOUS -> getAutonomous(userId)
            Role.ADMIN -> getAdmin(userId)
            Role.DRIVER -> TODO()
            Role.MANAGER -> TODO()
        }
    }

    private suspend fun getAdmin(userId: UserID): AppResponse<UserEligible> {
        val spec = AdminSpec(userId = userId)
        return getAdminUseCase(spec)
    }

    private suspend fun getAutonomous(userId: UserID): AppResponse<UserEligible> {
        val spec = AutonomousSpec(userId = userId)
        return getAutonomousUseCase(spec)
    }

    data class AsyncResponses(
        val companyDef: Deferred<AppResponse<Company>>,
        val eligibleDef: Deferred<AppResponse<UserEligible>>
    ) {

        suspend inline fun awaitAndMap(
            block: (AppResponse<Company>, AppResponse<UserEligible>) -> AppResponse<Session>
        ): AppResponse<Session> {
            val companyResponse = companyDef.await()
            val eligibleResponse = eligibleDef.await()
            return block(companyResponse, eligibleResponse)
        }

    }

}

