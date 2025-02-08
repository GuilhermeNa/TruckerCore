package com.example.truckercore.modules.fleet.shared.module.licensing.errors

import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.shared.errors.abstractions.MappingException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity

/**
 * Custom exception for errors related to mapping in [Licensing].
 *
 * This exception can be thrown when mapping data between objects fails due to various reasons,
 * such as invalid data or configuration issues, making it easier to diagnose and handle mapping errors.
 *
 * @param obj The [Entity] or [Dto] related to the error.
 * @property cause The underlying exception that caused this exception to be thrown.
 */
class LicensingMappingException : MappingException {

    private var _dto: Dto? = null
    val dto get() = _dto

    private var _entity: Entity? = null
    val entity get() = _entity

    private var _exception: Exception? = null
    val exception get() = _exception

    constructor(dto: Dto, cause: Exception): super() {
        _dto = dto
        _exception = cause
    }

    constructor(entity: Entity, cause: Exception): super() {
        _entity = entity
        _exception = cause
    }

}

