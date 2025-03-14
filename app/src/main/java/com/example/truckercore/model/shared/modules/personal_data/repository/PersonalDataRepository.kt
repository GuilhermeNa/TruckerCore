package com.example.truckercore.model.shared.modules.personal_data.repository

import com.example.truckercore.model.shared.interfaces.Repository
import com.example.truckercore.model.shared.modules.personal_data.dto.PersonalDataDto
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the repository for managing [PersonalDataDto] data.
 *
 * This interface extends the [Repository] interface, providing methods to interact
 * with the underlying data source for [PersonalDataDto] objects. It typically
 * includes operations such as creating, reading, updating, and deleting data.
 * Implementations of this interface are responsible for defining the actual
 * interaction with the data storage (e.g., database, external service, etc.).
 *
 * @see Repository
 * @see PersonalDataDto
 */
internal interface PersonalDataRepository : Repository {

    override fun fetchByDocument(documentParams: DocumentParameters): Flow<Response<PersonalDataDto>>

    override fun fetchByQuery(queryParams: QueryParameters): Flow<Response<List<PersonalDataDto>>>

}