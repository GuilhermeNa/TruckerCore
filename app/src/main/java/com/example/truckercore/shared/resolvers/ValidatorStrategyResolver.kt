package com.example.truckercore.shared.resolvers

import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.validator.BusinessCentralValidationStrategy
import com.example.truckercore.shared.abstractions.ValidatorStrategy
import com.example.truckercore.shared.errors.StrategyNotFoundException
import com.example.truckercore.shared.sealeds.ValidatorInput

/**
 * A Class responsible for resolving the appropriate validation strategy.
 */
internal class ValidatorStrategyResolver {

    /**
     * A map that associates class types to their respective [ValidatorStrategy] implementations.
     * This map is used to resolve the correct validation strategy based on the type of the input object.
     */
    private val strategies = mapOf(
        Pair(BusinessCentral::class.java, BusinessCentralValidationStrategy()),
        Pair(BusinessCentralDto::class.java, BusinessCentralValidationStrategy()),
    )

    /**
     * Resolves and returns the appropriate [ValidatorStrategy] based on the provided [ValidatorInput].
     *
     * This method checks the type of the input object (either a [DtoInput] or an [EntityInput]) and calls
     * the corresponding `resolve` method to retrieve the validation strategy associated with the input's class type.
     *
     * @param input The input object, which can be of type [ValidatorInput.DtoInput] or [ValidatorInput.EntityInput].
     * @return The corresponding [ValidatorStrategy] that will be used for validating the input.
     * @throws StrategyNotFoundException If no strategy is found for the provided class type, an exception is thrown.
     */
    fun execute(input: ValidatorInput): ValidatorStrategy = when (input) {
        is ValidatorInput.DtoInput -> resolve(input.dto)
        is ValidatorInput.EntityInput -> resolve(input.entity)
    }

    // Fetch and return the correct strategy or throw exception
    private fun <T> resolve(klass: T): ValidatorStrategy {
        return strategies[klass!!::class.java]
            ?: throw StrategyNotFoundException("The strategy for ${klass.javaClass} is not registered.")
    }

}