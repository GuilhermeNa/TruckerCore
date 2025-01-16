package com.example.truckercore.shared.abstractions

import com.example.truckercore.shared.errors.MappingException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.interfaces.MapperI

/**
 * An abstract class responsible for mapping between [Entity] and [Dto] types.
 *
 * This class provides the base functionality for converting between entities and DTOs (Data Transfer Objects).
 * Subclasses must provide concrete implementations for the methods that define how entities are mapped to DTOs and vice versa.
 *
 * @param E The type of the [Entity] being mapped.
 * @param D The type of the [Dto] being mapped.
 *
 * @see MapperI
 */
internal abstract class Mapper<E : Entity, D : Dto> : MapperI<E, D> {

    /**
     * Maps an [Entity] to a [Dto].
     *
     * This method is intended to be overridden by subclasses to define the specific logic of mapping an entity to its corresponding DTO.
     *
     * @param entity The entity to be converted to a DTO.
     * @return The resulting [Dto] representation of the given [Entity].
     */
    protected abstract fun mapEntityToDto(entity: E): D

    /**
     * Maps a [Dto] to an [Entity].
     *
     * This method is intended to be overridden by subclasses to define the specific logic of mapping a DTO to its corresponding entity.
     *
     * @param dto The DTO to be converted to an entity.
     * @return The resulting [Entity] representation of the given [Dto].
     */
    protected abstract fun mapDtoToEntity(dto: D): E

    /**
     * Handles errors that occur during the mapping process.
     *
     * This method is called whenever an exception is thrown during the conversion of an entity to a DTO or vice versa.
     * It allows the caller to decide which exception will be thrown, enabling specific exceptions to be thrown instead of a generic one.
     *
     * @param receivedException The exception that was thrown during the mapping operation.
     * @param obj The object ([Entity] or [Dto]) that caused the exception.
     *
     * @throws [MappingException]
     */
    protected abstract fun handleMappingError(receivedException: Exception, obj: Any): Nothing

}