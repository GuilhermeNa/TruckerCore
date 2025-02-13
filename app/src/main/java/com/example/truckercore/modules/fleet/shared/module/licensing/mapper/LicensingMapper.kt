package com.example.truckercore.modules.fleet.shared.module.licensing.mapper

import com.example.truckercore.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.mapping.IllegalMappingArgumentException
import com.example.truckercore.shared.errors.mapping.InvalidForMappingException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.interfaces.Mapper
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

internal class LicensingMapper : Mapper {

    override fun toEntity(dto: Dto): Licensing =
        if (dto is LicensingDto) convertToEntity(dto)
        else throw IllegalMappingArgumentException(
            expected = LicensingDto::class,
            received = dto::class
        )

    override fun toDto(entity: Entity): LicensingDto =
        if (entity is Licensing) convertToDto(entity)
        else throw IllegalMappingArgumentException(
            expected = Licensing::class,
            received = entity::class
        )

    private fun convertToEntity(dto: LicensingDto) = try {
        Licensing(
            businessCentralId = dto.businessCentralId!!,
            id = dto.id!!,
            lastModifierId = dto.lastModifierId!!,
            creationDate = dto.creationDate!!.toLocalDateTime(),
            lastUpdate = dto.lastUpdate!!.toLocalDateTime(),
            persistenceStatus = PersistenceStatus.convertString(dto.persistenceStatus),
            parentId = dto.parentId!!,
            emissionDate = dto.emissionDate!!.toLocalDateTime(),
            expirationDate = dto.expirationDate!!.toLocalDateTime(),
            plate = dto.plate!!,
            exercise = dto.exercise!!.toLocalDateTime()
        )
    } catch (error: Exception) {
        throw InvalidForMappingException(dto, error)
    }

    private fun convertToDto(entity: Licensing) = try {
        LicensingDto(
            businessCentralId = entity.businessCentralId,
            id = entity.id,
            lastModifierId = entity.lastModifierId,
            creationDate = entity.creationDate.toDate(),
            lastUpdate = entity.lastUpdate.toDate(),
            persistenceStatus = entity.persistenceStatus.name,
            parentId = entity.parentId,
            emissionDate = entity.emissionDate.toDate(),
            expirationDate = entity.expirationDate.toDate(),
            plate = entity.plate,
            exercise = entity.exercise.toDate()
        )
    } catch (error: Exception) {
        throw InvalidForMappingException(entity, error)
    }

}