package com.example.truckercore.modules.employee.admin.use_cases.interfaces

import com.example.truckercore.modules.employee.admin.aggregations.AdminWithDetails
import com.example.truckercore.modules.employee.shared.abstractions.Employee
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the use case for retrieving [Employee] records along with their associated [EmployeeDetails].
 */
internal interface AggregateAdminWithDetails {

    /**
     * Fetches a single employee record along with their associated details based on the provided ID.
     * This method retrieves the employee details as an [AdminWithDetails] object, which includes the [Employee]
     * data and the corresponding [EmployeeDetails] object.
     *
     * @param documentParams The document parameters to filter the employee records.
     * @return A [Flow] of:
     * - [Response.Success] containing the [AdminWithDetails] object if the employee record and details were found.
     * - [Response.Empty] if no employee record exists with the given ID or no details are associated with it.
     */
    fun execute(documentParams: DocumentParameters): Flow<Response<AdminWithDetails>>

}