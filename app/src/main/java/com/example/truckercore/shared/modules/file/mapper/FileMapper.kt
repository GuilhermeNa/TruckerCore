package com.example.truckercore.shared.modules.file.mapper

import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.mapping.IllegalMappingArgumentException
import com.example.truckercore.shared.errors.mapping.InvalidForMappingException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.interfaces.Mapper
import com.example.truckercore.shared.modules.file.dto.FileDto
import com.example.truckercore.shared.modules.file.entity.File
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

internal class FileMapper : Mapper {

    override fun toEntity(dto: Dto): File =
        if (dto is FileDto) convertToEntity(dto)
        else throw IllegalMappingArgumentException(
            expected = FileDto::class,
            received = dto::class
        )

    override fun toDto(entity: Entity): FileDto =
        if (entity is File) convertToDto(entity)
        else throw IllegalMappingArgumentException(
            expected = File::class,
            received = entity::class
        )

    private fun convertToEntity(dto: FileDto) = try {
        File(
            businessCentralId = dto.businessCentralId!!,
            id = dto.id!!,
            lastModifierId = dto.lastModifierId!!,
            creationDate = dto.creationDate!!.toLocalDateTime(),
            lastUpdate = dto.lastUpdate!!.toLocalDateTime(),
            persistenceStatus = PersistenceStatus.convertString(dto.persistenceStatus!!),
            parentId = dto.parentId!!,
            url = dto.url!!,
            isUpdating = dto.isUpdating!!
        )
    } catch (error: Exception) {
        throw InvalidForMappingException(dto, error)
    }

    private fun convertToDto(entity: File) = try {
        FileDto(
            businessCentralId = entity.businessCentralId,
            id = entity.id,
            lastModifierId = entity.lastModifierId,
            creationDate = entity.creationDate.toDate(),
            lastUpdate = entity.lastUpdate.toDate(),
            persistenceStatus = entity.persistenceStatus.name,
            parentId = entity.parentId,
            url = entity.url,
            isUpdating = entity.isUpdating
        )
    } catch (error: Exception) {
        throw InvalidForMappingException(entity, error)
    }

}

