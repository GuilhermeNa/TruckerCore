package com.example.truckercore.modules.fleet.shared.module.licensing.mapper

import com.example.truckercore.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.errors.LicensingMappingException
import com.example.truckercore.shared.abstractions.Mapper
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.utils.expressions.logError
import com.example.truckercore.shared.utils.expressions.logWarn
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime

internal class LicensingMapper : Mapper<Licensing, LicensingDto>() {

    override fun handleEntityMapping(entity: Licensing) = LicensingDto(
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

    override fun handleDtoMapping(dto: LicensingDto) = Licensing(
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

    override fun handleMappingError(receivedException: Exception, obj: Any): Nothing {
        val message = "Error while mapping a ${obj::class.simpleName} object."
        logWarn(
            context = javaClass,
            message = message
        )
        throw LicensingMappingException(message, receivedException)
    }

}