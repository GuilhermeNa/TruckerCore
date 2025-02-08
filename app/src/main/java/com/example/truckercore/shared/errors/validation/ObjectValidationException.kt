package com.example.truckercore.shared.errors.validation

import com.example.truckercore.shared.errors.abstractions.ValidationException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity

class ObjectValidationException : ValidationException {

    constructor(entity: Entity, fields: List<String>) : super() {
        _entity = entity
        invalidFields.addAll(fields)
    }

    constructor(dto: Dto, fields: List<String>) : super() {
        _dto = dto
        invalidFields.addAll(fields)
    }

    private val invalidFields = mutableListOf<String>()

    private var _entity: Entity? = null
    val entity get() = _entity

    private var _dto: Dto? = null
    val dto get() = _dto

    fun getMessage(): String {
        val objectName =
            if (dto != null) dto!!.javaClass.simpleName
            else entity!!.javaClass.simpleName

        return "Some error occurred while validating an object: " +
                "[$objectName] -> invalid field $invalidFields."
    }

}

