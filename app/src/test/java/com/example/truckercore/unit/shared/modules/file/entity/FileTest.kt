package com.example.truckercore.unit.shared.modules.file.entity

import com.example.truckercore._test_data_provider.TestFileDataProvider
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.net.MalformedURLException
import kotlin.test.assertNotNull

class FileTest {

    private val fileProvider = TestFileDataProvider

    @Test
    fun `should create File object with valid data`() {
        // Act
        val file = fileProvider.getBaseDto()

        // Assert
        assertNotNull(file)
    }

    @Test
    fun `should throw exception when URL is invalid`() {
        // Act && Assert
        assertThrows<MalformedURLException> {
            fileProvider.getBaseEntity().copy(url = "invalid-url")
        }

    }

}