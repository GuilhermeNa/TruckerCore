package com.example.truckercore.modules.fleet.shared.module.licensing.mapper

import com.example.truckercore.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.errors.LicensingMappingException
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.interfaces.NewMapper
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

internal class NewLicensingMapper : NewMapper {

    override fun toEntity(dto: Dto): Licensing =
        if (dto is LicensingDto) mapToEntity(dto)
        else throw IllegalArgumentException(
            "Expected a LicensingDto for mapping but received: ${dto.javaClass.simpleName}"
        )

    override fun toDto(entity: Entity): LicensingDto =
        if (entity is Licensing) mapToDto(entity)
        else throw IllegalArgumentException(
            "Expected a Licensing entity for mapping but received: ${entity.javaClass.simpleName}"
        )

    private fun mapToEntity(dto: LicensingDto) = try {
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
        throw LicensingMappingException(dto, error)
    }

    private fun mapToDto(entity: Licensing) = try {
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
        throw LicensingMappingException(entity, error)
    }

}