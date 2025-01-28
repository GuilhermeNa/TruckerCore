package com.example.truckercore.unit.domain.storage_file.configs

import com.example.truckercore._test_data_provider.TestStorageFileDataProvider
import com.example.truckercore.shared.modules.storage_file.dto.StorageFileDto
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

internal class StorageFileConfigsTestImpl {

    companion object {
        @JvmStatic
        fun getArrWithMissingFields() = TestStorageFileDataProvider.getArrWithMissingFields()
    }

    @Test
    fun `validateRequiredFields() should not throw exception when all required fields are present`() {
        // Object
        val dto = TestStorageFileDataProvider.getBaseDto()

        //Call
        assertDoesNotThrow {
            StorageFileConfigs.validateRequiredFields(dto)
        }

    }

    @ParameterizedTest
    @MethodSource("getArrWithMissingFields")
    fun `should throw IllegalArgumentException when there is missing fields`(
        dto: StorageFileDto
    ) {
        // Objects
        val validFields = mutableListOf<String>()
        dto::class.memberProperties.forEach { property ->
            val value = (property as KProperty1<StorageFileDto, Any?>).get(dto)
            if (value != null) validFields.add(property.name)
        }

        // Call
        val exception = assertThrows<IllegalArgumentException> {
            StorageFileConfigs.validateRequiredFields(dto)
        }

        //Assert
        assertFalse(validFields.contains(exception.message))
    }

}