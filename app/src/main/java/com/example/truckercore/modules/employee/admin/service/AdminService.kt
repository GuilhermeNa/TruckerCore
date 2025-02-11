package com.example.truckercore.modules.employee.admin.service

import com.example.truckercore.modules.employee.admin.aggregations.AdminWithDetails
import com.example.truckercore.modules.employee.admin.entity.Admin
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface for the Admin Service, responsible for communicating with the backend
 * to fetch and manage Admin data. This service acts as an intermediary layer that
 * allows applications to interact with the backend for interacting with Admin records.
 */
interface AdminService {

    fun fetchAdmin(documentParam: DocumentParameters): Flow<Response<Admin>>

    /**
     * Fetches a single admin record along with its associated files based on document parameters.
     *
     * @param documentParam The document parameters to filter the admin records with files.
     * @return A [Flow] containing a [Response] with the [AdminWithDetails] record.
     */
    fun fetchAdminWithDetails(documentParam: DocumentParameters): Flow<Response<AdminWithDetails>>

}