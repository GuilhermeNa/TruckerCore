package com.example.truckercore.model.modules.person.employee.admin.use_cases.interfaces

import com.example.truckercore.model.modules.person.employee.admin.entity.Admin
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving an [Admin] entity by its ID.
 *
 * This use case involves fetching the [Admin] from the repository using the provided ID,
 * checking the necessary permissions, and ensuring the [Admin] exists in the system.
 *
 * @see Admin
 * @see AppResponse
 */
interface GetAdminUseCase {

    /**
     * Executes the use case to retrieve an [Admin] entity by its ID.
     *
     * @param documentParams The document parameters to filter the admin records.
     * @return A [Flow] of:
     * - [AppResponse.Success] containing the [Admin] object if the admin record was found.
     * - [AppResponse.Empty] if no admin record exists with the given ID.
     */
    fun execute(documentParams: DocumentParameters): Flow<AppResponse<Admin>>

    /**
     * Executes the use case to retrieve a list of [Admin] entities based on the provided query parameters.
     *
     * This method allows searching for multiple admins using [QueryParameters], which may include
     * different filters and pagination options.
     *
     * @param queryParams The query parameters to filter and search for the admin records. This may
     *                    include various filters such as name, role, status, etc.
     * @return A [Flow] of:
     * - [AppResponse.Success] containing a list of [Admin] objects if admins were found.
     * - [AppResponse.Empty] if no admins match the given query criteria.
     */
    fun execute(queryParams: QueryParameters): Flow<AppResponse<List<Admin>>>

}