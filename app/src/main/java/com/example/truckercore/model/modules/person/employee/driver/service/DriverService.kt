package com.example.truckercore.model.modules.person.employee.driver.service

import com.example.truckercore.model.modules.person.employee.driver.entity.Driver
import com.example.truckercore.model.modules.person.shared.person_details.PersonWithDetails
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the service layer for managing driver data.
 * This service is responsible for interacting with use cases that fetch driver records,
 * both with and without additional details (such as personal data and files).
 */
interface DriverService {

    /**
     * Fetches a single driver record based on the provided document parameters.
     *
     * This method retrieves the basic driver information without additional details.
     *
     * @param documentParam The document parameters used to filter and fetch the driver record.
     * @return A [Flow] containing:
     * - [Response.Success] if the driver was found and fetched successfully.
     * - [Response.Empty] if no driver was found for the provided parameters.
     * - [Response.Error] if there was an error during the operation.
     */
    fun fetchDriver(documentParam: DocumentParameters): Flow<Response<Driver>>

    /**
     * Fetches a single driver record along with its associated details, such as personal data and files.
     *
     * This method retrieves the [PersonWithDetails] object, which includes not only the basic [Driver] information,
     * but also additional details like the driver's photo and personal data with files.
     *
     * @param documentParam The document parameters used to filter and fetch the driver and its details.
     * @return A [Flow] containing:
     *  - [Response.Success] if the driver and its details were found and fetched successfully.
     *  - [Response.Empty] if no driver or details were found for the provided parameters.
     *  - [Response.Error] if there was an error during the operation.
     */
    fun fetchDriverWithDetails(documentParam: DocumentParameters): Flow<Response<PersonWithDetails>>

}