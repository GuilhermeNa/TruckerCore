package com.example.truckercore.shared.errors.mapping

import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
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
class InvalidForMappingException : MappingException {

    private var _dto: Dto? = null
    val dto get() = _dto

    private var _entity: Entity? = null
    val entity get() = _entity

    private var _exception: Exception
    val exception get() = _exception

    /**
     * Constructor for creating an exception related to a DTO mapping failure.
     *
     * @param dto The DTO that caused the error.
     * @param cause The underlying exception that caused the error.
     */
    constructor(dto: Dto, cause: Exception) : super() {
        _dto = dto
        _exception = cause
    }

    /**
     * Constructor for creating an exception related to an Entity mapping failure.
     *
     * @param entity The Entity that caused the error.
     * @param cause The underlying exception that caused the error.
     */
    constructor(entity: Entity, cause: Exception) : super() {
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

    /**
     * Generates a message describing the error that occurred during the mapping process.
     * The message varies depending on whether a DTO or an Entity was involved in the error.
     *
     * @return A message describing the error.
     */
    fun message() =
        if (dto != null) {
            "Some error occurred while mapping a dto to entity for dto: $dto"
        } else {
            "Some error occurred while mapping an entity to dto for entity: $entity"
        }

}

