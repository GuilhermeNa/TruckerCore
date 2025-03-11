package com.example.truckercore.model.modules.user.factory

import com.example.truckercore.model.infrastructure.security.permissions.configs.DefaultPermissions
import com.example.truckercore.model.infrastructure.security.permissions.enums.Level
import com.example.truckercore.model.modules.user.dto.UserDto
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.modules.user.enums.PersonCategory
import com.example.truckercore.model.modules.user.mapper.UserMapper
import com.example.truckercore.model.shared.enums.PersistenceStatus
import com.example.truckercore.model.shared.services.ValidatorService
import java.time.LocalDateTime

/**
 * Factory class for creating instances of [UserDto].
 *
 * This class is responsible for creating a new [UserDto] with the provided `centralId`, `uid`, `personFlag`, and `personLevel`.
 * It ensures that the created entity is properly validated and mapped into a DTO before returning it.
 *
 * @property validator The [ValidatorService] used to validate the created [User] entity.
 * @property mapper The [UserMapper] used to convert the [User] entity to a DTO.
 */
internal class UserFactory(
    private val validator: ValidatorService,
    private val mapper: UserMapper
) {

    /**
     * Creates a new [UserDto] instance with the specified [centralId], [uid], [personCategory], and [personLevel].
     *
     * This method will create a new [User] entity, validate it using the provided [ValidatorService],
     * and then map it to a [UserDto]. The resulting DTO will be initialized with the provided `uid`.
     *
     * @param centralId The ID to be associated with the created [UserDto] as the `businessCentralId`.
     * @param uid The user ID to be associated with the created [UserDto] as the `lastModifierId` and to initialize the DTO.
     * @param personCategory The flag indicating the type of person (e.g., Driver or Admin) to be associated with the user.
     * @param personLevel The level associated with the user, which determines their permissions.
     * @return The newly created [UserDto] initialized with the specified `uid`.
     */
    fun create(
        centralId: String,
        uid: String,
        personCategory: PersonCategory,
        personLevel: Level
    ): UserDto {
        val entity = User(
            businessCentralId = centralId,
            id = null,
            lastModifierId = uid,
            creationDate = LocalDateTime.now(),
            lastUpdate = LocalDateTime.now(),
            persistenceStatus = PersistenceStatus.PENDING,
            isVip = false,
            vipStart = null,
            vipEnd = null,
            level = personLevel,
            permissions = DefaultPermissions.get(personLevel),
            personFLag = personCategory
        )
        validator.validateForCreation(entity)
        val dto = mapper.toDto(entity)
        return dto.initializeId(uid)
    }

}