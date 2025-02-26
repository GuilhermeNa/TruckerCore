package com.example.truckercore.unit.modules.employee.driver.aggregations

import com.example.truckercore._test_data_provider.TestDriverDataProvider
import com.example.truckercore._test_data_provider.TestFileDataProvider
import com.example.truckercore._test_data_provider.TestPersonalDataDataProvider
import com.example.truckercore.modules.employee.driver.aggregations.DriverWithDetails
import com.example.truckercore.shared.errors.InvalidStateException
import com.example.truckercore.shared.modules.personal_data.aggregations.PersonalDataWithFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DriverWithDetailsTest {

    private val driverProvider = TestDriverDataProvider
    private val fileProvider = TestFileDataProvider
    private val pDataProvider = TestPersonalDataDataProvider

    @Test
    fun `should create DriverWithDetails with all fields`() {
        // Arrange
        val driver = driverProvider.getBaseEntity().copy(id = DRIVER_ID)
        val file = fileProvider.getBaseEntity().copy(parentId = DRIVER_ID)
        val pDataWF = PersonalDataWithFile(
            pDataProvider.getBaseEntity().copy(id = P_DATA_ID, parentId = DRIVER_ID),
            listOf(fileProvider.getBaseEntity().copy(parentId = P_DATA_ID))
        )

        // Act
        val driverWD = DriverWithDetails(
            driver = driver,
            photo = file,
            personalDataWithFile = setOf(pDataWF)
        )

        // Assert
        assertEquals(driverWD.driver.id, driverWD.photo?.parentId)
        assertEquals(driverWD.driver.id, driverWD.personalDataWithFile.first().parentId)
    }

    @Test
    fun `should throw InvalidStateException when photo id does not belong to provided driver`() {
        // Arrange
        val driver = driverProvider.getBaseEntity().copy(id = DRIVER_ID)
        val file = fileProvider.getBaseEntity().copy(parentId = "wrongDriverId") // Invalid parentId
        val pDataWF = PersonalDataWithFile(
            pDataProvider.getBaseEntity().copy(id = P_DATA_ID, parentId = DRIVER_ID),
            listOf(fileProvider.getBaseEntity().copy(parentId = P_DATA_ID))
        )

        // Act & Assert
        assertThrows<InvalidStateException> {
            DriverWithDetails(
                driver = driver,
                photo = file,
                personalDataWithFile = setOf(pDataWF)
            )
        }
    }

    @Test
    fun `should throw InvalidStateException when pDataWF id does not belong to provided driver`() {
        // Arrange
        val driver = driverProvider.getBaseEntity().copy(id = DRIVER_ID)
        val file = fileProvider.getBaseEntity().copy(parentId = DRIVER_ID)
        val pDataWF = PersonalDataWithFile(
            pDataProvider.getBaseEntity()
                .copy(id = P_DATA_ID, parentId = "wrongDriverId"), // Invalid parentId
            listOf(fileProvider.getBaseEntity().copy(parentId = P_DATA_ID))
        )

        // Act & Assert
        assertThrows<InvalidStateException> {
            DriverWithDetails(
                driver = driver,
                photo = file,
                personalDataWithFile = setOf(pDataWF)
            )
        }
    }

    companion object {
        private const val DRIVER_ID = "driverId"
        private const val P_DATA_ID = "pDataId"
    }

}