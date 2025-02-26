package com.example.truckercore.unit.modules.fleet.shared.module.licensing.aggregations

import com.example.truckercore._test_data_provider.TestFileDataProvider
import com.example.truckercore._test_data_provider.TestLicensingDataProvider
import com.example.truckercore.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.shared.errors.InvalidStateException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LicensingWithFileTest {

    private val licensingProvider = TestLicensingDataProvider
    private val fileProvider = TestFileDataProvider

    @Test
    fun `should create LicensingWithFile when files belong to the licensing`() {
        // Arrange
        val licensing = licensingProvider.getBaseEntity().copy(id = LICENSING_ID)
        val file1 = fileProvider.getBaseEntity().copy(parentId = LICENSING_ID)
        val file2 = fileProvider.getBaseEntity().copy(parentId = LICENSING_ID)

        // Act
        val licensingWithFile = LicensingWithFile(
            licensing = licensing,
            files = listOf(file1, file2)
        )

        // Assert
        val firstFileParentId = licensingWithFile.files[0].parentId
        val secondFileParentId = licensingWithFile.files[1].parentId

        assertEquals(licensing.id, firstFileParentId)
        assertEquals(licensing.id, secondFileParentId)
    }

    @Test
    fun `should throw InvalidStateException when file parentId does not match licensing id`() {
        // Arrange
        val licensing = licensingProvider.getBaseEntity().copy(id = LICENSING_ID)
        val file = fileProvider.getBaseEntity().copy(parentId = "wrongLicensingId")

        // Act & Assert
        assertThrows<InvalidStateException> {
            LicensingWithFile(
                licensing = licensing,
                files = listOf(file)
            )
        }
    }

    @Test
    fun `should create LicensingWithFile with empty files`() {
        // Arrange
        val licensing = licensingProvider.getBaseEntity().copy(id = LICENSING_ID)

        // Act
        val licensingWithFile = LicensingWithFile(
            licensing = licensing,
            files = emptyList()
        )

        // Assert
        assertTrue(licensingWithFile.files.isEmpty())
    }

    @Test
    fun `parentId should return the licensing parent id`() {
        // Arrange
        val licensing = licensingProvider.getBaseEntity().copy(id = LICENSING_ID)
        val file1 = fileProvider.getBaseEntity().copy(parentId = LICENSING_ID)

        // Act
        val licensingWF = LicensingWithFile(
            licensing = licensing,
            files = emptyList()
        )

        // Assertion
        assertEquals(licensingWF.parentId, licensing.parentId)

    }

    companion object {
        private const val LICENSING_ID = "licensingId"
    }

}