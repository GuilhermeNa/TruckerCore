package com.example.truckercore.shared.modules.storage_file.mapper

import com.example.truckercore.shared.abstractions.Mapper
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.modules.storage_file.dto.StorageFileDto
import com.example.truckercore.shared.modules.storage_file.entity.StorageFile
import com.example.truckercore.shared.modules.storage_file.errors.StorageFileMappingException
import com.example.truckercore.shared.utils.expressions.logWarn
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

internal class StorageFileMapper : Mapper<StorageFile, StorageFileDto>() {

    override fun handleEntityMapping(entity: StorageFile) = StorageFileDto(
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

    override fun handleDtoMapping(dto: StorageFileDto) = StorageFile(
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

    override fun handleMappingError(receivedException: Exception, obj: Any): Nothing {
        val message = "Error while mapping a ${obj::class.simpleName} object."
        logWarn(
            context = javaClass,
            message = message
        )
        throw StorageFileMappingException(message, receivedException)
    }

}

