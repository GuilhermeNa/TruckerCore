package com.example.truckercore.model.modules.person.employee.driver.use_cases.interfaces

import com.example.truckercore.model.modules.person.employee.driver.entity.Driver
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving a [Driver] entity by its ID.
 */
interface GetDriverUseCase {

    /**
     * Executes the use case to retrieve a [Driver] entity by its ID.
     *
     * @param documentParams The document parameters to filter the licensing records.
     * @return A [Flow] of:
     * - [Response.Success] containing the [Driver] object if the driver record was found.
     * - [Response.Empty] if no driver record exists with the given ID.
     */
    fun execute(documentParams: DocumentParameters): Flow<Response<Driver>>

    fun execute(queryParams: QueryParameters): Flow<Response<List<Driver>>>

}