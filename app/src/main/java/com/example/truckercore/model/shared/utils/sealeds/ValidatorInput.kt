package com.example.truckercore.model.shared.utils.sealeds

import com.example.truckercore.model.shared.interfaces.data.dto.Dto
import com.example.truckercore.model.shared.interfaces.data.entity.Entity

/**
 * A sealed class representing different types of input for the validation strategy.
 *
 * This sealed class can represent either a Data Transfer Object (DTO) or an Entity input, which
 * are the two types of objects that can be validated using a specific validation strategy.
 */
sealed class ValidatorInput {

    /**
     * Represents the input as a Data Transfer Object (DTO).
     *
     * @param dto The DTO object to be validated.
     */
    data class DtoInput(val dto: Dto): ValidatorInput()

    /**
     * Represents the input as an Entity.
     *
     * @param entity The Entity object to be validated.
     */
    data class EntityInput(val entity: Entity): ValidatorInput()

}