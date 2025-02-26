package com.example.truckercore.unit.shared.modules.file.validator

import com.example.truckercore._test_data_provider.TestFileDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.validation.IllegalValidationArgumentException
import com.example.truckercore.shared.errors.validation.InvalidObjectException
import com.example.truckercore.shared.modules.file.dto.FileDto
import com.example.truckercore.shared.modules.file.entity.File
import com.example.truckercore.shared.modules.file.validator.StorageFileValidationStrategy
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

internal class FileValidationStrategyTest : KoinTest {

    private val validator: StorageFileValidationStrategy by inject()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single { StorageFileValidationStrategy() }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()

        @JvmStatic
        fun arrValidDtosForValidationRules() =
            TestFileDataProvider.arrValidDtosForValidationRules().map {
                ValidatorInput.DtoInput(it)
            }

        @JvmStatic
        fun arrInvalidDtosForValidationRules() =
            TestFileDataProvider.arrInvalidDtosForValidationRules().map {
                ValidatorInput.DtoInput(it)
            }

        @JvmStatic
        fun arrValidEntitiesForValidationRules() =
            TestFileDataProvider.arrValidEntitiesForValidationRules().map {
                ValidatorInput.EntityInput(it)
            }

        @JvmStatic
        fun arrInvalidEntitiesForValidationRules() =
            TestFileDataProvider.arrInvalidEntitiesForValidationRules().map {
                ValidatorInput.EntityInput(it)
            }

        @JvmStatic
        fun arrValidEntitiesForCreationRules() =
            TestFileDataProvider.arrValidEntitiesForCreationRules().map {
                ValidatorInput.EntityInput(it)
            }

        @JvmStatic
        fun arrInvalidEntitiesForCreationRules() =
            TestFileDataProvider.arrInvalidEntitiesForCreationRules().map {
                ValidatorInput.EntityInput(it)
            }
    }

    @ParameterizedTest
    @MethodSource("arrValidDtosForValidationRules")
    fun `validateDto() should call processDtoValidationRules() and don't throw exception`(
        input: ValidatorInput.DtoInput
    ) {
        val mock = spyk(validator, recordPrivateCalls = true)

        assertDoesNotThrow {
            mock.validateDto(input)
        }

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
        assertTrue(exception.dto is FileDto)
    }

    @Test
    fun `validateDto() should throw IllegalValidationArgumentException when receive an unexpected dto class`() {
        val unexpectedDto = TestUserDataProvider.getBaseDto()
        val unexpectedDtoInput = ValidatorInput.DtoInput(unexpectedDto)

        val exception = assertThrows<IllegalValidationArgumentException> {
            validator.validateDto(unexpectedDtoInput)
        }

        assertTrue(exception.received == UserDto::class)
        assertTrue(exception.expected == FileDto::class)
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
        // Call
        val exception = assertThrows<InvalidObjectException> {
            validator.validateEntity(input)
        }

        // Arrange
        assertTrue(exception.entity is File)
    }

    @Test
    fun `validateEntity() should throw IllegalValidationArgumentException when receive an unexpected entity class`() {
        val unexpectedEntity = TestUserDataProvider.getBaseEntity()
        val unexpectedEntityInput = ValidatorInput.EntityInput(unexpectedEntity)

        val exception = assertThrows<IllegalValidationArgumentException> {
            validator.validateEntity(unexpectedEntityInput)
        }

        assertTrue(exception.received == User::class)
        assertTrue(exception.expected == File::class)
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
        // Call
        val exception = assertThrows<InvalidObjectException> {
            validator.validateForCreation(input)
        }

        // Assertions
        assertTrue(exception.entity is File)
    }

    @Test
    fun `validateForCreation() should throw IllegalValidationArgumentException when receive an unexpected entity class`() {
        val unexpectedEntity = TestUserDataProvider.getBaseEntity()
        val unexpectedEntityInput = ValidatorInput.EntityInput(unexpectedEntity)

        val exception = assertThrows<IllegalValidationArgumentException> {
            validator.validateForCreation(unexpectedEntityInput)
        }

        // Assertions
        assertTrue(exception.received == User::class)
        assertTrue(exception.expected == File::class)
    }

}
