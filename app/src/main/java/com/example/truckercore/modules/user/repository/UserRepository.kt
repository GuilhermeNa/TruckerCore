package com.example.truckercore.modules.user.repository

import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.shared.interfaces.Repository

/**
 * Interface representing the repository for managing [UserDto] data.
 *
 * This interface extends the [Repository] interface, providing methods to interact
 * with the underlying data source for UserDto objects. It typically
 * includes operations such as creating, reading, updating, and deleting UserDto data.
 * Implementations of this interface are responsible for defining the actual
 * interaction with the data storage (e.g., database, external service, etc.).
 *
 * @see Repository
 * @see BusinessCentralDto
 */
internal interface UserRepository: Repository<UserDto>