package com.example.truckercore.unit.modules.user.dto

import com.example.truckercore._test_data_provider.TestUserDataProvider
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

internal class UserDtoTest {

    val dto = TestUserDataProvider.getBaseDto()

    @Test
    fun `test initializeId should update id and set status to persisted`() {
        // Arrange
        val newId = "newUserId"

        // Call
        val updatedDto = dto.initializeId(newId)

        // Assertions
        assertEquals(newId, updatedDto.id)
        assertEquals("PERSISTED", updatedDto.persistenceStatus)

        assertEquals(dto.businessCentralId, updatedDto.businessCentralId)
        assertEquals(dto.lastModifierId, updatedDto.lastModifierId)
        assertEquals(dto.creationDate, updatedDto.creationDate)
        assertEquals(dto.lastUpdate, updatedDto.lastUpdate)
        assertEquals(dto.isVip, updatedDto.isVip)
        assertEquals(dto.vipStart, updatedDto.vipStart)
        assertEquals(dto.vipEnd, updatedDto.vipEnd)

        assertEquals(dto.level, updatedDto.level)
        assertEquals(dto.permissions, updatedDto.permissions)
        assertEquals(dto.personFLag, updatedDto.personFLag)
    }

}