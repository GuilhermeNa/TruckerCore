package com.example.truckercore.model.modules.vip.mapper

import com.example.truckercore.model.modules.vip.dto.VipDto
import com.example.truckercore.model.modules.vip.entity.Vip
import com.example.truckercore.model.shared.enums.PersistenceStatus
import com.example.truckercore.model.shared.errors.mapping.IllegalMappingArgumentException
import com.example.truckercore.model.shared.errors.mapping.InvalidForMappingException
import com.example.truckercore.model.shared.interfaces.Dto
import com.example.truckercore.model.shared.interfaces.Entity
import com.example.truckercore.model.shared.interfaces.Mapper
import com.example.truckercore.model.shared.utils.expressions.toDate
import com.example.truckercore.model.shared.utils.expressions.toLocalDateTime

internal class VipMapper : Mapper {

    override fun toEntity(dto: Dto): Vip =
        if (dto is VipDto) convertToEntity(dto)
        else throw IllegalMappingArgumentException(
            expected = VipDto::class.simpleName,
            received = dto::class.simpleName
        )

    override fun toDto(entity: Entity): VipDto =
        if (entity is Vip) convertToDto(entity)
        else throw IllegalMappingArgumentException(
            expected = Vip::class.simpleName,
            received = entity::class.simpleName
        )

    private fun convertToEntity(dto: VipDto) = try {
        Vip(
            businessCentralId = dto.businessCentralId!!,
            id = dto.id!!,
            lastModifierId = dto.lastModifierId!!,
            creationDate = dto.creationDate!!.toLocalDateTime(),
            lastUpdate = dto.lastUpdate!!.toLocalDateTime(),
            persistenceStatus = PersistenceStatus.convertString(dto.persistenceStatus),
            vipStart = dto.vipStart!!.toLocalDateTime(),
            vipEnd = dto.vipEnd!!.toLocalDateTime(),
            notificationDate = dto.notificationDate!!.toLocalDateTime(),
            userId = dto.userId!!,
            isActive = dto.isActive!!
        )
    } catch (error: Exception) {
        throw InvalidForMappingException(dto, error)
    }

    private fun convertToDto(entity: Vip) = try {
        VipDto(
            businessCentralId = entity.businessCentralId,
            id = entity.id,
            lastModifierId = entity.lastModifierId,
            creationDate = entity.creationDate.toDate(),
            lastUpdate = entity.lastUpdate.toDate(),
            persistenceStatus = entity.persistenceStatus.name,
            vipStart = entity.vipStart.toDate(),
            vipEnd = entity.vipEnd.toDate(),
            notificationDate = entity.notificationDate.toDate(),
            userId = entity.userId,
            isActive = entity.isActive
        )
    } catch (error: Exception) {
        throw InvalidForMappingException(entity, error)
    }

}