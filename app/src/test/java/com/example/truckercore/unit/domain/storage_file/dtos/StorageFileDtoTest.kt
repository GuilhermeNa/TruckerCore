package com.example.truckercore.unit.domain.storage_file.dtos

import com.example.truckercore._test_data_provider.TestStorageFileDataProvider
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StorageFileDtoTest {

    @Test
    fun `initializeId() should initialize id and update persistence status`() {
        // Object
        val dto = TestStorageFileDataProvider.getBaseDto()

        // Call
        val updatedDto = dto.initializeId("newId")

        // Asserts
        assertEquals("newId", updatedDto.id)
        assertEquals("PERSISTED", updatedDto.persistenceStatus)

        assertEquals(dto.centralId, updatedDto.centralId)
        assertEquals(dto.lastModifierId, updatedDto.lastModifierId)
        assertEquals(dto.creationDate, updatedDto.creationDate)
        assertEquals(dto.lastUpdate, updatedDto.lastUpdate)
        assertEquals(dto.parentId, updatedDto.parentId)
        assertEquals(dto.url, updatedDto.url)
        assertEquals(dto.isUpdating, updatedDto.isUpdating)

    }

}