package com.example.truckercore.model.infrastructure.security.authentication.use_cases.implementations

import com.example.truckercore.model.configs.app_constants.Field
import com.example.truckercore.model.infrastructure.security.authentication.entity.SessionInfo
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.GetSessionInfoUseCase
import com.example.truckercore.model.modules.business_central.entity.BusinessCentral
import com.example.truckercore.model.modules.business_central.use_cases.interfaces.GetBusinessCentralUseCase
import com.example.truckercore.model.modules.person.shared.person_details.GetPersonWithDetailsUseCase
import com.example.truckercore.model.modules.person.shared.person_details.PersonWithDetails
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.modules.user.use_cases.interfaces.GetUserUseCase
import com.example.truckercore.model.modules.vip.entity.Vip
import com.example.truckercore.model.modules.vip.use_cases.interfaces.GetVipUseCase
import com.example.truckercore.model.shared.enums.QueryType
import com.example.truckercore.model.shared.errors.ObjectNotFoundException
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.parameters.QuerySettings
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf

internal class GetSessionInfoUseCaseImpl(
    private val getUser: GetUserUseCase,
    private val getPersonDetails: GetPersonWithDetailsUseCase,
    private val getVip: GetVipUseCase,
    private val getCentral: GetBusinessCentralUseCase
) : GetSessionInfoUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(firebaseUid: String): Flow<AppResponse<SessionInfo>> =
        getUser.execute(firebaseUid).flatMapConcat { userResponse ->

            val user = when (userResponse) {
                is AppResponse.Success -> userResponse.data
                else -> return@flatMapConcat flowOf(AppResponse.Empty)
            }

            combineSessionInfoFlows(user)

        }

    private fun combineSessionInfoFlows(user: User): Flow<Response<SessionInfo>> =
        combine(
            getVip.execute(getQueryParams(user)),
            getPersonDetails.execute(user),
            getCentral.execute(getDocumentParams(user))
        ) { vipResponse, personResponse, centralResponse ->
            val sessionInfo = SessionInfo(
                user = user,
                central = centralResponse.extractBusinessCentral(),
                personWD = personResponse.extractPersonDetails(),
                vips = vipResponse.extractVipList(),
            )
            Response.Success(sessionInfo)
        }

    private fun getDocumentParams(user: User): DocumentParameters {
        val centralId = user.businessCentralId
        return DocumentParameters.create(user).setId(centralId).build()
    }

    private fun getQueryParams(user: User): QueryParameters =
        QueryParameters.create(user)
            .setQueries(
                QuerySettings(
                    field = Field.USER_ID,
                    type = QueryType.WHERE_EQUALS,
                    value = user.id!!
                )
            ).build()

    private fun Response<List<Vip>>.extractVipList(): List<Vip> =
        if (this is Response.Success) data
        else emptyList()

    private fun Response<PersonWithDetails>.extractPersonDetails(): PersonWithDetails =
        if (this is Response.Success) data
        else throw ObjectNotFoundException("Person Data was not found.")

    private fun Response<BusinessCentral>.extractBusinessCentral(): BusinessCentral =
        if (this is Response.Success) data
        else throw ObjectNotFoundException("Business Central was not found.")

}