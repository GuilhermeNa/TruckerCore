package com.example.truckercore.unit.model.modules.person.employee.admin.dto

import com.example.truckercore._test_data_provider.TestAdminDataProvider
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

internal class AdminDtoTest {

    val dto = TestAdminDataProvider.getBaseDto()

    @Test
    fun `test initializeId should update id and set status to persisted`() {
        // Arrange
        val newId = "newId"

        // Call
        val updatedDto = dto.initializeId(newId)

        // Assertions
        assertEquals(newId, updatedDto.id)
        assertEquals("PERSISTED", updatedDto.persistenceStatus)

        assertEquals(dto.businessCentralId, updatedDto.businessCentralId)
        assertEquals(dto.lastModifierId, updatedDto.lastModifierId)
        assertEquals(dto.creationDate, updatedDto.creationDate)
        assertEquals(dto.lastUpdate, updatedDto.lastUpdate)

        assertEquals(dto.userId, updatedDto.userId)
        assertEquals(dto.name, updatedDto.name)
        assertEquals(dto.email, updatedDto.email)
        assertEquals(dto.employeeStatus, updatedDto.employeeStatus)
    }

}