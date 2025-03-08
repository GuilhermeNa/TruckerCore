package com.example.truckercore.model.modules.person.employee.admin.service

import com.example.truckercore.model.modules.person.employee.admin.entity.Admin
import com.example.truckercore.model.modules.person.shared.person_details.PersonWithDetails
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the contract for services interacting with Admin data.
 * The [AdminService] interface defines methods for fetching basic admin records and admin records with
 * associated details like photos and personal data. This service acts as an intermediary layer between the
 * application and the backend system, encapsulating the logic for retrieving Admin information.
 */
interface AdminService {

    /**
     * Fetches a basic admin record based on document parameters.
     *
     * @param documentParam The parameters used to filter the admin records.
     * @return A [Flow] of [Response] containing the [Admin] record:
     * - [Response.Success] if the admin is found and retrieved successfully.
     * - [Response.Empty] if no admin record is found.
     * - [Response.Error] if an error occurs during the process.
     */
    fun fetchAdmin(documentParam: DocumentParameters): Flow<Response<Admin>>

    /**
     * Fetches an admin record along with its associated details (like personal data, photo, etc.).
     *
     * @param documentParam The parameters used to filter the admin records and fetch related details.
     * @return A [Flow] of [Response] containing the [PersonWithDetails] record.
     * - [Response.Success] if the admin and details are found and retrieved successfully.
     * - [Response.Empty] if no admin with the requested details is found.
     * - [Response.Error] if an error occurs during the process.
     */
    fun fetchAdminWithDetails(documentParam: DocumentParameters): Flow<Response<PersonWithDetails>>

}