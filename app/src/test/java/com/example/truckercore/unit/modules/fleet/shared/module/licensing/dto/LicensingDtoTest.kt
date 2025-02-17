package com.example.truckercore.unit.modules.fleet.shared.module.licensing.dto

import com.example.truckercore._test_data_provider.TestLicensingDataProvider
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

internal class LicensingDtoTest {

    val dto = TestLicensingDataProvider.getBaseDto()

    @Test
    fun `test initializeId should update id and set status to persisted`() {
        // Arrange
        val newId = "newLicensingId"

        // Call
        val updatedDto = dto.initializeId(newId)

        // Assertions
        assertEquals(newId, updatedDto.id)
        assertEquals("PERSISTED", updatedDto.persistenceStatus)

        assertEquals(dto.businessCentralId, updatedDto.businessCentralId)
        assertEquals(dto.lastModifierId, updatedDto.lastModifierId)
        assertEquals(dto.creationDate, updatedDto.creationDate)
        assertEquals(dto.lastUpdate, updatedDto.lastUpdate)

        assertEquals(dto.parentId, updatedDto.parentId)
        assertEquals(dto.emissionDate, updatedDto.emissionDate)
        assertEquals(dto.expirationDate, updatedDto.expirationDate)
        assertEquals(dto.plate, updatedDto.plate)
        assertEquals(dto.exercise, updatedDto.exercise)
    }

}