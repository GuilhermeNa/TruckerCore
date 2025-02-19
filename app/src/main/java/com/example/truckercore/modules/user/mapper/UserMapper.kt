package com.example.truckercore.modules.user.mapper

import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.enums.PersonCategory
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.mapping.IllegalMappingArgumentException
import com.example.truckercore.shared.errors.mapping.InvalidForMappingException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.interfaces.Mapper
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

internal class UserMapper : Mapper {

    override fun toEntity(dto: Dto): User =
        if (dto is UserDto) convertToEntity(dto)
        else throw IllegalMappingArgumentException(expected = UserDto::class, received = dto::class)

    override fun toDto(entity: Entity): UserDto =
        if (entity is User) convertToDto(entity)
        else throw IllegalMappingArgumentException(expected = User::class, received = entity::class)

    private fun convertToEntity(dto: UserDto) = try {
        User(
            businessCentralId = dto.businessCentralId!!,
            id = dto.id!!,
            lastModifierId = dto.lastModifierId!!,
            creationDate = dto.creationDate!!.toLocalDateTime(),
            lastUpdate = dto.lastUpdate!!.toLocalDateTime(),
            persistenceStatus = PersistenceStatus.convertString(dto.persistenceStatus),
            level = Level.convertString(dto.level),
            permissions = dto.permissions!!.map { Permission.convertString(it) }.toHashSet(),
            personFLag = PersonCategory.convertString(dto.personFLag)
        )
    } catch (error: Exception) {
        throw InvalidForMappingException(dto, error)
    }

    private fun convertToDto(entity: User) = try {
        UserDto(
            businessCentralId = entity.businessCentralId,
            id = entity.id,
            lastModifierId = entity.lastModifierId,
            creationDate = entity.creationDate.toDate(),
            lastUpdate = entity.lastUpdate.toDate(),
            persistenceStatus = entity.persistenceStatus.name,
            level = entity.level.name,
            permissions = entity.permissions.map { it.name },
            personFLag = entity.personFLag.name
        )
    } catch (error: Exception) {
        throw InvalidForMappingException(entity, error)
    }

}
