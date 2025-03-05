package com.example.truckercore.modules.user.use_cases.implementations

import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.GetAdminUseCase
import com.example.truckercore.modules.person.employee.driver.use_cases.interfaces.GetDriverUseCase
import com.example.truckercore.modules.user.aggregations.UserWithPerson
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.enums.PersonCategory
import com.example.truckercore.modules.user.use_cases.interfaces.AggregateUserWithPersonUseCase
import com.example.truckercore.modules.user.use_cases.interfaces.GetUserUseCase
import com.example.truckercore.shared.abstractions.Person
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

internal class AggregateUserWithPersonUseCaseImpl(
    private val getUser: GetUserUseCase,
    private val getDriver: GetDriverUseCase,
    private val getAdminUseCase: GetAdminUseCase
) : AggregateUserWithPersonUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(userId: String, shouldStream: Boolean): Flow<Response<UserWithPerson>> =
        getUser.execute(userId, shouldStream).flatMapConcat { response ->
            if (response !is Response.Success) return@flatMapConcat flowOf(Response.Empty)

            val user = response.data
            val docParams = getDocumentParams(user, shouldStream)

            getPersonFlow(docParams).map { personResult ->
                if (personResult !is Response.Success) return@map Response.Empty
                val person = personResult.data
                getResult(docParams.user, person)
            }
        }

    private fun getPersonFlow(docParams: DocumentParameters): Flow<Response<Person>> {
        val personFlag = docParams.user.personFLag
        return when (personFlag) {
            PersonCategory.DRIVER -> getDriver.execute(docParams)
            PersonCategory.ADMIN -> getAdminUseCase.execute(docParams)
        }
    }

    private fun getDocumentParams(user: User, shouldStream: Boolean): DocumentParameters {
        val id = user.id ?: throw NullPointerException("Null User id while retrieving data.")
        return DocumentParameters.create(user)
            .setStream(shouldStream)
            .setId(id)
            .build()
    }

    private fun getResult(user: User, person: Person): Response<UserWithPerson> =
        Response.Success(UserWithPerson(user, person))

}