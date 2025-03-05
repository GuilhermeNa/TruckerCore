package com.example.truckercore.modules.person.shared.person_details

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.GetAdminUseCase
import com.example.truckercore.modules.person.employee.driver.use_cases.interfaces.GetDriverUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.enums.PersonCategory
import com.example.truckercore.shared.abstractions.Person
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.modules.file.entity.File
import com.example.truckercore.shared.modules.file.use_cases.interfaces.GetFileUseCase
import com.example.truckercore.shared.modules.personal_data.aggregations.PersonalDataWithFile
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.AggregatePersonalDataWithFilesUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf

internal class GetPersonWithDetailsUseCaseImpl(
    private val getAdmin: GetAdminUseCase,
    private val getDriver: GetDriverUseCase,
    private val getFile: GetFileUseCase,
    private val getPDataWF: AggregatePersonalDataWithFilesUseCase
) : GetPersonWithDetailsUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(user: User): Flow<Response<PersonWithDetails>> {
        val params = getQueryParamsForLoggedUser(user)

        return getPersonListFlow(user.personFLag, params).flatMapConcat { personResponse ->
            val person = when (personResponse) {
                is Response.Success -> personResponse.data.first()
                else -> return@flatMapConcat flowOf(Response.Empty)
            }
            combinePhotoAndPersonalDataWFFlow(user, person)
        }

    }

    private fun getQueryParamsForLoggedUser(user: User): QueryParameters {
        val userId = user.id ?: throw NullPointerException("User id is null.")
        return QueryParameters.create(user)
            .setQueries(QuerySettings(Field.USER_ID, QueryType.WHERE_EQUALS, userId))
            .build()
    }

    private fun getPersonListFlow(
        category: PersonCategory, params: QueryParameters
    ): Flow<Response<List<Person>>> = when (category) {
        PersonCategory.ADMIN -> getAdmin.execute(params)
        PersonCategory.DRIVER -> getDriver.execute(params)
    }

    private fun combinePhotoAndPersonalDataWFFlow(user: User, person: Person) = combine(
        getPhotoFlow(user, arrayListOf(person.id!!)),
        getPersonalDataWithFilesFlow(user, arrayListOf(person.id!!))
    ) { photoResponse, pDataWFResponse ->

        val photo = photoResponse.extractSinglePhotoFromList()
        val pDataWFList = pDataWFResponse.extractSinglePDataWFList()

        val result = PersonWithDetails(
            person = person,
            photo = photo,
            pDataWFSet = pDataWFList.toHashSet()
        )

        Response.Success(result)
    }

    private fun getPhotoFlow(user: User, personIds: List<String>): Flow<Response<List<File>>> {
        val queryParams = QueryParameters.create(user)
            .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_IN, personIds))
            .build()
        return getFile.execute(queryParams)
    }

    private fun getPersonalDataWithFilesFlow(
        user: User, personIds: List<String>
    ): Flow<Response<List<PersonalDataWithFile>>> {
        val queryParams = QueryParameters.create(user).setQueries(
            QuerySettings(Field.PARENT_ID, QueryType.WHERE_IN, personIds)
        ).build()
        return getPDataWF.execute(queryParams)
    }

    private fun Response<List<File>>.extractSinglePhotoFromList(): File? =
        if (this is Response.Success) this.data.firstOrNull()
        else null

    private fun Response<List<PersonalDataWithFile>>.extractSinglePDataWFList(): List<PersonalDataWithFile> =
        if (this is Response.Success) this.data
        else emptyList()

    //----------------------------------------------------------------------------------------------

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(
        params: DocumentParameters,
        category: PersonCategory
    ): Flow<Response<PersonWithDetails>> =
        getPersonFlow(category, params).flatMapConcat { personResponse ->
            val person = when (personResponse) {
                is Response.Success -> personResponse.data
                else -> return@flatMapConcat flowOf(Response.Empty)
            }
            combinePhotoAndPersonalDataWFFlow(params.user, person)
        }

    private fun getPersonFlow(
        category: PersonCategory, params: DocumentParameters
    ): Flow<Response<Person>> = when (category) {
        PersonCategory.ADMIN -> getAdmin.execute(params)
        PersonCategory.DRIVER -> getDriver.execute(params)
    }

    //----------------------------------------------------------------------------------------------

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(
        params: QueryParameters,
        category: PersonCategory
    ): Flow<Response<List<PersonWithDetails>>> =
        getPersonListFlow(category, params).flatMapConcat { personResponse ->
            val personList = when (personResponse) {
                is Response.Success -> personResponse.data
                else -> return@flatMapConcat flowOf(Response.Empty)
            }
            combinePhotoAndPersonalDataWFListFlow(params.user, personList)
        }

    private fun combinePhotoAndPersonalDataWFListFlow(user: User, personList: List<Person>) =
        combine(
            getPhotoFlow(user, personList.mapNotNull { it.id }),
            getPersonalDataWithFilesFlow(user, personList.mapNotNull { it.id })
        ) { photoResponse, pDataWFResponse ->

            val photos = if (photoResponse is Response.Success) photoResponse.data else emptyList()
            val pDataWFList =
                if (pDataWFResponse is Response.Success) pDataWFResponse.data else emptyList()

            val result = personList.map { person ->
                PersonWithDetails(
                    person = person,
                    photo = photos.firstOrNull { it.parentId == person.id },
                    pDataWFSet = pDataWFList.filter { it.parentId == person.id }.toHashSet()
                )

            }

            Response.Success(result)
        }

}