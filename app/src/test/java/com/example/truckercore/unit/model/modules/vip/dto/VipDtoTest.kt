package com.example.truckercore.unit.model.modules.vip.dto

import com.example.truckercore._test_data_provider.TestVipDataProvider
import com.example.truckercore.model.shared.enums.PersistenceStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class VipDtoTest {

    private val provider = TestVipDataProvider

    @Test
    fun `test initializeId should update id and set status to persisted`() {
        // Arrange
        val newId = "newVipId"
        val dto = provider.getBaseDto().copy(
            id = null, persistenceStatus = PersistenceStatus.PENDING.name
        )

        // Call
        val updatedDto = dto.initializeId(newId)

        // Assertions
        assertEquals(newId, updatedDto.id)
        assertEquals("PERSISTED", updatedDto.persistenceStatus)

        assertEquals(dto.businessCentralId, updatedDto.businessCentralId)
        assertEquals(dto.lastModifierId, updatedDto.lastModifierId)
        assertEquals(dto.creationDate, updatedDto.creationDate)
        assertEquals(dto.lastUpdate, updatedDto.lastUpdate)
        assertEquals(dto.vipStart, updatedDto.vipStart)
        assertEquals(dto.vipEnd, updatedDto.vipEnd)
        assertEquals(dto.notificationDate, updatedDto.notificationDate)
        assertEquals(dto.userId, updatedDto.userId)
        assertEquals(dto.isActive, updatedDto.isActive)
    }

}