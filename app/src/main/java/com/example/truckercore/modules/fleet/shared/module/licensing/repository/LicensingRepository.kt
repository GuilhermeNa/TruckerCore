package com.example.truckercore.modules.fleet.shared.module.licensing.repository

import com.example.truckercore.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.shared.interfaces.Repository

/**
 * Interface representing the repository for managing [LicensingDto] data.
 *
 * This interface extends the [Repository] interface, providing methods to interact
 * with the underlying data source for [LicensingDto] objects. It typically includes
 * operations such as creating, reading, updating, and deleting data. Implementations
 * of this interface are responsible for defining the actual interaction with the data
 * storage (e.g., database, external service, etc.).
 *
 * @see Repository
 * @see LicensingDto
 */
internal interface LicensingRepository : Repository<LicensingDto>