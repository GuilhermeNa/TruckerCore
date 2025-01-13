package com.example.truckercore.unit.modules.business_central.validator

import com.example.truckercore._test_data_provider.TestBusinessCentralDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.errors.BusinessCentralValidationException
import com.example.truckercore.modules.business_central.validator.BusinessCentralValidationStrategy
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal class BusinessCentralValidationStrategyTestImpl {

    private lateinit var validator: BusinessCentralValidationStrategy
    private lateinit var entity: BusinessCentral
    private lateinit var dto: BusinessCentralDto

    companion object {
        @JvmStatic
        fun arrValidEntitiesForValidationRules() =
            TestBusinessCentralDataProvider.arrValidEntitiesForValidationRules()

        @JvmStatic
        fun arrValidEntitiesForCreationRules() =
            TestBusinessCentralDataProvider.arrValidEntitiesForCreationRules()

        @JvmStatic
        fun arrInvalidDtosForValidationRules() =
            TestBusinessCentralDataProvider.arrInvalidDtosForValidationRules()

        @JvmStatic
        fun arrInvalidEntitiesForValidationRules() =
            TestBusinessCentralDataProvider.arrInvalidEntitiesForValidationRules()

        @JvmStatic
        fun arrInvalidEntitiesForCreationRules() =
            TestBusinessCentralDataProvider.arrInvalidEntitiesForCreationRules()

        @JvmStatic
        fun arrValidDtosForValidationRules() =
            TestBusinessCentralDataProvider.arrValidDtosForValidationRules()

    }

    @BeforeEach
    fun setup() {
        mockStaticLog()
        validator = BusinessCentralValidationStrategy()
        entity = TestBusinessCentralDataProvider.getBaseEntity()
        dto = TestBusinessCentralDataProvider.getBaseDto()
    }

    @ParameterizedTest
    @MethodSource("arrValidDtosForValidationRules")
    fun `validateDto() should call processDtoValidationRules() and don't throw exception`() {
        // Object
        val mock = spyk(validator, recordPrivateCalls = true)

        // Call
        assertDoesNotThrow {
            mock.validateDto(dto)
        }

        // Assertion
        verify {
            mock["processDtoValidationRules"](dto)
        }

    }

    @ParameterizedTest
    @MethodSource("arrInvalidDtosForValidationRules")
    fun `validateDto() throw BusinessCentralValidationException when there is any invalid field`(
        pDto: BusinessCentralDto
    ) {
        // Call
        assertThrows<BusinessCentralValidationException> {
            validator.validateDto(pDto)
        }
    }

    @ParameterizedTest
    @MethodSource("arrValidEntitiesForValidationRules")
    fun `validateEntity() should call processEntityValidationRules() and don't throw exception`(
        pEntity: BusinessCentral
    ) {
        // Object
        val mock = spyk(validator, recordPrivateCalls = true)

        // Call
        assertDoesNotThrow {
            mock.validateEntity(pEntity)
        }

        // Assertion
        verify {
            mock["processEntityValidationRules"](pEntity)
        }

    }

    @ParameterizedTest
    @MethodSource("arrInvalidEntitiesForValidationRules")
    fun `validateEntity() should throw BusinessCentralValidationException when there is any invalid field`(
        pEntity: BusinessCentral
    ) {
        // Call
        assertThrows<BusinessCentralValidationException> {
            validator.validateEntity(pEntity)
        }
    }

    @ParameterizedTest
    @MethodSource("arrValidEntitiesForCreationRules")
    fun `validateForCreation() should call processEntityCreationRules() and don't throw exception`(
        pEntity: BusinessCentral
    ) {
        // Object
        val mock = spyk(validator, recordPrivateCalls = true)

        // Call
        assertDoesNotThrow {
            mock.validateForCreation(pEntity)
        }

        // Assertion
        verify {
            mock["processEntityCreationRules"](pEntity)
        }

    }

    @ParameterizedTest
    @MethodSource("arrInvalidEntitiesForCreationRules")
    fun `validateForCreation() throw BusinessCentralValidationException when there is any invalid field`(
        pEntity: BusinessCentral
    ) {
        // Call
        assertThrows<BusinessCentralValidationException> {
            validator.validateForCreation(pEntity)
        }
    }

}