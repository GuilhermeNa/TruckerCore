package com.example.truckercore.model.shared.interfaces

import com.example.truckercore.model.shared.interfaces.data.dto.Dto
import com.example.truckercore.model.shared.interfaces.data.entity.Entity

/**
 * An interface that defines the contract for mapping between [Dto] and [Entity] objects.
 *
 * This interface provides two functions to convert between a Data Transfer Object (DTO) and an Entity.
 * Implementations of this interface will provide the logic for converting between these two types.
 */
internal interface Mapper {

    /**
     * Converts a [Dto] object to an [Entity] object.
     *
     * This function maps the properties of the provided DTO to the corresponding fields of the entity.
     *
     * @param dto The [Dto] object to be converted to an [Entity].
     * @return The corresponding [Entity] object.
     */
    fun toEntity(dto: Dto): Entity

    /**
     * Converts an [Entity] object to a [Dto] object.
     *
     * This function maps the properties of the provided entity to the corresponding fields of the DTO.
     *
     * @param entity The [Entity] object to be converted to a [Dto].
     * @return The corresponding [Dto] object.
     */
    fun toDto(entity: Entity): Dto

}