package com.example.truckercore.model.shared.errors.mapping

import com.example.truckercore.model.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.model.shared.interfaces.Dto
import com.example.truckercore.model.shared.interfaces.Entity

/**
 * Custom exception for errors related to mapping in [Licensing].
 *
 * This exception can be thrown when mapping data between objects fails due to various reasons,
 * such as invalid data or configuration issues, making it easier to diagnose and handle mapping errors.
 *
 * @param obj The [Entity] or [Dto] related to the error.
 * @property cause The underlying exception that caused this exception to be thrown.
 */
class InvalidForMappingException private constructor() : MappingException() {

    private var _dto: Dto? = null
    val dto get() = _dto

    private var _entity: Entity? = null
    val entity get() = _entity

    private lateinit var _exception: Exception
    val exception get() = _exception

    /**
     * Constructor for creating an exception related to a DTO mapping failure.
     *
     * @param dto The DTO that caused the error.
     * @param cause The underlying exception that caused the error.
     */
    constructor(dto: Dto, cause: Exception) : this() {
        _dto = dto
        _exception = cause
    }

    /**
     * Constructor for creating an exception related to an Entity mapping failure.
     *
     * @param entity The Entity that caused the error.
     * @param cause The underlying exception that caused the error.
     */
    constructor(entity: Entity, cause: Exception) : this() {
        _entity = entity
        _exception = cause
    }

    /**
     * Retrieves the object (DTO or Entity) that caused the error.
     * Returns either the DTO or the Entity, depending on which was passed during the exception creation.
     *
     * @return The DTO or Entity that caused the error.
     */
    fun getObject() = dto ?: entity

    override val message: String
        get() = dto?.let {
            "Some error occurred while mapping a dto to entity for dto: $it"
        } ?: "Some error occurred while mapping an entity to dto for entity: $entity"

}

