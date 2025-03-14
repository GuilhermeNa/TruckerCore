package com.example.truckercore.unit.model.modules.notification.dto

import com.example.truckercore._test_data_provider.TestNotificationDataProvider
import com.example.truckercore.model.shared.enums.PersistenceStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class NotificationDtoTest {

    @Test
    fun `test initializeId should update id and set status to persisted`() {
        // Arrange
        val dto = TestNotificationDataProvider.getBaseDto().copy(
            id = null, persistenceStatus = PersistenceStatus.PENDING.name
        )
        val newId = "newNotificationId"

        // Call
        val updatedDto = dto.initializeId(newId)

        // Assertions
        assertEquals(newId, updatedDto.id)
        assertEquals("PERSISTED", updatedDto.persistenceStatus)

        assertEquals(dto.businessCentralId, updatedDto.businessCentralId)
        assertEquals(dto.lastModifierId, updatedDto.lastModifierId)
        assertEquals(dto.creationDate, updatedDto.creationDate)
        assertEquals(dto.lastUpdate, updatedDto.lastUpdate)
        assertEquals(dto.title, updatedDto.title)
        assertEquals(dto.message, updatedDto.message)
        assertEquals(dto.isRead, updatedDto.isRead)
        assertEquals(dto.parent, updatedDto.parent)
        assertEquals(dto.parentId, updatedDto.parentId)
    }

}