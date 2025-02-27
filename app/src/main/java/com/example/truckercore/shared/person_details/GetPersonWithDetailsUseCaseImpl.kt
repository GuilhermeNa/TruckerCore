package com.example.truckercore.shared.person_details

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.GetAdminUseCase
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.GetDriverUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.enums.PersonCategory
import com.example.truckercore.shared.abstractions.Person
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.modules.file.entity.File
import com.example.truckercore.shared.modules.file.use_cases.interfaces.GetFileUseCase
import com.example.truckercore.shared.modules.personal_data.aggregations.PersonalDataWithFile
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.AggregatePersonalDataWithFilesUseCase
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
    override fun execute(user: User): Flow<Response<PersonWithDetails>> =
        getPersonFlow(user).flatMapConcat { personResponse ->
            if (personResponse !is Response.Success) return@flatMapConcat flowOf(Response.Empty)
            val person = personResponse.extractPerson()

            combine(
                getPhotoFlow(user, person.id!!),
                getPersonalDataWithFilesFlow(user, person.id!!)
            ) { photoResponse, pDataWFResponse ->
                val photo = photoResponse.extractPhoto()
                val pDataWFList = pDataWFResponse.extractPDataWFList()

                val result = PersonWithDetails(
                    person = person,
                    photo = photo,
                    pDataWFSet = pDataWFList.toHashSet()
                )
                Response.Success(result)
            }

        }

    private fun getPersonFlow(user: User): Flow<Response<List<Person>>> {
        val userId = user.id ?: throw NullPointerException("User id is null.")

        val queryParams = QueryParameters.create(user)
            .setQueries(QuerySettings(Field.USER_ID, QueryType.WHERE_EQUALS, userId))
            .build()

        return when (user.personFLag) {
            PersonCategory.ADMIN -> getAdmin.execute(queryParams)
            PersonCategory.DRIVER -> getDriver.execute(queryParams)
        }
    }

    private fun getPhotoFlow(user: User, personId: String): Flow<Response<List<File>>> {
        val queryParams = QueryParameters.create(user)
            .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_EQUALS, personId))
            .build()
        return getFile.execute(queryParams)
    }

    private fun getPersonalDataWithFilesFlow(
        user: User, personId: String
    ): Flow<Response<List<PersonalDataWithFile>>> {
        val queryParams = QueryParameters.create(user)
            .setQueries(QuerySettings(Field.PARENT_ID, QueryType.WHERE_EQUALS, personId))
            .build()

        return getPDataWF.execute(queryParams)
    }

    private fun Response<List<File>>.extractPhoto(): File? =
        if (this is Response.Success) this.data.firstOrNull()
        else null

    private fun Response<List<PersonalDataWithFile>>.extractPDataWFList(): List<PersonalDataWithFile> =
        if (this is Response.Success) this.data
        else emptyList()

    private fun Response<List<Person>>.extractPerson(): Person =
        if (this is Response.Success && !this.isEmpty()) data.first()
        else throw ObjectNotFoundException("Any person was found for required User.")

}