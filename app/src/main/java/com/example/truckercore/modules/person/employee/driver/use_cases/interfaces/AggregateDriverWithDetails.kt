package com.example.truckercore.modules.person.employee.driver.use_cases.interfaces

import com.example.truckercore.modules.person.employee.admin.aggregations.AdminWithDetails
import com.example.truckercore.modules.person.employee.driver.aggregations.DriverWithDetails
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving [Driver] records along with their associated [DriverWithDetails].
 */
internal interface AggregateDriverWithDetails {

    /**
     * Fetches a single employee record along with their associated details based on the provided ID.
     * This method retrieves the employee details as an [AdminWithDetails] object, which includes the [Employee]
     * data and the corresponding [DriverWithDetails] object.
     *
     * @param documentParams The document parameters to filter the employee records.
     * @return A [Flow] of:
     * - [Response.Success] containing the [DriverWithDetails] object if the employee record and details were found.
     * - [Response.Empty] if no employee record exists with the given ID or no details are associated with it.
     */
    fun execute(documentParams: DocumentParameters): Flow<Response<DriverWithDetails>>

}