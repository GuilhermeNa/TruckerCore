package com.example.truckercore.model.modules.user.mapper

import com.example.truckercore.model.infrastructure.security.permissions.enums.Level
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.modules.user.dto.UserDto
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.modules.user.enums.PersonCategory
import com.example.truckercore.model.shared.enums.PersistenceStatus
import com.example.truckercore.model.shared.errors.mapping.IllegalMappingArgumentException
import com.example.truckercore.model.shared.errors.mapping.InvalidForMappingException
import com.example.truckercore.model.shared.interfaces.Dto
import com.example.truckercore.model.shared.interfaces.Entity
import com.example.truckercore.model.shared.interfaces.Mapper
import com.example.truckercore.model.shared.utils.expressions.toDate
import com.example.truckercore.model.shared.utils.expressions.toLocalDateTime

internal class UserMapper : Mapper {

    override fun toEntity(dto: Dto): User =
        if (dto is UserDto) convertToEntity(dto)
        else throw IllegalMappingArgumentException(
            expected = UserDto::class.simpleName,
            received = dto::class.simpleName
        )

    override fun toDto(entity: Entity): UserDto =
        if (entity is User) convertToDto(entity)
        else throw IllegalMappingArgumentException(
            expected = User::class.simpleName,
            received = entity::class.simpleName
        )

    private fun convertToEntity(dto: UserDto) = try {
        User(
            businessCentralId = dto.businessCentralId!!,
            id = dto.id!!,
            lastModifierId = dto.lastModifierId!!,
            creationDate = dto.creationDate!!.toLocalDateTime(),
            lastUpdate = dto.lastUpdate!!.toLocalDateTime(),
            persistenceStatus = PersistenceStatus.convertString(dto.persistenceStatus),
            isVip = dto.isVip!!,
            vipStart = dto.vipStart?.toLocalDateTime(),
            vipEnd = dto.vipEnd?.toLocalDateTime(),
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
            isVip = entity.isVip,
            vipStart = entity.vipStart?.toDate(),
            vipEnd = entity.vipEnd?.toDate(),
            level = entity.level.name,
            permissions = entity.permissions.map { it.name },
            personFLag = entity.personFLag.name
        )
    } catch (error: Exception) {
        throw InvalidForMappingException(entity, error)
    }

}
