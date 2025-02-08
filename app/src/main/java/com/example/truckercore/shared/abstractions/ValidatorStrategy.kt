package com.example.truckercore.shared.abstractions

import com.example.truckercore.shared.errors.InvalidObjectException
import com.example.truckercore.shared.errors.UnexpectedValidatorInputException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.interfaces.ValidatorStrategyI
import kotlin.reflect.KClass

/**
 * This abstract class implements the [ValidatorStrategyI] interface and provides some common functionality for validation strategies.
 * It defines common methods for handling invalid fields and unexpected input, which can be used by subclasses.
 * Subclasses must implement the validation logic for both [Dto] and [Entity] types.
 */
internal abstract class ValidatorStrategy : ValidatorStrategyI {

    /**
     * Processes the validation rules for a [Dto] object.
     * This method must be implemented by the subclass to define the specific validation logic for the [Dto].
     *
     * @param dto The [Dto] object to be validated.
     */
    protected abstract fun processDtoValidationRules(dto: Dto)

    /**
     * Processes the validation rules for an [Entity] object.
     * This method must be implemented by the subclass to define the specific validation logic for the [Entity].
     *
     * @param entity The [Entity] object to be validated.
     */
    protected abstract fun processEntityValidationRules(entity: Entity)

    /**
     * Processes the validation rules for an [Entity] object when it is being created.
     * This method must be implemented by the subclass to define the specific validation logic for entity creation.
     *
     * @param entity The [Entity] object to be validated for creation.
     */
    protected abstract fun processEntityCreationRules(entity: Entity)

    /**
     * Handles an error scenario where the input received is not of the expected type.
     * This method is used to throw an exception when there is a mismatch between the expected
     * and received types during validation.
     *
     * This is an internal helper method that is used when validation encounters a type mismatch.
     *
     * @param expected The expected class type that the input should adhere to.
     * @param received The class type of the actual input that was received.
     * @throws UnexpectedValidatorInputException Thrown when there is a type mismatch.
     */
    protected fun handleUnexpectedInputError(expected: KClass<*>, received: KClass<*>) {
        throw UnexpectedValidatorInputException(expected = expected, received = received)
    }

    /**
     * Handles validation errors by throwing an exception that contains the list of invalid fields.
     * This method is invoked when an object fails validation due to one or more invalid fields.
     *
     * This method helps encapsulate error reporting and ensures that invalid fields are properly listed
     * when the validation fails.
     *
     * @param obj The class type of the object being validated (either [Dto] or [Entity]).
     * @param fields The list of invalid fields that failed the validation. This will contain the names of the fields that caused the error.
     * @throws InvalidObjectException Thrown when validation errors are found on the object.
     */
    protected fun handleValidationErrors(obj: KClass<*>, fields: List<String>) {
        throw InvalidObjectException(obj = obj, invalidFields = fields)
    }

}