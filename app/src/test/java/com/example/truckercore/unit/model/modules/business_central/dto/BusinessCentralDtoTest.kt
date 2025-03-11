package com.example.truckercore.unit.model.modules.business_central.dto

import com.example.truckercore._test_data_provider.TestBusinessCentralDataProvider
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


internal class BusinessCentralDtoTest {

    val dto = TestBusinessCentralDataProvider.getBaseDto()

    @Test
    fun `test initializeId should update id and set status to persisted`() {
        // Arrange --
        val newId = "newId"

        // Call --
        val updatedDto = dto.initializeId(newId)

        // Assertions --

        // Should be updated
        assertEquals(newId, updatedDto.id)
        assertEquals("PERSISTED", updatedDto.persistenceStatus)

        // Should be equals
        assertEquals(dto.businessCentralId, updatedDto.businessCentralId)
        assertEquals(dto.lastModifierId, updatedDto.lastModifierId)
        assertEquals(dto.creationDate, updatedDto.creationDate)
        assertEquals(dto.lastUpdate, updatedDto.lastUpdate)
        assertEquals(dto.authorizedUserIds, updatedDto.authorizedUserIds)
        assertEquals(dto.keys, updatedDto.keys)

    }

}