package com.example.truckercore.shared.errors.validation

import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity

/**
 * Custom exception class that represents an error when an object (either an entity or a DTO)
 * fails validation due to one or more invalid fields.
 *
 * This class is designed to handle validation failures involving both entities and data transfer objects (DTOs).
 */
class InvalidObjectException private constructor() : ValidationException() {

    private val invalidFields = mutableListOf<String>()

    private var _entity: Entity? = null
    val entity get() = _entity

    private var _dto: Dto? = null
    val dto get() = _dto

    /**
     * Creates an instance of the exception for an entity that failed validation.
     *
     * @param entity The entity that failed validation.
     * @param fields The list of invalid fields.
     */
    constructor(entity: Entity, fields: List<String>) : this() {
        _entity = entity
        invalidFields.addAll(fields)
    }

    /**
     * Creates an instance of the exception for a DTO that failed validation.
     *
     * @param dto The DTO that failed validation.
     * @param fields The list of invalid fields.
     */
    constructor(dto: Dto, fields: List<String>) : this() {
        _dto = dto
        invalidFields.addAll(fields)
    }

    /**
     * Returns a message describing the validation failure.
     *
     * The message will indicate which object (either entity or DTO) failed validation and which fields
     * are invalid.
     *
     * @return A string message detailing the validation error.
     */
    fun message(): String {
        val objectName =
            if (dto != null) dto!!.javaClass.simpleName
            else entity!!.javaClass.simpleName

        return "Some error occurred while validating an object: " +
                "[$objectName] -> invalid field $invalidFields."
    }

}

