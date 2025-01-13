package com.example.truckercore.shared.interfaces

/**
 * Interface for mapping between [Entity] and [Dto] types.
 *
 * This interface defines the contract for converting between an [Entity] and its corresponding [Dto] and vice versa.
 * Any class implementing this interface must provide concrete implementations for the `toDto` and `toEntity` methods,
 * which handle the actual conversion logic.
 *
 * @param E The type of the [Entity] being mapped.
 * @param D The type of the [Dto] being mapped.
 *
 */
internal interface MapperI<E, D> {

    /**
     * Converts an [Entity] to a [Dto].
     *
     * @param entity The entity to be converted into a DTO.
     * @return The resulting DTO representation of the given entity.
     */
    fun toDto(entity: E): D

    /**
     * Converts a [Dto] to an [Entity].
     *
     * @param dto The DTO to be converted into an entity.
     * @return The resulting entity representation of the given DTO.
     */
    fun toEntity(dto: D): E

}