package com.example.truckercore.shared.errors.mapping

import com.example.truckercore.shared.errors.abstractions.MappingException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity

class IllegalMappingArgumentException : MappingException { //TODO terminar de organizar as exceções genericas

    constructor(dto: Dto) : super() {
        _dto = dto
    }

    constructor(entity: Entity) : super() {
        _entity = entity
    }

    private var _dto: Dto? = null
    val dto get() = _dto

    private var _entity: Entity? = null
    val entity get() = _entity



    fun getMessage() = if (dto != null) {
        "Expected a LicensingDto for mapping but received: ${dto?.javaClass?.simpleName}"
    } else {
        "Expected a Licensing for mapping but received: ${entity?.javaClass?.simpleName}"
    }

}