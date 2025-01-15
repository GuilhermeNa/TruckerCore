package com.example.truckercore.shared.abstractions

import com.example.truckercore.modules.business_central.errors.BusinessCentralValidationException
import com.example.truckercore.shared.errors.UnexpectedValidatorInputException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.interfaces.ValidatorStrategyI
import com.example.truckercore.shared.utils.expressions.logError
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
     * Handles errors related to invalid fields during validation.
     *
     * @param obj The class type of the object being validated (either [Dto] or [Entity]).
     * @param fields The list of invalid fields.
     * @throws BusinessCentralValidationException if there are invalid fields.
     */
    protected fun <T : KClass<*>> handleInvalidFieldsErrors(obj: T, fields: List<String>) {
        val objectName = obj.simpleName
        val className = this.javaClass.simpleName

        logError("$className: There are invalid fields when validating the $objectName.")

        throw BusinessCentralValidationException(
            "Invalid $objectName. Missing or invalid fields: ${fields.joinToString(", ")}."
        )
    }

    /**
     * Handles unexpected input errors when the input type does not match the expected type.
     *
     * @param expectedClass The expected class type (either [Dto] or [Entity]).
     * @param inputClass The actual class type of the input.
     * @throws UnexpectedValidatorInputException if the input type is not what was expected.
     */
    protected fun <E : KClass<*>, I : KClass<*>> handleUnexpectedInputError(
        expectedClass: E,
        inputClass: I
    ) {
        val className = this.javaClass.simpleName
        val expected = expectedClass.simpleName
        val received = inputClass.simpleName

        logError("$className: Awaited input was $expected, and received $received.")

        throw UnexpectedValidatorInputException("Awaited input was $expected, and received $received.")
    }

}