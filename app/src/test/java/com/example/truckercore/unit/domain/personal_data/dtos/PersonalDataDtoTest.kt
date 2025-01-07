package com.example.truckercore.unit.domain.personal_data.dtos

import com.example.truckercore._test_data_provider.TestPersonalDataDataProvider
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PersonalDataDtoTest {

    @Test
    fun `initializeId() should initialize id and update persistence status`() {
        // Object
        val dto = TestPersonalDataDataProvider.getBaseDto()

        // Call
        val updatedDto = dto.initializeId("newId")

        // Asserts
        assertEquals("newId", updatedDto.id)
        assertEquals("PERSISTED", updatedDto.persistenceStatus)

        assertEquals(dto.masterUid, updatedDto.masterUid)
        assertEquals(dto.lastModifierId, updatedDto.lastModifierId)
        assertEquals(dto.creationDate, updatedDto.creationDate)
        assertEquals(dto.lastUpdate, updatedDto.lastUpdate)
        assertEquals(dto.parentId, updatedDto.parentId)
        assertEquals(dto.name, updatedDto.name)
        assertEquals(dto.number, updatedDto.number)
        assertEquals(dto.emissionDate, updatedDto.emissionDate)
        assertEquals(dto.expirationDate, updatedDto.expirationDate)
    }

}