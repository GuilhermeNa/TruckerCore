package com.example.truckercore.unit.modules.employee.driver.validator

import com.example.truckercore._test_data_provider.TestDriverDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.employee.driver.errors.DriverValidationException
import com.example.truckercore.modules.employee.driver.validator.DriverValidationStrategy
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.UnexpectedValidatorInputException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.sealeds.ValidatorInput
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

class DriverValidationStrategyTest {

    private lateinit var validator: DriverValidationStrategy

    companion object {
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

    @BeforeEach
    fun setup() {
        mockStaticLog()
        validator = DriverValidationStrategy()
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
    fun `validateDto() should throw DriverValidationException when there is any invalid field`(
        input: ValidatorInput.DtoInput
    ) {
        val exception = assertThrows<DriverValidationException> {
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

        val exception = assertThrows<UnexpectedValidatorInputException> {
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
    fun `validateEntity() should throw DriverValidationException when there is any invalid field`(
        input: ValidatorInput.EntityInput
    ) {
        val exception = assertThrows<DriverValidationException> {
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
        val unexpectedEntity = object : Entity {
            override val businessCentralId: String = ""
            override val id: String = ""
            override val lastModifierId = ""
            override val creationDate = LocalDateTime.now()
            override val lastUpdate = LocalDateTime.now()
            override val persistenceStatus = PersistenceStatus.PERSISTED
        }
        val unexpectedEntityInput = ValidatorInput.EntityInput(unexpectedEntity)

        val exception = assertThrows<UnexpectedValidatorInputException> {
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
    fun `validateForCreation() should throw DriverValidationException when there is any invalid field`(
        input: ValidatorInput.EntityInput
    ) {
        val exception = assertThrows<DriverValidationException> {
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
        val unexpectedEntity = object : Entity {
            override val businessCentralId: String = ""
            override val id: String = ""
            override val lastModifierId = ""
            override val creationDate = LocalDateTime.now()
            override val lastUpdate = LocalDateTime.now()
            override val persistenceStatus = PersistenceStatus.PERSISTED
        }
        val unexpectedEntityInput = ValidatorInput.EntityInput(unexpectedEntity)

        val exception = assertThrows<UnexpectedValidatorInputException> {
            validator.validateForCreation(unexpectedEntityInput)
        }

        assertTrue(
            exception.message?.run {
                contains("Awaited input was") &&
                        contains("and received")
            } ?: false
        )
    }

}