/*
package com.example.truckercore.unit.shared.modules.personal_data.validator

import com.example.truckercore._test_data_provider.TestPersonalDataDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.validation.IllegalValidationArgumentException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.modules.personal_data.validator.PersonalDataValidationStrategy
import com.example.truckercore.shared.utils.sealeds.ValidatorInput
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalDateTime
import java.util.Date

class PersonalDataValidationStrategyTest {

    private lateinit var validator: PersonalDataValidationStrategy

    companion object {
        @JvmStatic
        fun arrValidDtosForValidationRules() =
            TestPersonalDataDataProvider.arrValidDtosForValidationRules().map {
                ValidatorInput.DtoInput(it)
            }

        @JvmStatic
        fun arrInvalidDtosForValidationRules() =
            TestPersonalDataDataProvider.arrInvalidDtosForValidationRules().map {
                ValidatorInput.DtoInput(it)
            }

        @JvmStatic
        fun arrValidEntitiesForValidationRules() =
            TestPersonalDataDataProvider.arrValidEntitiesForValidationRules().map {
                ValidatorInput.EntityInput(it)
            }

        @JvmStatic
        fun arrInvalidEntitiesForValidationRules() =
            TestPersonalDataDataProvider.arrInvalidEntitiesForValidationRules().map {
                ValidatorInput.EntityInput(it)
            }

        @JvmStatic
        fun arrValidEntitiesForCreationRules() =
            TestPersonalDataDataProvider.arrValidEntitiesForCreationRules().map {
                ValidatorInput.EntityInput(it)
            }

        @JvmStatic
        fun arrInvalidEntitiesForCreationRules() =
            TestPersonalDataDataProvider.arrInvalidEntitiesForCreationRules().map {
                ValidatorInput.EntityInput(it)
            }
    }

    @BeforeEach
    fun setup() {
        mockStaticLog()
        validator = PersonalDataValidationStrategy()
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
    fun `validateDto() should throw PersonalDataValidationException when there is any invalid field`(
        input: ValidatorInput.DtoInput
    ) {
        // Call
        val exception = assertThrows<PersonalDataValidationException> {
            validator.validateDto(input)
        }

        assertTrue(
            exception.message?.run {
                contains("Invalid") &&
                        contains("Missing or invalid fields")
            } ?: false
        )
    }

    @Test
    fun `validateDto() should throw UnexpectedValidatorInputException when receive an unexpected dto class`() {
        // Object
        val unexpectedDto = object : Dto {
            override val businessCentralId: String? = null
            override val id: String? = null
            override val lastModifierId: String? = null
            override val creationDate: Date? = null
            override val lastUpdate: Date? = null
            override val persistenceStatus: String? = null
            override fun initializeId(newId: String): Dto { TODO() }
        }
        val unexpectedDtoInput = ValidatorInput.DtoInput(unexpectedDto)

        // Call
        val exception = assertThrows<IllegalValidationArgumentException> {
            validator.validateDto(unexpectedDtoInput)
        }

        assertTrue(
            exception.message?.run {
                contains("Awaited input was") &&
                        contains("and received")
            } ?: false
        )
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
    fun `validateEntity() should throw PersonalDataValidationException when there is any invalid field`(
        input: ValidatorInput.EntityInput
    ) {
        // Call
        val exception = assertThrows<PersonalDataValidationException> {
            validator.validateEntity(input)
        }

        assertTrue(
            exception.message?.run {
                contains("Invalid") &&
                        contains("Missing or invalid fields")
            } ?: false
        )
    }

    @Test
    fun `validateEntity() should throw UnexpectedValidatorInputException when receive an unexpected entity class`() {
        // Object
        val unexpectedEntity = object : Entity {
            override val businessCentralId: String = ""
            override val id: String = ""
            override val lastModifierId = ""
            override val creationDate = LocalDateTime.now()
            override val lastUpdate = LocalDateTime.now()
            override val persistenceStatus = PersistenceStatus.PERSISTED
        }
        val unexpectedEntityInput = ValidatorInput.EntityInput(unexpectedEntity)

        // Call
        val exception = assertThrows<IllegalValidationArgumentException> {
            validator.validateEntity(unexpectedEntityInput)
        }

        assertTrue(
            exception.message?.run {
                contains("Awaited input was") &&
                        contains("and received")
            } ?: false
        )
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
    fun `validateForCreation() should throw PersonalDataValidationException when there is any invalid field`(
        input: ValidatorInput.EntityInput
    ) {
        // Call
        val exception = assertThrows<PersonalDataValidationException> {
            validator.validateForCreation(input)
        }

        assertTrue(
            exception.message?.run {
                contains("Invalid") &&
                        contains("Missing or invalid fields")
            } ?: false
        )
    }

    @Test
    fun `validateForCreation() should throw UnexpectedValidatorInputException when receive an unexpected entity class`() {
        // Object
        val unexpectedEntity = object : Entity {
            override val businessCentralId: String = ""
            override val id: String = ""
            override val lastModifierId = ""
            override val creationDate = LocalDateTime.now()
            override val lastUpdate = LocalDateTime.now()
            override val persistenceStatus = PersistenceStatus.PERSISTED
        }
        val unexpectedEntityInput = ValidatorInput.EntityInput(unexpectedEntity)

        // Call
        val exception = assertThrows<IllegalValidationArgumentException> {
            validator.validateForCreation(unexpectedEntityInput)
        }

        assertTrue(
            exception.message?.run {
                contains("Awaited input was") &&
                        contains("and received")
            } ?: false
        )
    }


}*/
