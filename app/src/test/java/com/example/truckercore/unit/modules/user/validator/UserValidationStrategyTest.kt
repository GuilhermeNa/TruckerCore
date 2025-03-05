package com.example.truckercore.unit.modules.user.validator

import com.example.truckercore._test_data_provider.TestDriverDataProvider
import com.example.truckercore._test_data_provider.TestTruckDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.fleet.truck.dto.TruckDto
import com.example.truckercore.modules.fleet.truck.entity.Truck
import com.example.truckercore.modules.person.employee.driver.entity.Driver
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.validator.UserValidationStrategy
import com.example.truckercore.shared.errors.validation.IllegalValidationArgumentException
import com.example.truckercore.shared.errors.validation.InvalidObjectException
import com.example.truckercore.shared.utils.sealeds.ValidatorInput
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class UserValidationStrategyTest : KoinTest {

    private val validator: UserValidationStrategy by inject()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()

            startKoin {
                modules(
                    module {
                        single { UserValidationStrategy() }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()

        @JvmStatic
        fun arrValidDtosForValidationRules() =
            TestUserDataProvider.arrValidDtosForValidationRules().map {
                ValidatorInput.DtoInput(it)
            }

        @JvmStatic
        fun arrValidEntitiesForValidationRules() =
            TestUserDataProvider.arrValidEntitiesForValidationRules().map {
                ValidatorInput.EntityInput(it)
            }

        @JvmStatic
        fun arrValidEntitiesForCreationRules() =
            TestUserDataProvider.arrValidEntitiesForCreationRules().map {
                ValidatorInput.EntityInput(it)
            }

        @JvmStatic
        fun arrInvalidDtosForValidationRules() =
            TestUserDataProvider.arrInvalidDtosForValidationRules().map {
                ValidatorInput.DtoInput(it)
            }

        @JvmStatic
        fun arrInvalidEntitiesForValidationRules() =
            TestUserDataProvider.arrInvalidEntitiesForValidationRules().map {
                ValidatorInput.EntityInput(it)
            }

        @JvmStatic
        fun arrInvalidEntitiesForCreationRules() =
            TestUserDataProvider.arrInvalidEntitiesForCreationRules().map {
                ValidatorInput.EntityInput(it)
            }
    }

    @ParameterizedTest
    @MethodSource("arrValidDtosForValidationRules")
    fun `validateDto() should call processDtoValidationRules() and don't throw exception`(
        input: ValidatorInput.DtoInput
    ) {
        // Object
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
        // Call
        val exception = assertThrows<InvalidObjectException> {
            validator.validateDto(input)
        }

        // Assertions
        assertTrue(exception.dto is UserDto)
    }

    @Test
    fun `validateDto() should throw IllegalValidationArgumentException when receive an unexpected dto class`() {
        // Object
        val unexpectedDto = TestTruckDataProvider.getBaseDto()
        val unexpectedDtoInput = ValidatorInput.DtoInput(unexpectedDto)

        // Call
        val exception = assertThrows<IllegalValidationArgumentException> {
            validator.validateDto(unexpectedDtoInput)
        }

        assertTrue(exception.received == TruckDto::class)
        assertTrue(exception.expected == UserDto::class)
    }

    @ParameterizedTest
    @MethodSource("arrValidEntitiesForValidationRules")
    fun `validateEntity() should call processEntityValidationRules() and don't throw exception`(
        input: ValidatorInput.EntityInput
    ) {
        // Object
        val mock = spyk(validator, recordPrivateCalls = true)

        // Call
        assertDoesNotThrow {
            mock.validateEntity(input)
        }

        // Assertion
        verify {
            mock["processEntityValidationRules"](input.entity)
        }
    }

    @ParameterizedTest
    @MethodSource("arrInvalidEntitiesForValidationRules")
    fun `validateEntity() should throw InvalidObjectException when there is any invalid field`(
        input: ValidatorInput.EntityInput
    ) {
        // Call
        val exception = assertThrows<InvalidObjectException> {
            validator.validateEntity(input)
        }

        assertTrue(exception.entity is User)
    }

    @Test
    fun `validateEntity() should throw UnexpectedValidatorInputException when receive an unexpected entity class`() {
        // Object
        val unexpectedEntity = TestTruckDataProvider.getBaseEntity()
        val unexpectedEntityInput = ValidatorInput.EntityInput(unexpectedEntity)

        // Call
        val exception = assertThrows<IllegalValidationArgumentException> {
            validator.validateEntity(unexpectedEntityInput)
        }

        assertTrue(exception.received == Truck::class)
        assertTrue(exception.expected == User::class)
    }

    @ParameterizedTest
    @MethodSource("arrValidEntitiesForCreationRules")
    fun `validateForCreation() should call processEntityCreationRules() and don't throw exception`(
        input: ValidatorInput.EntityInput
    ) {
        // Object
        val mock = spyk(validator, recordPrivateCalls = true)

        // Call
        assertDoesNotThrow {
            mock.validateForCreation(input)
        }

        // Assertion
        verify {
            mock["processEntityCreationRules"](input.entity)
        }

    }

    @ParameterizedTest
    @MethodSource("arrInvalidEntitiesForCreationRules")
    fun `validateForCreation() throw UserValidationException when there is any invalid field`(
        input: ValidatorInput.EntityInput
    ) {
        // Call
        val exception = assertThrows<InvalidObjectException> {
            validator.validateForCreation(input)
        }

        // Assertions
        assertTrue(exception.entity is User)
    }

    @Test
    fun `validateForCreation() should throw IllegalValidationArgumentException when receive an unexpected entity class`() {
        // Object
        val unexpectedEntity = TestDriverDataProvider.getBaseEntity()
        val unexpectedEntityInput = ValidatorInput.EntityInput(unexpectedEntity)

        // Call
        val exception = assertThrows<IllegalValidationArgumentException> {
            validator.validateForCreation(unexpectedEntityInput)
        }

        // Arrange
        assertTrue(exception.received == Driver::class)
        assertTrue(exception.expected == User::class)
    }

}
