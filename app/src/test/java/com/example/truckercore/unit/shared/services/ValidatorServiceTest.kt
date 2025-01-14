package com.example.truckercore.unit.shared.services

import com.example.truckercore._test_data_provider.TestBusinessCentralDataProvider
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.validator.BusinessCentralValidationStrategy
import com.example.truckercore.shared.abstractions.ValidatorStrategy
import com.example.truckercore.shared.resolvers.ValidatorStrategyResolver
import com.example.truckercore.shared.sealeds.ValidatorInput
import com.example.truckercore.shared.services.ValidatorService
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ValidatorServiceTest {

    private lateinit var entity: BusinessCentral
    private lateinit var dto: BusinessCentralDto
    private lateinit var resolver: ValidatorStrategyResolver
    private lateinit var _strategy: ValidatorStrategy
    private lateinit var service: ValidatorService

    @BeforeEach
    fun setup() {
        entity = TestBusinessCentralDataProvider.getBaseEntity()
        dto = TestBusinessCentralDataProvider.getBaseDto()
        resolver = mockk(relaxed = true)
        _strategy = mockk(relaxed = true)
        service = spyk(
            ValidatorService(resolver, _strategy),
            recordPrivateCalls = true
        )
    }

    @Test
    fun `validateDto() should call resolver and validate`() {
        // Object
        val input = ValidatorInput.DtoInput(dto)

        // Behavior
        every { resolver.execute(input) } returns _strategy

        // Call
        service.validateDto(dto)

        verifyOrder {
            service["setStrategy"](input)
            _strategy.validateDto(dto)
        }
    }

    @Test
    fun `validateEntity() should call resolver and validate`() {
        // Object
        val input = ValidatorInput.EntityInput(entity)

        // Behavior
        every { resolver.execute(input) } returns _strategy

        // Call
        service.validateEntity(entity)

        verifyOrder {
            service["setStrategy"](input)
            _strategy.validateEntity(entity)
        }
    }

    @Test
    fun `validateForCreation() should call resolver and validate`() {
        // Object
        val input = ValidatorInput.EntityInput(entity)

        // Behavior
        every { resolver.execute(input) } returns _strategy

        // Call
        service.validateForCreation(entity)

        verifyOrder {
            service["setStrategy"](input)
            _strategy.validateForCreation(entity)
        }
    }

}