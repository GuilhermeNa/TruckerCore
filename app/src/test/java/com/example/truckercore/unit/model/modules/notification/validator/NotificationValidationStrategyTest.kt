package com.example.truckercore.unit.model.modules.notification.validator

import com.example.truckercore._test_data_provider.TestNotificationDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore.model.modules.notification.dto.NotificationDto
import com.example.truckercore.model.modules.notification.entity.Notification
import com.example.truckercore.model.modules.notification.validator.NotificationValidationStrategy
import com.example.truckercore.model.modules.user.dto.UserDto
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.errors.validation.IllegalValidationArgumentException
import com.example.truckercore.model.shared.errors.validation.InvalidObjectException
import com.example.truckercore.model.shared.utils.sealeds.ValidatorInput
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal class NotificationValidationStrategyTest {

    private val validator = NotificationValidationStrategy()

    companion object {
        @JvmStatic
        fun arrValidDtosForValidationRules() =
            TestNotificationDataProvider.arrValidDtosForValidationRules().map {
                ValidatorInput.DtoInput(it)
            }

        @JvmStatic
        fun arrValidEntitiesForValidationRules() =
            TestNotificationDataProvider.arrValidEntitiesForValidationRules().map {
                ValidatorInput.EntityInput(it)
            }

        @JvmStatic
        fun arrValidEntitiesForCreationRules() =
            TestNotificationDataProvider.arrValidEntitiesForCreationRules().map {
                ValidatorInput.EntityInput(it)
            }

        @JvmStatic
        fun arrInvalidDtosForValidationRules() =
            TestNotificationDataProvider.arrInvalidDtosForValidationRules().map {
                ValidatorInput.DtoInput(it)
            }

        @JvmStatic
        fun arrInvalidEntitiesForValidationRules() =
            TestNotificationDataProvider.arrInvalidEntitiesForValidationRules().map {
                ValidatorInput.EntityInput(it)
            }

        @JvmStatic
        fun arrInvalidEntitiesForCreationRules() =
            TestNotificationDataProvider.arrInvalidEntitiesForCreationRules().map {
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
        assertTrue(exception.dto is NotificationDto)
    }

    @Test
    fun `validateDto() should throw IllegalValidationArgumentException when receive an unexpected dto class`() {
        // Object
        val unexpectedDto = TestUserDataProvider.getBaseDto()
        val unexpectedDtoInput = ValidatorInput.DtoInput(unexpectedDto)

        // Call
        val exception = assertThrows<IllegalValidationArgumentException> {
            validator.validateDto(unexpectedDtoInput)
        }

        assertTrue(exception.received == UserDto::class)
        assertTrue(exception.expected == NotificationDto::class)
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

        assertTrue(exception.entity is Notification)
    }

    @Test
    fun `validateEntity () should throw IllegalValidationArgumentException when receive an unexpected dto class`() {
        // Object
        val unexpectedEntity = TestUserDataProvider.getBaseEntity()
        val unexpectedEntityInput = ValidatorInput.EntityInput(unexpectedEntity)

        // Call
        val exception = assertThrows<IllegalValidationArgumentException> {
            validator.validateEntity(unexpectedEntityInput)
        }

        assertTrue(exception.received == User::class)
        assertTrue(exception.expected == Notification::class)
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
    fun `validateForCreation() throw InvalidObjectException when there is any invalid field`(
        input: ValidatorInput.EntityInput
    ) {
        // Call
        val exception = assertThrows<InvalidObjectException> {
            validator.validateForCreation(input)
        }

        // Assertions
        assertTrue(exception.entity is Notification)

    }

    @Test
    fun `validateForCreation() should throw IllegalValidationArgumentException when receive an unexpected dto class`() {
        // Object
        val unexpectedEntity = TestUserDataProvider.getBaseEntity()
        val unexpectedEntityInput = ValidatorInput.EntityInput(unexpectedEntity)

        // Call
        val exception = assertThrows<IllegalValidationArgumentException> {
            validator.validateForCreation(unexpectedEntityInput)
        }

        // Arrange
        assertTrue(exception.received == User::class)
        assertTrue(exception.expected == Notification::class)
    }

}