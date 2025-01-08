package com.example.truckercore.unit.modules.central.validator

import com.example.truckercore._test_data_provider.TestCentralDataProvider
import com.example.truckercore.modules.central.dto.CentralDto
import com.example.truckercore.modules.central.validator.CentralCreationValidator
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.InvalidStateException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal class CentralCreationValidatorTest {

    companion object {

        @JvmStatic
        fun getArrForTestingCreationValidator() =
            TestCentralDataProvider.getArrForTestingCreationValidator()

    }

    @Test
    fun `execute() should pass without exceptions when the central is valid for creation`() {
        // Object
        val dto = TestCentralDataProvider.getBaseDto().copy(
            centralId = null,
            id = null,
            persistenceStatus = PersistenceStatus.PENDING.name
        )

        // Call
        assertDoesNotThrow {
            CentralCreationValidator.execute(dto)
        }

    }

    @ParameterizedTest
    @MethodSource("getArrForTestingCreationValidator")
    fun `execute() should throw InvalidStateException when the central is invalid for creation`(
        dto: CentralDto
    ) {
        assertThrows<IllegalArgumentException> {
            CentralCreationValidator.execute(dto)
        }
    }

}