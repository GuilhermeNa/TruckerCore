package com.example.truckercore.unit.modules.fleet.truck.aggregations

import com.example.truckercore._test_data_provider.TestLicensingDataProvider
import com.example.truckercore._test_data_provider.TestTrailerDataProvider
import com.example.truckercore._test_data_provider.TestTruckDataProvider
import com.example.truckercore.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.modules.fleet.trailer.aggregations.TrailerWithDetails
import com.example.truckercore.modules.fleet.truck.aggregation.TruckWithDetails
import com.example.truckercore.shared.errors.InvalidStateException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TruckWithDetailsTest {

    private val truckProvider = TestTruckDataProvider
    private val trailerProvider = TestTrailerDataProvider
    private val licensingProvider = TestLicensingDataProvider

    @Test
    fun `should create TruckWithDetails successfully when trailers and licensing belong to the truck`() {
        // Arrange
        val truck = truckProvider.getBaseEntity().copy(id = TRUCK_ID)
        val trailerWD = TrailerWithDetails(
            trailer = trailerProvider.getBaseEntity().copy(truckId = TRUCK_ID)
        )
        val licensingWF = LicensingWithFile(
            licensing = licensingProvider.getBaseEntity().copy(parentId = TRUCK_ID)
        )

        // Act
        val truckWD = TruckWithDetails(
            truck = truck,
            trailersWithDetails = listOf(trailerWD),
            licensingWithFiles = listOf(licensingWF)
        )

        // Assert
        assertEquals(truckWD.truck.id, truckWD.trailersWithDetails.first().truckId)
        assertEquals(truckWD.truck.id, truckWD.licensingWithFiles.first().parentId)
    }

    @Test
    fun `should throw InvalidStateException when a trailer does not belong to the truck`() {
        // Arrange
        val truck = truckProvider.getBaseEntity().copy(id = TRUCK_ID)
        val trailer = trailerProvider.getBaseEntity().copy(truckId = "wrongParentId")
        val trailerWD = TrailerWithDetails(trailer)

        // Act && Assert
        assertThrows<InvalidStateException> {
            TruckWithDetails(
                truck = truck,
                trailersWithDetails = listOf(trailerWD)
            )
        }

    }

    @Test
    fun `should throw InvalidStateException when a licensing does not belong to the truck`() {
        // Arrange
        val truck = truckProvider.getBaseEntity().copy(id = TRUCK_ID)
        val licensing = licensingProvider.getBaseEntity().copy(parentId = "wrongParentId")
        val licensingWF = LicensingWithFile(licensing)

        // Act && Assert
        assertThrows<InvalidStateException> {
            TruckWithDetails(
                truck = truck,
                licensingWithFiles = listOf(licensingWF)
            )
        }

    }

    @Test
    fun `should throw InvalidStateException when both trailer and licensing do not belong to the truck`() {
        // Arrange
        val truck = truckProvider.getBaseEntity().copy(id = TRUCK_ID)
        val trailer = trailerProvider.getBaseEntity().copy(truckId = "wrongParentId")
        val licensing = licensingProvider.getBaseEntity().copy(parentId = "wrongParentId")
        val trailerWD = TrailerWithDetails(trailer)
        val licensingWF = LicensingWithFile(licensing)

        // Act && Assert
        assertThrows<InvalidStateException> {
            TruckWithDetails(
                truck = truck,
                trailersWithDetails = listOf(trailerWD),
                licensingWithFiles = listOf(licensingWF)
            )
        }
    }

    companion object {
        private const val TRUCK_ID = "truckId"
        private const val TRAILER_ID = "trailerId"
        private const val LICENSING_ID = "licensingId"
    }

}