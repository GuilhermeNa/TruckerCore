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
import com.example.truckercore.model.shared.utils.sealeds.getOrElse
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetSessionInfoUseCaseImpl(
    private val getUserUseCase: GetUserUserCase,
    private val getCompanyUseCase: GetCompanyUseCase,
    private val getAdminUseCase: GetAdminUseCase,
    private val getAutonomousUseCase: GetAutonomousUseCase
) : GetSessionInfoUseCase {

    override suspend fun invoke(uid: UID): AppResponse<Session> {
        return coroutineScope {
            val user = getUser(uid).getOrElse(
                onSuccess = { data -> data },
                orElse = { response -> return@coroutineScope response }
            )

            val companyDef = async { getCompanyAsync(user.companyId) }

            val eligibleDef = async { getEligibleAsync(user.id, user.getRole()) }

            val company = companyDef.await().getOrElse(
                onSuccess = { data -> data },
                orElse = { response -> return@coroutineScope response }
            )
            val eligible = eligibleDef.await().getOrElse(
                onSuccess = { data -> data },
                orElse = { response -> return@coroutineScope response }
            )

            val response = Session(
                uid = uid,
                user = user,
                company = company,
                person = eligible
            )

            return@coroutineScope AppResponse.Success(response)
        }
    }

    private suspend fun getUser(uid: UID): AppResponse<User> {
        val spec = UserSpec(uid = uid)
        return getUserUseCase(spec)
    }

    private suspend fun getCompanyAsync(companyId: CompanyID): AppResponse<Company> {
        val spec = CompanySpec(entityId = companyId)
        return getCompanyUseCase(spec)
    }

    private suspend fun getEligibleAsync(userId: UserID, role: Role): AppResponse<UserEligible> {
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

}