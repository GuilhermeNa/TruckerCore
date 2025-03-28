package com.example.truckercore.model.modules.fleet.trailer.mapper

import com.example.truckercore.model.modules.fleet.trailer.dto.TrailerDto
import com.example.truckercore.model.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.model.modules.fleet.trailer.enums.TrailerBrand
import com.example.truckercore.model.modules.fleet.trailer.enums.TrailerCategory
import com.example.truckercore.model.shared.enums.PersistenceStatus
import com.example.truckercore.model.shared.errors.mapping.IllegalMappingArgumentException
import com.example.truckercore.model.shared.errors.mapping.InvalidForMappingException
import com.example.truckercore.model.shared.interfaces.Dto
import com.example.truckercore.model.shared.interfaces.Entity
import com.example.truckercore.model.shared.interfaces.Mapper
import com.example.truckercore.model.shared.utils.expressions.toDate
import com.example.truckercore.model.shared.utils.expressions.toLocalDateTime

internal class TrailerMapper : Mapper {

    override fun toEntity(dto: Dto): Trailer =
        if (dto is TrailerDto) convertToEntity(dto)
        else throw IllegalMappingArgumentException(
            expected = TrailerDto::class.simpleName,
            received = dto::class.simpleName
        )

    override fun toDto(entity: Entity): TrailerDto =
        if (entity is Trailer) convertToDto(entity)
        else throw IllegalMappingArgumentException(
            expected = Trailer::class.simpleName,
            received = entity::class.simpleName
        )

    private fun convertToEntity(dto: TrailerDto) = try {
        Trailer(
            businessCentralId = dto.businessCentralId!!,
            id = dto.id!!,
            lastModifierId = dto.lastModifierId!!,
            creationDate = dto.creationDate!!.toLocalDateTime(),
            lastUpdate = dto.lastUpdate!!.toLocalDateTime(),
            persistenceStatus = PersistenceStatus.convertString(dto.persistenceStatus),
            plate = dto.plate!!,
            color = dto.color!!,
            brand = TrailerBrand.convertString(dto.brand),
            category = TrailerCategory.convertString(dto.category),
            truckId = dto.truckId
        )
    } catch (error: Exception) {
        throw InvalidForMappingException(dto, error)
    }

    private fun convertToDto(entity: Trailer) = try {
        TrailerDto(
            businessCentralId = entity.businessCentralId,
            id = entity.id,
            lastModifierId = entity.lastModifierId,
            creationDate = entity.creationDate.toDate(),
            lastUpdate = entity.lastUpdate.toDate(),
            persistenceStatus = entity.persistenceStatus.name,
            plate = entity.plate,
            color = entity.color,
            brand = entity.brand.name,
            category = entity.category.name,
            truckId = entity.truckId
        )
    } catch (error: Exception) {
        throw InvalidForMappingException(entity, error)
    }

}
