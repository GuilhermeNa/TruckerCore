package com.example.truckercore.shared.modules.storage_file.mappers

import com.example.truckercore.shared.modules.storage_file.configs.StorageFileConfigs
import com.example.truckercore.shared.modules.storage_file.dtos.StorageFileDto
import com.example.truckercore.shared.modules.storage_file.entities.StorageFile
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.InvalidPersistenceStatusException
import com.example.truckercore.shared.errors.InvalidStateParameterException
import com.example.truckercore.shared.errors.InvalidUrlFormatException
import com.example.truckercore.shared.errors.MissingFieldException
import com.example.truckercore.shared.errors.UnknownErrorException
import com.example.truckercore.shared.interfaces.Mapper
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime
import java.net.MalformedURLException

internal object StorageFileMapper : Mapper<StorageFile, StorageFileDto> {

    override fun toDto(entity: StorageFile) = mapEntityToDto(entity)

    override fun toEntity(dto: StorageFileDto) = try {
        mapDtoToEntity(dto)

    } catch (e: IllegalArgumentException) {
        throw MissingFieldException(buildExceptionMessage(dto, e))

    } catch (e: InvalidStateParameterException) {
        throw InvalidPersistenceStatusException(buildExceptionMessage(dto, e))

    } catch (e: MalformedURLException) {
        throw InvalidUrlFormatException(buildExceptionMessage(dto, e))

    } catch (e: Exception) {
        throw UnknownErrorException(message = buildExceptionMessage(dto, e), throwable = e)

    }

//--------------------------------------------------------------------------------------------------

    private fun mapEntityToDto(entity: StorageFile) =
        StorageFileDto(
            centralId = entity.centralId,
            id = entity.id,
            lastModifierId = entity.lastModifierId,
            creationDate = entity.creationDate.toDate(),
            lastUpdate = entity.lastUpdate.toDate(),
            persistenceStatus = entity.persistenceStatus.name,
            parentId = entity.parentId,
            url = entity.url,
            isUpdating = entity.isUpdating
        )

    private fun mapDtoToEntity(dto: StorageFileDto): StorageFile {
        StorageFileConfigs.validateRequiredFields(dto)
        return StorageFile(
            centralId = dto.centralId!!,
            id = dto.id!!,
            lastModifierId = dto.lastModifierId!!,
            creationDate = dto.creationDate!!.toLocalDateTime(),
            lastUpdate = dto.lastUpdate!!.toLocalDateTime(),
            persistenceStatus = PersistenceStatus.convertString(dto.persistenceStatus!!),
            parentId = dto.parentId!!,
            url = dto.url!!,
            isUpdating = dto.isUpdating!!
        )
    }

    private fun buildExceptionMessage(
        dto: StorageFileDto,
        exception: Exception
    ): String = when (exception) {
        is IllegalArgumentException -> {
            "Failed while mapping a storage file. Missing fields: ${exception.message}"
        }

        is InvalidStateParameterException -> {
            "Failed while mapping a storage file. Expecting a valid persistence " +
                    "status, and received: ${dto.persistenceStatus} "
        }

        is MalformedURLException -> {
            "Invalid file URL: ${dto.url}"
        }

        else -> "Unknown error occurred while mapping a storage file."
    }

}

