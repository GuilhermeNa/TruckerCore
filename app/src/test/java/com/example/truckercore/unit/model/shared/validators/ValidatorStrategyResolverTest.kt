package com.example.truckercore.unit.model.shared.validators

import com.example.truckercore._test_data_provider.TestBusinessCentralDataProvider
import com.example.truckercore.model.modules.business_central.validator.BusinessCentralValidationStrategy
import com.example.truckercore.model.shared.errors.strategy.StrategyNotFoundException
import com.example.truckercore.model.shared.interfaces.Dto
import com.example.truckercore.model.shared.resolvers.ValidatorStrategyResolver
import com.example.truckercore.model.shared.utils.sealeds.ValidatorInput
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.Date

internal class ValidatorStrategyResolverTest {

    private val resolver = ValidatorStrategyResolver()

    @Test
    fun `execute() should resolve the correct strategy for DtoInput`() {
        // Arrange
        val dtoInput = ValidatorInput.DtoInput(
            TestBusinessCentralDataProvider.getBaseDto()
        )

        // Call
        val strategy = resolver.execute(dtoInput)

        // Assert
        assert(strategy is BusinessCentralValidationStrategy)
    }

    @Test
    fun `execute() should resolve the correct strategy for EntityInput`() {
        // Arrange
        val entityInput = ValidatorInput.EntityInput(
            TestBusinessCentralDataProvider.getBaseEntity()
        )

        // Act
        val strategy = resolver.execute(entityInput)

        // Assert
        assert(strategy is BusinessCentralValidationStrategy)
    }

    @Test
    fun `execute() should throw StrategyNotFoundException if strategy is not found in the map`() {
        // Objects
        val unregisteredDto = object : Dto {
            override val businessCentralId: String? = null
            override val id: String? = null
            override val lastModifierId: String? = null
            override val creationDate: Date? = null
            override val lastUpdate: Date? = null
            override val persistenceStatus: String? = null
            override fun initializeId(newId: String): Dto {
                TODO()
            }
        }
        val unregisteredInput = ValidatorInput.DtoInput(unregisteredDto)

        // Assert
        val exception = assertThrows<StrategyNotFoundException> {
            resolver.execute(unregisteredInput)
        }

        assertTrue(exception.message?.contains("The strategy for") ?: false)

    }

}