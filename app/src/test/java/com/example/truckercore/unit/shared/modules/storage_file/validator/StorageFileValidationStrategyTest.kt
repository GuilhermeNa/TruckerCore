/*
package com.example.truckercore.unit.shared.modules.storage_file.validator

import com.example.truckercore._test_data_provider.TestStorageFileDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.validation.IllegalValidationArgumentException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.modules.storage_file.validator.StorageFileValidationStrategy
import com.example.truckercore.shared.utils.sealeds.ValidatorInput
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalDateTime
import java.util.Date

internal class StorageFileValidationStrategyTest {

    private val validator = StorageFileValidationStrategy()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
        }

        @JvmStatic
        fun arrValidDtosForValidationRules() =
            TestStorageFileDataProvider.arrValidDtosForValidationRules().map {
                ValidatorInput.DtoInput(it)
            }

        @JvmStatic
        fun arrInvalidDtosForValidationRules() =
            TestStorageFileDataProvider.arrInvalidDtosForValidationRules().map {
                ValidatorInput.DtoInput(it)
            }

        @JvmStatic
        fun arrValidEntitiesForValidationRules() =
            TestStorageFileDataProvider.arrValidEntitiesForValidationRules().map {
                ValidatorInput.EntityInput(it)
            }

        @JvmStatic
        fun arrInvalidEntitiesForValidationRules() =
            TestStorageFileDataProvider.arrInvalidEntitiesForValidationRules().map {
                ValidatorInput.EntityInput(it)
            }

        @JvmStatic
        fun arrValidEntitiesForCreationRules() =
            TestStorageFileDataProvider.arrValidEntitiesForCreationRules().map {
                ValidatorInput.EntityInput(it)
            }

        @JvmStatic
        fun arrInvalidEntitiesForCreationRules() =
            TestStorageFileDataProvider.arrInvalidEntitiesForCreationRules().map {
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
    fun `validateDto() should throw StorageFileValidationException when there is any invalid field`(
        input: ValidatorInput.DtoInput
    ) {
        val exception = assertThrows<StorageFileValidationException> {
            validator.validateDto(input)
        }

        assert(exception.message?.contains("Invalid") == true)
        assert(exception.message?.contains("Missing or invalid fields") == true)
    }

    @Test
    fun `validateDto() should throw UnexpectedValidatorInputException when receive an unexpected dto class`() {
        val unexpectedDto = object : Dto {
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
        val unexpectedDtoInput = ValidatorInput.DtoInput(unexpectedDto)

        val exception = assertThrows<IllegalValidationArgumentException> {
            validator.validateDto(unexpectedDtoInput)
        }

        assert(exception.message?.contains("Awaited input was") == true)
        assert(exception.message?.contains("and received") == true)
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
    fun `validateEntity() should throw StorageFileValidationException when there is any invalid field`(
        input: ValidatorInput.EntityInput
    ) {
        val exception = assertThrows<StorageFileValidationException> {
            validator.validateEntity(input)
        }

        assert(exception.message?.contains("Invalid") == true)
        assert(exception.message?.contains("Missing or invalid fields") == true)
    }

    @Test
    fun `validateEntity() should throw UnexpectedValidatorInputException when receive an unexpected entity class`() {
        val unexpectedEntity = object : Entity {
            override val businessCentralId: String = "dummy"
            override val id: String = "dummy"
            override val lastModifierId = "dummy"
            override val creationDate = LocalDateTime.now()
            override val lastUpdate = LocalDateTime.now()
            override val persistenceStatus = PersistenceStatus.PERSISTED
        }
        val unexpectedEntityInput = ValidatorInput.EntityInput(unexpectedEntity)

        val exception = assertThrows<IllegalValidationArgumentException> {
            validator.validateEntity(unexpectedEntityInput)
        }

        assert(exception.message?.contains("Awaited input was") == true)
        assert(exception.message?.contains("and received") == true)
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
    fun `validateForCreation() should throw StorageFileValidationException when there is any invalid field`(
        input: ValidatorInput.EntityInput
    ) {
        val exception = assertThrows<StorageFileValidationException> {
            validator.validateForCreation(input)
        }

        assert(exception.message?.contains("Invalid") == true)
        assert(exception.message?.contains("Missing or invalid fields") == true)
    }

    @Test
    fun `validateForCreation() should throw UnexpectedValidatorInputException when receive an unexpected entity class`() {
        val unexpectedEntity = object : Entity {
            override val businessCentralId: String = "dummy"
            override val id: String = "dummy"
            override val lastModifierId = "dummy"
            override val creationDate = LocalDateTime.now()
            override val lastUpdate = LocalDateTime.now()
            override val persistenceStatus = PersistenceStatus.PERSISTED
        }
        val unexpectedEntityInput = ValidatorInput.EntityInput(unexpectedEntity)

        val exception = assertThrows<IllegalValidationArgumentException> {
            validator.validateForCreation(unexpectedEntityInput)
        }

        assert(exception.message?.contains("Awaited input was") == true)
        assert(exception.message?.contains("and received") == true)
    }

}*/
