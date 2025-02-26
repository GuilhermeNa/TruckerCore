package com.example.truckercore.shared.interfaces

import com.example.truckercore.shared.errors.validation.ValidationException
import com.example.truckercore.shared.utils.sealeds.ValidatorInput

/**
 * This interface defines the contract for validating both [Entity] and [Dto] objects.
 * It provides methods for validating general correctness as well as for specific validation scenarios, such as creation.
 * Any class implementing this interface must provide concrete validation logic for each of the defined methods.
 */
internal interface ValidatorStrategyI {

    /**
     * Validates a [Dto] object for general correctness.
     *
     * This method checks that the provided DTO complies with the required business rules, constraints, or formatting.
     * The exact validation logic should be implemented by the class implementing this interface.
     *
     * @param input The [ValidatorInput.DtoInput] object containing the [Dto] to be validated.
     * @throws ValidationException If the DTO fails any validation checks.
     */
    fun validateDto(input: ValidatorInput.DtoInput)

    /**
     * Validates an [Entity] object for general correctness.
     *
     * This method checks that the provided entity complies with the required business rules, constraints, or formatting.
     * The exact validation logic should be implemented by the class implementing this interface.
     *
     * @param input The [ValidatorInput.EntityInput] object containing the [Entity] to be validated.
     * @throws ValidationException If the entity fails any validation checks.
     */
    fun validateEntity(input: ValidatorInput.EntityInput)

    /**
     * Validates an [Entity] object specifically for creation.
     *
     * This method checks that the provided entity complies with the necessary business rules or constraints during its creation process.
     * This may include additional validation specific to entity creation, such as checking for required fields or unique constraints.
     *
     * @param input The [ValidatorInput.EntityInput] object containing the [Entity] to be validated for creation.
     * @throws ValidationException If the entity fails any validation checks for creation.
     */
    fun validateForCreation(input: ValidatorInput.EntityInput)

}