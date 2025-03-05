package com.example.truckercore.modules.person.employee.admin.use_cases.interfaces

import com.example.truckercore.modules.person.employee.admin.aggregations.AdminWithDetails
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing a use case for retrieving an admin record along with its associated details.
 * This interface defines the contract for fetching both the admin data and its associated details
 * (such as personal data and files). The result is aggregated into a single [AdminWithDetails] object.
 *
 * The aggregated details may include data like photos, personal data with files, and other associated
 * information linked to the admin.
 */
internal interface AggregateAdminWithDetails {

    /**
     * Executes the use case to fetch an admin record along with its associated details.
     * The method combines the admin data, file data, and personal data with files, and returns them
     * as a single aggregated [AdminWithDetails] object.
     *
     * @param documentParams The parameters used to filter the admin and related records.
     * @return A [Flow] of:
     * - [Response.Success] containing the [AdminWithDetails] object if the admin and its associated details were successfully fetched.
     * - [Response.Empty] if no matching admin record is found or if no related details are available.
     */
    fun execute(documentParams: DocumentParameters): Flow<Response<AdminWithDetails>>

}