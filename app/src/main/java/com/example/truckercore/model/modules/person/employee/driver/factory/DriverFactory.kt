package com.example.truckercore.model.modules.person.employee.driver.factory

import com.example.truckercore.model.modules.person.employee.driver.dto.DriverDto
import com.example.truckercore.model.modules.person.employee.driver.entity.Driver
import com.example.truckercore.model.modules.person.employee.driver.mapper.DriverMapper
import com.example.truckercore.model.modules.person.employee.shared.enums.EmployeeStatus
import com.example.truckercore.model.shared.enums.PersistenceStatus
import com.example.truckercore.model.shared.services.ValidatorService
import java.time.LocalDateTime

/**
 * Factory class for creating instances of [DriverDto].
 *
 * This class is responsible for creating a new [DriverDto] with the provided `centralId`, `userId`, `personId`, `name`, and `email`.
 * It ensures that the created entity is properly validated and mapped into a DTO before returning it.
 *
 * @property validator The [ValidatorService] used to validate the created [Driver] entity.
 * @property mapper The [DriverMapper] used to convert the [Driver] entity to a DTO.
 */
internal class DriverFactory(
    private val validator: ValidatorService,
    private val mapper: DriverMapper
) {

    /**
     * Creates a new [DriverDto] instance with the specified [centralId], [userId], [personId], [name], and [email].
     *
     * This method will create a new [Driver] entity, validate it using the provided [ValidatorService],
     * and then map it to a [DriverDto]. The resulting DTO will be initialized with the provided `personId`.
     *
     * @param centralId The ID to be associated with the created [DriverDto] as the `businessCentralId`.
     * @param userId The user ID to be associated with the created [DriverDto] as the `lastModifierId` and `userId`.
     * @param personId The ID to be used for the initialization of the resulting [DriverDto].
     * @param name The name of the driver to be associated with the created [DriverDto].
     * @param email The email address of the driver to be associated with the created [DriverDto].
     * @return The newly created [DriverDto] initialized with the specified `personId`.
     */
    fun create(
        centralId: String,
        userId: String,
        personId: String,
        name: String,
        email: String
    ): DriverDto {
        val entity = Driver(
            businessCentralId = centralId,
            id = null,
            lastModifierId = userId,
            creationDate = LocalDateTime.now(),
            lastUpdate = LocalDateTime.now(),
            persistenceStatus = PersistenceStatus.PENDING,
            userId = userId,
            name = name,
            email = email,
            employeeStatus = EmployeeStatus.WORKING
        )
        validator.validateForCreation(entity)
        val dto = mapper.toDto(entity)
        return dto.initializeId(personId)
    }

}