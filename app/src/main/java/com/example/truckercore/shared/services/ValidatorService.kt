package com.example.truckercore.shared.services

import com.example.truckercore.shared.abstractions.ValidatorStrategy
import com.example.truckercore.shared.errors.ValidationException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.interfaces.ValidatorStrategyI

/**
 * A service class responsible for validating [Entity] and [Dto] objects using a provided validation strategy.
 *
 * This class delegates the validation process to a specific [ValidatorStrategyI] implementation, which defines the rules
 * for validating the data. It provides methods for validating both DTOs and entities, as well as for validating entities
 * specifically when they are being created.
 *
 * @param E The type of the [Entity] being validated.
 * @param D The type of the [Dto] being validated.
 * @param strategy The validation strategy to be used for validating entities and DTOs.
 *
 */
internal class ValidatorService(private val strategy: ValidatorStrategy) {

    /**
     * Validates a [Dto] object using the provided validation strategy.
     *
     * This method delegates the validation of the DTO to the strategy's `validateDto` method.
     * It ensures that the DTO complies with the necessary business rules and validation constraints.
     *
     * @param dto The DTO object to be validated.
     * @throws ValidationException If the DTO fails any validation checks.
     */
    fun validateDto(dto: Dto) {
        strategy.validateDto(dto)
    }

    /**
     * Validates an [Entity] object using the provided validation strategy.
     *
     * This method delegates the validation of the entity to the strategy's `validateEntity` method.
     * It ensures that the entity complies with the necessary business rules and validation constraints.
     *
     * @param entity The entity object to be validated.
     * @throws ValidationException If the entity fails any validation checks.
     */
    fun validateEntity(entity: Entity) {
        strategy.validateEntity(entity)
    }

    /**
     * Validates an [Entity] object specifically for creation using the provided validation strategy.
     *
     * This method delegates the validation of the entity for creation to the strategy's `validateForCreation` method.
     * It checks if the entity complies with the necessary business rules and validation constraints during its creation process.
     *
     * @param entity The entity object to be validated for creation.
     * @throws ValidationException If the entity fails any validation checks for creation.
     */
    fun validateForCreation(entity: Entity) {
        strategy.validateForCreation(entity)
    }

}