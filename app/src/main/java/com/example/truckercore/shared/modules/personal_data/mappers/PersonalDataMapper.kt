package com.example.truckercore.shared.modules.personal_data.mappers

import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.InvalidPersistenceStatusException
import com.example.truckercore.shared.errors.InvalidEnumParameterException
import com.example.truckercore.shared.errors.MissingFieldException
import com.example.truckercore.shared.errors.UnknownErrorException
import com.example.truckercore.shared.interfaces.MapperI
import com.example.truckercore.shared.modules.personal_data.configs.PersonalDataConfigs
import com.example.truckercore.shared.modules.personal_data.dtos.PersonalDataDto
import com.example.truckercore.shared.modules.personal_data.entities.PersonalData
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

internal object PersonalDataMapper : MapperI<PersonalData, PersonalDataDto> {

    override fun toDto(entity: PersonalData) = mapEntityToDto(entity)

    override fun toEntity(dto: PersonalDataDto) = try {
        mapDtoToEntity(dto)

    } catch (e: IllegalArgumentException) {
        throw MissingFieldException(buildExceptionMessage(dto, e))

    } catch (e: InvalidEnumParameterException) {
        throw InvalidPersistenceStatusException(buildExceptionMessage(dto, e))

    } catch (e: Exception) {
        throw UnknownErrorException(message = buildExceptionMessage(dto, e), throwable = e)

    }

//--------------------------------------------------------------------------------------------------

    private fun mapEntityToDto(entity: PersonalData) =
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

    private fun mapDtoToEntity(dto: PersonalDataDto): PersonalData {
        PersonalDataConfigs.validateRequiredFields(dto)
        return PersonalData(
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
    }

    private fun buildExceptionMessage(
        dto: PersonalDataDto,
        exception: Exception
    ): String = when (exception) {
        is IllegalArgumentException -> {
            "Failed while mapping a personal data. Missing fields: ${exception.message}"
        }

        is InvalidEnumParameterException -> {
            "Failed while mapping a personal data. Expecting a valid persistence " +
                    "status, and received: ${dto.persistenceStatus} "
        }

        else -> "Unknown error occurred while mapping a personal data."
    }

}