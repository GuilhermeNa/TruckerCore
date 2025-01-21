package com.example.truckercore.modules.employee.driver.repository

import com.example.truckercore.modules.employee.driver.dto.DriverDto
import com.example.truckercore.shared.interfaces.Repository

/**
 * Interface representing the repository for managing [DriverDto] data.
 *
 * This interface extends the [Repository] interface, providing methods to interact
 * with the underlying data source for [DriverDto] objects. It typically
 * includes operations such as creating, reading, updating, and deleting data.
 * Implementations of this interface are responsible for defining the actual
 * interaction with the data storage (e.g., database, external service, etc.).
 *
 * @see Repository
 * @see DriverDto
 */
internal interface DriverRepository : Repository<DriverDto>