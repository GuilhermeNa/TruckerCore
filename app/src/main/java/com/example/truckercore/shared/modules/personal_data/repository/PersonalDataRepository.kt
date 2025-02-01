package com.example.truckercore.shared.modules.personal_data.repository

import com.example.truckercore.shared.interfaces.Repository
import com.example.truckercore.shared.modules.personal_data.dto.PersonalDataDto
import com.example.truckercore.shared.utils.sealeds.Response
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
internal interface PersonalDataRepository : Repository<PersonalDataDto> {

    /**
     * Fetches a list of [PersonalDataDto] entities by their parent ID.
     *
     * @param parentId The unique identifier of parent.
     * @return A [Flow] of:
     * - [Response.Success] when the object's are successfully found.
     * - [Response.Error] when any error occurs.
     * - [Response.Empty] when the data was not found.
     */
    suspend fun fetchByParentId(parentId: String): Flow<Response<List<PersonalDataDto>>>

}