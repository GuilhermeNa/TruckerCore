package com.example.truckercore.infrastructure.security.authentication.use_cases

import com.example.truckercore.infrastructure.security.authentication.entity.LoggedUserDetails
import com.example.truckercore.modules.person.shared.person_details.GetPersonWithDetailsUseCase
import com.example.truckercore.modules.person.shared.person_details.PersonWithDetails
import com.example.truckercore.modules.user.use_cases.interfaces.GetUserUseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

internal class GetLoggedUserDetailsUseCaseImpl(
    private val getUser: GetUserUseCase,
    private val getPersonDetails: GetPersonWithDetailsUseCase
) : GetLoggedUserDetailsUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(firebaseUid: String): Flow<Response<LoggedUserDetails>> =
        getUser.execute(firebaseUid).flatMapConcat { userResponse ->

            val user = when (userResponse) {
                is Response.Success -> userResponse.data
                else -> return@flatMapConcat flowOf(Response.Empty)
            }

            getPersonDetails.execute(user).map { personWDResponse ->
                val personWD = personWDResponse.extractPersonDetails()
                val loggedUser = LoggedUserDetails(user = user, personWD = personWD)
                Response.Success(loggedUser)
            }

        }

    private fun Response<PersonWithDetails>.extractPersonDetails(): PersonWithDetails =
        if (this is Response.Success) data
        else throw ObjectNotFoundException("Person Data was not found.")

}