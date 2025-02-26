package com.example.truckercore.unit.modules.fleet.trailer.aggregations

import com.example.truckercore._test_data_provider.TestFileDataProvider
import com.example.truckercore._test_data_provider.TestLicensingDataProvider
import com.example.truckercore._test_data_provider.TestTrailerDataProvider
import com.example.truckercore.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.modules.fleet.trailer.aggregations.TrailerWithDetails
import com.example.truckercore.shared.errors.InvalidStateException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TrailerWirthDetailsTest {

    private val trailerProvider = TestTrailerDataProvider
    private val licensingProvider = TestLicensingDataProvider
    private val fileProvider = TestFileDataProvider

    @Test
    fun `should create TrailerWithDetails with valid data`() {
        // Arrange
        val trailer = trailerProvider.getBaseEntity().copy(id = TRAILER_ID)
        val licensing = licensingProvider.getBaseEntity()
            .copy(id = LICENSING_ID, parentId = TRAILER_ID)
        val file = fileProvider.getBaseEntity().copy(parentId = LICENSING_ID)
        val licensingWF = LicensingWithFile(licensing, listOf(file))

        // Act
        val trailerWD = TrailerWithDetails(trailer, listOf(licensingWF))

        // Assert
        trailerWD.licensingWithFiles.forEach { lwf ->
            assertEquals(lwf.parentId, trailerWD.trailer.id)
        }

    }

    @Test
    fun `should create TrailerWithDetails with empty list when licensing is not provided`() {
        // Arrange
        val trailer = trailerProvider.getBaseEntity().copy(id = TRAILER_ID)

        // Act
        val trailerWD = TrailerWithDetails(trailer)

        // Assert
        assertTrue(trailerWD.licensingWithFiles.isEmpty())

    }

    @Test
    fun `should throw InvalidStateException when licensing does not belong to the provided trailer`() {
        // Arrange
        val trailer = trailerProvider.getBaseEntity().copy(id = TRAILER_ID)
        val licensing = licensingProvider.getBaseEntity()
            .copy(parentId = "wrongTrailerId")  // Invalid parentId
        val licensingWF = LicensingWithFile(licensing, emptyList())

        // Act & Assert
        assertThrows<InvalidStateException> {
            TrailerWithDetails(
                trailer = trailer,
                licensingWithFiles = listOf(licensingWF)
            )
        }

    }

    @Test
    fun `should return truckId from trailer`() {
        // Arrange
        val trailer = trailerProvider.getBaseEntity().copy(id = TRAILER_ID, truckId = "truckId123")

        // Act
        val trailerWithDetails = TrailerWithDetails(trailer = trailer)

        // Assert
        assertEquals("truckId123", trailerWithDetails.truckId)
    }

    companion object {
        private const val TRAILER_ID = "trailerId"
        private const val LICENSING_ID = "licensingId"
    }

}