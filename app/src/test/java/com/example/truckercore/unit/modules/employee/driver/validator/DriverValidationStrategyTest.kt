package com.example.truckercore.unit.modules.employee.driver.validator

import com.example.truckercore._test_data_provider.TestBusinessCentralDataProvider
import com.example.truckercore._test_data_provider.TestDriverDataProvider
import com.example.truckercore._test_data_provider.TestPersonalDataDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.employee.driver.dto.DriverDto
import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.modules.employee.driver.validator.DriverValidationStrategy
import com.example.truckercore.shared.errors.validation.IllegalValidationArgumentException
import com.example.truckercore.shared.errors.validation.InvalidObjectException
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.utils.sealeds.ValidatorInput
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class DriverValidationStrategyTest : KoinTest {

    private val validator: DriverValidationStrategy by inject()

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()

            startKoin {
                modules(
                    module {
                        single { DriverValidationStrategy() }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()

        @JvmStatic
        fun arrValidDtosForValidationRules() =
            TestDriverDataProvider.arrValidDtosForValidationRules().map {
                ValidatorInput.DtoInput(it)
            }

        @JvmStatic
        fun arrValidEntitiesForValidationRules() =
            TestDriverDataProvider.arrValidEntitiesForValidationRules().map {
                ValidatorInput.EntityInput(it)
            }

        @JvmStatic
        fun arrValidEntitiesForCreationRules() =
            TestDriverDataProvider.arrValidEntitiesForCreationRules().map {
                ValidatorInput.EntityInput(it)
            }

        @JvmStatic
        fun arrInvalidDtosForValidationRules() =
            TestDriverDataProvider.arrInvalidDtosForValidationRules().map {
                ValidatorInput.DtoInput(it)
            }

        @JvmStatic
        fun arrInvalidEntitiesForValidationRules() =
            TestDriverDataProvider.arrInvalidEntitiesForValidationRules().map {
                ValidatorInput.EntityInput(it)
            }

        @JvmStatic
        fun arrInvalidEntitiesForCreationRules() =
            TestDriverDataProvider.arrInvalidEntitiesForCreationRules().map {
                ValidatorInput.EntityInput(it)
            }
    }

    @ParameterizedTest
    @MethodSource("arrValidDtosForValidationRules")
    fun `validateDto() should call processDtoValidationRules() and don't throw exception`(
        input: ValidatorInput.DtoInput
    ) {
        val mock = spyk(validator, recordPrivateCalls = true)

        // Call
        assertDoesNotThrow {
            mock.validateDto(input)
        }

        // Assertion
        verify {
            mock["processDtoValidationRules"](input.dto)
        }
    }

    @ParameterizedTest
    @MethodSource("arrInvalidDtosForValidationRules")
    fun `validateDto() should throw InvalidObjectException when there is any invalid field`(
        input: ValidatorInput.DtoInput
    ) {
        val exception = assertThrows<InvalidObjectException> {
            validator.validateDto(input)
        }

        // Assertions
        assertTrue(exception.dto is DriverDto)
    }

    @Test
    fun `validateDto() should throw UnexpectedValidatorInputException when receive an unexpected dto class`() {
        val unexpectedDto = TestBusinessCentralDataProvider.getBaseDto()
        val unexpectedDtoInput = ValidatorInput.DtoInput(unexpectedDto)

        val exception = assertThrows<IllegalValidationArgumentException> {
            validator.validateDto(unexpectedDtoInput)
        }

        assertTrue(exception.received == BusinessCentralDto::class)
        assertTrue(exception.expected == DriverDto::class)
    }

    @ParameterizedTest
    @MethodSource("arrValidEntitiesForValidationRules")
    fun `validateEntity() should call processEntityValidationRules() and don't throw exception`(
        input: ValidatorInput.EntityInput
    ) {
        val mock = spyk(validator, recordPrivateCalls = true)

        assertDoesNotThrow {
            mock.validateEntity(input)
        }

        verify {
            mock["processEntityValidationRules"](input.entity)
        }
    }

    @ParameterizedTest
    @MethodSource("arrInvalidEntitiesForValidationRules")
    fun `validateEntity() should throw InvalidObjectException when there is any invalid field`(
        input: ValidatorInput.EntityInput
    ) {
        val exception = assertThrows<InvalidObjectException> {
            validator.validateEntity(input)
        }

        assertTrue(exception.entity is Driver)
    }

    @Test
    fun `validateEntity() should throw IllegalValidationArgumentException when receive an unexpected entity class`() {
        val unexpectedEntity = TestPersonalDataDataProvider.getBaseEntity()
        val unexpectedEntityInput = ValidatorInput.EntityInput(unexpectedEntity)

        val exception = assertThrows<IllegalValidationArgumentException> {
            validator.validateEntity(unexpectedEntityInput)
        }

        assertTrue(exception.expected == Driver::class)
        assertTrue(exception.received == PersonalData::class)
    }

    @ParameterizedTest
    @MethodSource("arrValidEntitiesForCreationRules")
    fun `validateForCreation() should call processEntityCreationRules() and don't throw exception`(
        input: ValidatorInput.EntityInput
    ) {
        val mock = spyk(validator, recordPrivateCalls = true)

        assertDoesNotThrow {
            mock.validateForCreation(input)
        }

        verify {
            mock["processEntityCreationRules"](input.entity)
        }
    }

    @ParameterizedTest
    @MethodSource("arrInvalidEntitiesForCreationRules")
    fun `validateForCreation() should throw InvalidObjectException when there is any invalid field`(
        input: ValidatorInput.EntityInput
    ) {
        val exception = assertThrows<InvalidObjectException> {
            validator.validateForCreation(input)
        }

        assertTrue(exception.entity is Driver)
    }

    @Test
    fun `validateForCreation() should throw UnexpectedValidatorInputException when receive an unexpected entity class`() {
        val unexpectedEntity = TestBusinessCentralDataProvider.getBaseEntity()
        val unexpectedEntityInput = ValidatorInput.EntityInput(unexpectedEntity)

        val exception = assertThrows<IllegalValidationArgumentException> {
            validator.validateForCreation(unexpectedEntityInput)
        }

        assertTrue(exception.received == BusinessCentral::class)
        assertTrue(exception.expected == Driver::class)
    }

}
