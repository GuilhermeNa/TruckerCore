package com.example.truckercore.shared.interfaces

import com.example.truckercore.shared.errors.ValidationException
import com.example.truckercore.shared.services.ValidatorService

/**
 * This interface defines the contract for validating both [Entity] and [Dto] objects.
 * It provides methods for validating general correctness as well as for specific validation scenarios, such as creation.
 * Any class implementing this interface must provide concrete validation logic for each of the defined methods.
 *
 * @param E The type of the [Entity] being validated.
 * @param D The type of the [Dto] being validated.
 *
 * @see ValidatorService
 */
internal interface ValidatorStrategyI {

    /**
     * Validates a [Dto] object for general correctness.
     *
     * This method checks that the provided DTO complies with the required business rules, constraints, or formatting.
     * The exact validation logic should be implemented by the class implementing this interface.
     *
     * @param dto The DTO object to be validated.
     * @throws ValidationException If the DTO fails any validation checks.
     */
    fun validateDto(dto: Dto)

    /**
     * Validates an [Entity] object for general correctness.
     *
     * This method checks that the provided entity complies with the required business rules, constraints, or formatting.
     * The exact validation logic should be implemented by the class implementing this interface.
     *
     * @param entity The entity object to be validated.
     * @throws ValidationException If the entity fails any validation checks.
     */
    fun validateEntity(entity: Entity)

    /**
     * Validates an [Entity] object for correctness specifically when it is being created.
     *
     * This method checks that the provided entity complies with the necessary business rules or constraints for creation.
     * This may include additional validation specific to entity creation, such as checking for required fields or unique constraints.
     *
     * @param entity The entity object to be validated for creation.
     * @throws ValidationException If the entity fails any validation checks for creation.
     */
    fun validateForCreation(entity: Entity)

}