package com.example.truckercore.model.infrastructure.security.authentication.use_cases

import com.example.truckercore.model.configs.app_constants.Field
import com.example.truckercore.model.infrastructure.security.authentication.entity.LoggedUser
import com.example.truckercore.model.modules.person.shared.person_details.GetPersonWithDetailsUseCase
import com.example.truckercore.model.modules.person.shared.person_details.PersonWithDetails
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.modules.user.use_cases.interfaces.GetUserUseCase
import com.example.truckercore.model.modules.vip.entity.Vip
import com.example.truckercore.model.modules.vip.use_cases.interfaces.GetVipUseCase
import com.example.truckercore.model.shared.enums.QueryType
import com.example.truckercore.model.shared.errors.ObjectNotFoundException
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.parameters.QuerySettings
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf

internal class GetLoggedUserUseCaseImpl(
    private val getUser: GetUserUseCase,
    private val getPersonDetails: GetPersonWithDetailsUseCase,
    private val getVip: GetVipUseCase
) : GetLoggedUserUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(firebaseUid: String): Flow<Response<LoggedUser>> =
        getUser.execute(firebaseUid).flatMapConcat { userResponse ->

            val user = when (userResponse) {
                is Response.Success -> userResponse.data
                else -> return@flatMapConcat flowOf(Response.Empty)
            }

            combineVipAndPersonFlow(user)

        }

    private fun combineVipAndPersonFlow(user: User): Flow<Response<LoggedUser>> =
        combine(
            getVip.execute(getQueryParams(user)),
            getPersonDetails.execute(user)
        ) { vipResponse, personResponse ->
            val loggedUser = LoggedUser(
                user = user,
                personWD = personResponse.extractPersonDetails(),
                vips = vipResponse.extractVipList()
            )
            Response.Success(loggedUser)
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

}