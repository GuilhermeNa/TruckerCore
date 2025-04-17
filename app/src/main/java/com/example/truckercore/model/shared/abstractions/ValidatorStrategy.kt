package com.example.truckercore.model.shared.abstractions

import com.example.truckercore.model.shared.interfaces.ValidatorStrategyI

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

}