package com.example.truckercore.unit.model.modules.fleet.truck.dto

import com.example.truckercore._test_data_provider.TestTruckDataProvider
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

internal class TruckDtoTest {

    val dto = TestTruckDataProvider.getBaseDto()

    @Test
    fun `test initializeId should update id and set status to persisted`() {
        // Arrange
        val newId = "newTruckId"

        // Call
        val updatedDto = dto.initializeId(newId)

        // Assertions
        assertEquals(newId, updatedDto.id)
        assertEquals("PERSISTED", updatedDto.persistenceStatus)

        assertEquals(dto.businessCentralId, updatedDto.businessCentralId)
        assertEquals(dto.lastModifierId, updatedDto.lastModifierId)
        assertEquals(dto.creationDate, updatedDto.creationDate)
        assertEquals(dto.lastUpdate, updatedDto.lastUpdate)

        assertEquals(dto.plate, updatedDto.plate)
        assertEquals(dto.color, updatedDto.color)
        assertEquals(dto.brand, updatedDto.brand)
    }

}