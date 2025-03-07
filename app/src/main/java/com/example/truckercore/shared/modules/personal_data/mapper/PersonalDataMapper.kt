package com.example.truckercore.shared.modules.personal_data.mapper

import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.mapping.IllegalMappingArgumentException
import com.example.truckercore.shared.errors.mapping.InvalidForMappingException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.interfaces.Mapper
import com.example.truckercore.shared.modules.personal_data.dto.PersonalDataDto
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

internal class PersonalDataMapper : Mapper {

    override fun toEntity(dto: Dto): PersonalData =
        if (dto is PersonalDataDto) convertToEntity(dto)
        else throw IllegalMappingArgumentException(
            expected = PersonalDataDto::class.simpleName,
            received = dto::class.simpleName
        )

    override fun toDto(entity: Entity): PersonalDataDto =
        if (entity is PersonalData) convertToDto(entity)
        else throw IllegalMappingArgumentException(
            expected = PersonalData::class.simpleName,
            received = entity::class.simpleName
        )

    private fun convertToEntity(dto: PersonalDataDto) = try {
        PersonalData(
            businessCentralId = dto.businessCentralId!!,
            id = dto.id!!,
            lastModifierId = dto.lastModifierId!!,
            creationDate = dto.creationDate!!.toLocalDateTime(),
            lastUpdate = dto.lastUpdate!!.toLocalDateTime(),
            persistenceStatus = PersistenceStatus.convertString(dto.persistenceStatus!!),
            parentId = dto.parentId!!,
            name = dto.name!!,
            number = dto.number!!,
            emissionDate = dto.emissionDate!!.toLocalDateTime(),
            expirationDate = dto.expirationDate?.toLocalDateTime()
        )
    } catch (error: Exception) {
        throw InvalidForMappingException(dto, error)
    }

    private fun convertToDto(entity: PersonalData) = try {
        PersonalDataDto(
            businessCentralId = entity.businessCentralId,
            id = entity.id,
            lastModifierId = entity.lastModifierId,
            creationDate = entity.creationDate.toDate(),
            lastUpdate = entity.lastUpdate.toDate(),
            persistenceStatus = entity.persistenceStatus.name,
            parentId = entity.parentId,
            name = entity.name,
            number = entity.number,
            emissionDate = entity.emissionDate.toDate(),
            expirationDate = entity.expirationDate?.toDate()
        )
    } catch (error: Exception) {
        throw InvalidForMappingException(entity, error)
    }

}