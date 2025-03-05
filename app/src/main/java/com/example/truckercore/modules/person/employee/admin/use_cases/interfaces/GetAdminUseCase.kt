package com.example.truckercore.modules.person.employee.admin.use_cases.interfaces

import com.example.truckercore.modules.person.employee.admin.entity.Admin
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving an [Admin] entity by its ID.
 *
 * This use case involves fetching the [Admin] from the repository using the provided ID,
 * checking the necessary permissions, and ensuring the [Admin] exists in the system.
 *
 * @see Admin
 * @see Response
 */
interface GetAdminUseCase {

    /**
     * Executes the use case to retrieve an [Admin] entity by its ID.
     *
     * @param documentParams The document parameters to filter the admin records.
     * @return A [Flow] of:
     * - [Response.Success] containing the [Admin] object if the admin record was found.
     * - [Response.Empty] if no admin record exists with the given ID.
     */
    fun execute(documentParams: DocumentParameters): Flow<Response<Admin>>

    fun execute(queryParams: QueryParameters): Flow<Response<List<Admin>>>

}