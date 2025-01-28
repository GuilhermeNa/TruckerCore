package com.example.truckercore.shared.modules.personal_data.mapper

import com.example.truckercore.shared.abstractions.Mapper
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.modules.personal_data.dto.PersonalDataDto
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.modules.personal_data.errors.PersonalDataMappingException
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

internal class PersonalDataMapper : Mapper<PersonalData, PersonalDataDto>() {

    override fun toEntity(dto: PersonalDataDto): PersonalData = try {
        handleDtoMapping(dto)
    } catch (e: Exception) {
        handleMappingError(e, dto)
    }

    override fun toDto(entity: PersonalData): PersonalDataDto = try {
        handleEntityMapping(entity)
    } catch (e: Exception) {
        handleMappingError(e, entity)
    }

    //----------------------------------------------------------------------------------------------

    override fun handleEntityMapping(entity: PersonalData) = PersonalDataDto(
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

    override fun handleDtoMapping(dto: PersonalDataDto) = PersonalData(
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

    override fun handleMappingError(receivedException: Exception, obj: Any): Nothing {
        val message = "Error while mapping a ${obj::class.simpleName} object."
        logError(message)
        throw PersonalDataMappingException(message = "$message Obj: $obj", receivedException)
    }

}