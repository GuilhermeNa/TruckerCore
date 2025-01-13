package com.example.truckercore.unit.domain.personal_data.configs

import com.example.truckercore._test_data_provider.TestPersonalDataDataProvider
import com.example.truckercore.shared.modules.personal_data.dtos.PersonalDataDto
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

internal class PersonalDataConfigsTestImpl {

    companion object {
        @JvmStatic
        fun getArrWithMissingFields() = TestPersonalDataDataProvider.getArrWithMissingFields()
    }

    @Test
    fun `validateRequiredFields() should not throw exception when all required fields are present`() {
        // Object
        val dto = TestPersonalDataDataProvider.getBaseDto()

        //Call
        assertDoesNotThrow {
            com.example.truckercore.shared.modules.personal_data.configs.PersonalDataConfigs.validateRequiredFields(dto)
        }

    }

    @ParameterizedTest
    @MethodSource("getArrWithMissingFields")
    fun `should throw IllegalArgumentException when there is missing fields`(
        dto: PersonalDataDto
    ) {
        // Objects
        val validFields = mutableListOf<String>()
        dto::class.memberProperties.forEach { property ->
            val value = (property as KProperty1<PersonalDataDto, Any?>).get(dto)
            if (value != null) validFields.add(property.name)
        }

        // Call
        val exception = assertThrows<IllegalArgumentException> {
            com.example.truckercore.shared.modules.personal_data.configs.PersonalDataConfigs.validateRequiredFields(dto)
        }

        //Assert
        assertFalse(validFields.contains(exception.message))
    }

}