package com.example.truckercore.model.modules.notification.mapper

import com.example.truckercore.model.configs.app_constants.Parent
import com.example.truckercore.model.modules.notification.dto.NotificationDto
import com.example.truckercore.model.modules.notification.entity.Notification
import com.example.truckercore.model.shared.enums.PersistenceStatus
import com.example.truckercore.model.shared.errors.mapping.IllegalMappingArgumentException
import com.example.truckercore.model.shared.errors.mapping.InvalidForMappingException
import com.example.truckercore.model.shared.interfaces.Dto
import com.example.truckercore.model.shared.interfaces.Entity
import com.example.truckercore.model.shared.interfaces.Mapper
import com.example.truckercore.model.shared.utils.expressions.toDate
import com.example.truckercore.model.shared.utils.expressions.toLocalDateTime

internal class NotificationMapper : Mapper {

    override fun toEntity(dto: Dto): Notification =
        if (dto is NotificationDto) convertToEntity(dto)
        else throw IllegalMappingArgumentException(
            expected = NotificationDto::class.simpleName,
            received = dto::class.simpleName
        )

    override fun toDto(entity: Entity): NotificationDto =
        if (entity is Notification) convertToDto(entity)
        else throw IllegalMappingArgumentException(
            expected = Notification::class.simpleName,
            received = entity::class.simpleName
        )

    private fun convertToEntity(dto: NotificationDto) = try {
        Notification(
            businessCentralId = dto.businessCentralId!!,
            id = dto.id!!,
            lastModifierId = dto.lastModifierId!!,
            creationDate = dto.creationDate!!.toLocalDateTime(),
            lastUpdate = dto.lastUpdate!!.toLocalDateTime(),
            persistenceStatus = PersistenceStatus.convertString(dto.persistenceStatus!!),
            title = dto.title!!,
            message = dto.message!!,
            isRead = dto.isRead!!,
            parent = Parent.convertString(dto.parent),
            parentId = dto.parentId!!
        )
    } catch (error: Exception) {
        throw InvalidForMappingException(dto, error)
    }

    private fun convertToDto(entity: Notification) = try {
        NotificationDto(
            businessCentralId = entity.businessCentralId,
            id = entity.id,
            lastModifierId = entity.lastModifierId,
            creationDate = entity.creationDate.toDate(),
            lastUpdate = entity.lastUpdate.toDate(),
            persistenceStatus = entity.persistenceStatus.name,
            title = entity.title,
            message = entity.message,
            isRead = entity.isRead,
            parent = entity.parent.name,
            parentId = entity.parentId
        )
    } catch (error: Exception) {
        throw InvalidForMappingException(entity, error)
    }

}