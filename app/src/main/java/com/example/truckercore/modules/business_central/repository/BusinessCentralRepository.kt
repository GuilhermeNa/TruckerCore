package com.example.truckercore.modules.business_central.repository

import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.shared.interfaces.Repository

/**
 * Interface representing the repository for managing `BusinessCentralDto` data.
 *
 * This interface extends the `Repository` interface, providing methods to interact
 * with the underlying data source for `BusinessCentralDto` objects. It typically
 * includes operations such as creating, reading, updating, and deleting `BusinessCentralDto` data.
 * Implementations of this interface are responsible for defining the actual
 * interaction with the data storage (e.g., database, external service, etc.).
 *
 * @see Repository
 * @see BusinessCentralDto
 */
internal interface BusinessCentralRepository: Repository<BusinessCentralDto>