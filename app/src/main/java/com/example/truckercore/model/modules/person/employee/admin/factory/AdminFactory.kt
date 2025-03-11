package com.example.truckercore.model.modules.person.employee.admin.factory

import com.example.truckercore.model.modules.person.employee.admin.dto.AdminDto
import com.example.truckercore.model.modules.person.employee.admin.entity.Admin
import com.example.truckercore.model.modules.person.employee.admin.mapper.AdminMapper
import com.example.truckercore.model.modules.person.employee.shared.enums.EmployeeStatus
import com.example.truckercore.model.shared.enums.PersistenceStatus
import com.example.truckercore.model.shared.services.ValidatorService
import java.time.LocalDateTime

/**
 * Factory class for creating instances of [AdminDto].
 *
 * This class is responsible for creating a new [AdminDto] with the provided `centralId`, `userId`, `personId`, `name`, and `email`.
 * It ensures that the created entity is properly validated and mapped into a DTO before returning it.
 *
 * @property validator The [ValidatorService] used to validate the created [Admin] entity.
 * @property mapper The [AdminMapper] used to convert the [Admin] entity to a DTO.
 */
internal class AdminFactory(
    private val validator: ValidatorService,
    private val mapper: AdminMapper
) {

    /**
     * Creates a new [AdminDto] instance with the specified [centralId], [userId], [personId], [name], and [email].
     *
     * This method will create a new [Admin] entity, validate it using the provided [ValidatorService],
     * and then map it to a [AdminDto]. The resulting DTO will be initialized with the provided `personId`.
     *
     * @param centralId The ID to be associated with the created [AdminDto] as the `businessCentralId`.
     * @param userId The user ID to be associated with the created [AdminDto] as the `lastModifierId` and `userId`.
     * @param personId The ID to be used for the initialization of the resulting [AdminDto].
     * @param name The name of the admin to be associated with the created [AdminDto].
     * @param email The email address of the admin to be associated with the created [AdminDto].
     * @return The newly created [AdminDto] initialized with the specified `personId`.
     */
    fun create(
        centralId: String,
        userId: String,
        personId: String,
        name: String,
        email: String
    ): AdminDto {
        val entity = Admin(
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