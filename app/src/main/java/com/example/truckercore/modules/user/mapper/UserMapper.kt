package com.example.truckercore.modules.user.mapper

import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.errors.UserMappingException
import com.example.truckercore.shared.abstractions.Mapper
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

internal class UserMapper : Mapper<User, UserDto>() {

    override fun toEntity(dto: UserDto): User = try {
        handleDtoMapping(dto)
    } catch (e: Exception) {
        handleMappingError(e, dto)
    }

    override fun toDto(entity: User): UserDto = try {
        handleEntityMapping(entity)
    } catch (e: Exception) {
        handleMappingError(e, entity)
    }

    //----------------------------------------------------------------------------------------------

    override fun handleEntityMapping(entity: User) = UserDto(
        businessCentralId = entity.businessCentralId,
        id = entity.id,
        lastModifierId = entity.lastModifierId,
        creationDate = entity.creationDate.toDate(),
        lastUpdate = entity.lastUpdate.toDate(),
        persistenceStatus = entity.persistenceStatus.name,
        level = entity.level.name,
        permissions = entity.permissions.map { it.name }
    )

    override fun handleDtoMapping(dto: UserDto) = User(
        businessCentralId = dto.businessCentralId!!,
        id = dto.id!!,
        lastModifierId = dto.lastModifierId!!,
        creationDate = dto.creationDate!!.toLocalDateTime(),
        lastUpdate = dto.lastUpdate!!.toLocalDateTime(),
        persistenceStatus = PersistenceStatus.convertString(dto.persistenceStatus),
        level = Level.convertString(dto.level),
        permissions = dto.permissions!!.map { Permission.convertString(it) }.toSet()
    )

    override fun handleMappingError(receivedException: Exception, obj: Any): Nothing {
        val message = "Error while mapping a ${obj::class.simpleName} object."
        logError(message)
        throw UserMappingException(message = "$message Obj: $obj", receivedException)
    }

}