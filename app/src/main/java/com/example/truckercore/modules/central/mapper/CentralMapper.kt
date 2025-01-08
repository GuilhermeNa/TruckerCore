package com.example.truckercore.modules.central.mapper

import com.example.truckercore.modules.central.validator.CentralMappingValidator
import com.example.truckercore.modules.central.dto.CentralDto
import com.example.truckercore.modules.central.entity.Central
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.InvalidPersistenceStatusException
import com.example.truckercore.shared.errors.InvalidStateParameterException
import com.example.truckercore.shared.errors.MissingFieldException
import com.example.truckercore.shared.errors.UnknownErrorException
import com.example.truckercore.shared.interfaces.Mapper
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

internal object CentralMapper : Mapper<Central, CentralDto> {

    override fun toDto(entity: Central): CentralDto = mapEntityToDto(entity)

    override fun toEntity(dto: CentralDto): Central = try {
        mapDtoToEntity(dto)

    } catch (e: IllegalArgumentException) {
        throw MissingFieldException(buildExceptionMessage(dto, e))

    } catch (e: InvalidStateParameterException) {
        throw InvalidPersistenceStatusException(buildExceptionMessage(dto, e))

    } catch (e: Exception) {
        throw UnknownErrorException(message = buildExceptionMessage(dto, e), throwable = e)

    }

    //----------------------------------------------------------------------------------------------

    private fun mapEntityToDto(entity: Central) =
        CentralDto(
            centralId = entity.centralId,
            id = entity.id,
            lastModifierId = entity.lastModifierId,
            creationDate = entity.creationDate.toDate(),
            lastUpdate = entity.lastUpdate.toDate(),
            persistenceStatus = entity.persistenceStatus.name
        )

    private fun mapDtoToEntity(dto: CentralDto): Central {
        CentralMappingValidator.execute(dto)
        return Central(
            centralId = dto.centralId!!,
            id = dto.id!!,
            lastModifierId = dto.lastModifierId!!,
            creationDate = dto.creationDate!!.toLocalDateTime(),
            lastUpdate = dto.lastUpdate!!.toLocalDateTime(),
            persistenceStatus = PersistenceStatus.convertString(dto.persistenceStatus!!)
        )
    }

    private fun buildExceptionMessage(
        dto: CentralDto,
        exception: Exception
    ): String = when (exception) {
        is IllegalArgumentException -> {
            "Failed while mapping a central. Missing fields: ${exception.message}"
        }

        is InvalidStateParameterException -> {
            "Failed while mapping a central. Expecting a valid persistence " +
                    "status, and received: ${dto.persistenceStatus} "
        }

        else -> "Unknown error occurred while mapping a central."
    }

}