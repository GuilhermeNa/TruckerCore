package com.example.truckercore.shared.services

import com.example.truckercore.shared.abstractions.ValidatorStrategy
import com.example.truckercore.shared.errors.StrategyNotFoundException
import com.example.truckercore.shared.errors.abstractions.ValidationException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.resolvers.ValidatorStrategyResolver
import com.example.truckercore.shared.sealeds.ValidatorInput

/**
 * A service class responsible for validating [Entity] and [Dto] objects using a provided validation strategy.
 *
 * This class delegates the validation process to a specific [ValidatorStrategy] implementation, which defines the rules
 * for validating the data. It provides methods for validating both DTOs and entities, as well as for validating entities
 * specifically when they are being created.
 *
 * @param resolver The class responsible for determining which strategy will be used.
 * @param _strategy The validation strategy to be used for validating entities and DTOs.
 *
 */
internal class ValidatorService(
    private val resolver: ValidatorStrategyResolver,
    private var _strategy: ValidatorStrategy
) {

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
        val input = ValidatorInput.DtoInput(dto)
        setStrategy(input)
        _strategy.validateDto(input)
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
        val input = ValidatorInput.EntityInput(entity)
        setStrategy(input)
        _strategy.validateEntity(input)
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
        val input = ValidatorInput.EntityInput(entity)
        setStrategy(input)
        _strategy.validateForCreation(input)
    }

    /**
     * Sets the validation strategy based on the provided [ValidatorInput].
     *
     * This method is responsible for resolving and assigning the correct [ValidatorStrategy] based on the input type.
     * It uses the [ValidatorStrategyResolver] to determine the appropriate strategy and updates the current strategy
     * to ensure that subsequent validation operations are performed using the correct rules.
     *
     * @param input The input object that provides the data to determine the appropriate validation strategy. This can be
     *              either a [ValidatorInput.DtoInput] containing a [Dto] or a [ValidatorInput.EntityInput] containing an [Entity].
     * @throws StrategyNotFoundException If no strategy is found for the provided input type, an exception is thrown.
     */
    private fun setStrategy(input: ValidatorInput) {
        val newStrategy = resolver.execute(input)
        _strategy = newStrategy
    }

}