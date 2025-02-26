package com.example.truckercore.unit.shared.modules.file.dto

import com.example.truckercore._test_data_provider.TestFileDataProvider
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FileDtoTestImpl {

    @Test
    fun `initializeId() should initialize id and update persistence status`() {
        // Object
        val dto = TestFileDataProvider.getBaseDto()

        // Call
        val updatedDto = dto.initializeId("newId")

        // Asserts
        assertEquals("newId", updatedDto.id)
        assertEquals("PERSISTED", updatedDto.persistenceStatus)

        assertEquals(dto.businessCentralId, updatedDto.businessCentralId)
        assertEquals(dto.lastModifierId, updatedDto.lastModifierId)
        assertEquals(dto.creationDate, updatedDto.creationDate)
        assertEquals(dto.lastUpdate, updatedDto.lastUpdate)
        assertEquals(dto.parentId, updatedDto.parentId)
        assertEquals(dto.url, updatedDto.url)
        assertEquals(dto.isUpdating, updatedDto.isUpdating)

    }

}