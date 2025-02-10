package com.example.truckercore.shared.modules.storage_file.mapper

import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.mapping.IllegalMappingArgumentException
import com.example.truckercore.shared.errors.mapping.InvalidForMappingException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.interfaces.NewMapper
import com.example.truckercore.shared.modules.storage_file.dto.StorageFileDto
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

internal class StorageFileMapper : NewMapper {

    override fun toEntity(dto: Dto): StorageFile =
        if (dto is StorageFileDto) convertToEntity(dto)
        else throw IllegalMappingArgumentException(expected = StorageFileDto::class, received = dto)

    override fun toDto(entity: Entity): StorageFileDto =
        if (entity is StorageFile) convertToDto(entity)
        else throw IllegalMappingArgumentException(expected = StorageFile::class, received = entity)

    private fun convertToEntity(dto: StorageFileDto) = try {
        StorageFile(
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

    private fun convertToDto(entity: StorageFile) = try {
        StorageFileDto(
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

