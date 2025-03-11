package com.example.truckercore.model.modules.business_central.factory

import com.example.truckercore.model.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.model.modules.business_central.entity.BusinessCentral
import com.example.truckercore.model.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.model.shared.enums.PersistenceStatus
import com.example.truckercore.model.shared.services.ValidatorService
import java.time.LocalDateTime

/**
 * Factory class for creating instances of [BusinessCentralDto].
 *
 * This class is responsible for creating a new [BusinessCentralDto] with the provided `centralId` and `userId`.
 * It ensures that the created entity is properly validated and mapped into a DTO before returning it.
 *
 * @property validator The [ValidatorService] used to validate the created [BusinessCentral] entity.
 * @property mapper The [BusinessCentralMapper] used to convert the [BusinessCentral] entity to a DTO.
 */
internal class BusinessCentralFactory(
    private val validator: ValidatorService,
    private val mapper: BusinessCentralMapper
) {

    companion object {
        // The default amount of system access keys for the BusinessCentral entity.
        private const val DEFAULT_SYSTEM_ACCESS_KEYS_AMOUNT = 1
    }

    /**
     * Creates a new [BusinessCentralDto] instance with the specified [centralId] and [userId].
     *
     * This method will create a new [BusinessCentral] entity, validate it using the provided [ValidatorService],
     * and then map it to a [BusinessCentralDto]. The resulting DTO will be initialized with the provided `centralId`.
     *
     * @param centralId The ID to be assigned to the created [BusinessCentralDto].
     * @param userId The user ID to be associated with the created [BusinessCentralDto] as the `lastModifierId`
     *               and the only authorized user in the [authorizedUserIds] set.
     * @return The newly created [BusinessCentralDto] initialized with the specified [centralId] and [userId].
     */
    fun create(
        centralId: String,
        userId: String
    ): BusinessCentralDto {
        val entity = BusinessCentral(
            businessCentralId = "",
            id = null,
            lastModifierId = userId,
            creationDate = LocalDateTime.now(),
            lastUpdate = LocalDateTime.now(),
            persistenceStatus = PersistenceStatus.PENDING,
            authorizedUserIds = hashSetOf(userId),
            keys = DEFAULT_SYSTEM_ACCESS_KEYS_AMOUNT
        )
        validator.validateForCreation(entity)
        val dto = mapper.toDto(entity)
        return dto.initializeId(centralId)
    }

}