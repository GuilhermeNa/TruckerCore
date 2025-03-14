package com.example.truckercore.unit.model.modules.notification.mapper

import com.example.truckercore._test_data_provider.TestNotificationDataProvider
import com.example.truckercore._test_data_provider.TestTruckDataProvider
import com.example.truckercore.model.modules.fleet.truck.dto.TruckDto
import com.example.truckercore.model.modules.fleet.truck.entity.Truck
import com.example.truckercore.model.modules.notification.dto.NotificationDto
import com.example.truckercore.model.modules.notification.entity.Notification
import com.example.truckercore.model.modules.notification.mapper.NotificationMapper
import com.example.truckercore.model.shared.errors.mapping.IllegalMappingArgumentException
import com.example.truckercore.model.shared.errors.mapping.InvalidForMappingException
import com.example.truckercore.model.shared.utils.expressions.toDate
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

internal class NotificationMapperTest {

    private val mapper = NotificationMapper()
    private val provider = TestNotificationDataProvider

    companion object {
        @JvmStatic
        fun arrInvalidDtosForMapping(): Array<NotificationDto> {
            return TestNotificationDataProvider.arrInvalidDtosForMapping()
        }
    }

    @Test
    fun `toDto() should return a valid dto when data is valid`() {
        // Arrange
        val entity = provider.getBaseEntity()

        // Call
        val createdDto = mapper.toDto(entity)

        // Assertions
        assertEquals(entity.businessCentralId, createdDto.businessCentralId)
        assertEquals(entity.id, createdDto.id)
        assertEquals(entity.lastModifierId, createdDto.lastModifierId)
        assertEquals(entity.creationDate.toDate(), createdDto.creationDate)
        assertEquals(entity.lastUpdate.toDate(), createdDto.lastUpdate)
        assertEquals(entity.persistenceStatus.name, createdDto.persistenceStatus)
        assertEquals(entity.title, createdDto.title)
        assertEquals(entity.message, createdDto.message)
        assertEquals(entity.isRead, createdDto.isRead)
        assertEquals(entity.parent.name, createdDto.parent)
        assertEquals(entity.parentId, createdDto.parentId)
    }

    @Test
    fun `toEntity() should return a valid entity when data is valid`() {
        // Arrange
        val dto = provider.getBaseDto()

        // Call
        val createdEntity = mapper.toEntity(dto)

        // Assertions
        assertEquals(dto.businessCentralId, createdEntity.businessCentralId)
        assertEquals(dto.id, createdEntity.id)
        assertEquals(dto.lastModifierId, createdEntity.lastModifierId)
        assertEquals(dto.creationDate, createdEntity.creationDate.toDate())
        assertEquals(dto.lastUpdate, createdEntity.lastUpdate.toDate())
        assertEquals(dto.persistenceStatus, createdEntity.persistenceStatus.name)
        assertEquals(dto.title, createdEntity.title)
        assertEquals(dto.message, createdEntity.message)
        assertEquals(dto.isRead, createdEntity.isRead)
        assertEquals(dto.parent, createdEntity.parent.name)
        assertEquals(dto.parentId, createdEntity.parentId)
    }

    @Test
    fun `toDto() should throw IllegalMappingArgumentException when the dto is wrong`() {
        // Arrange
        val wrongDto = TestTruckDataProvider.getBaseDto()

        // Call
        val exception = assertThrows<IllegalMappingArgumentException> {
            mapper.toEntity(wrongDto)
        }

        // Assertions
        assertTrue(exception.expected == NotificationDto::class.simpleName)
        assertTrue(exception.received == TruckDto::class.simpleName)
    }

    @Test
    fun `toEntity() should throw IllegalMappingArgumentException when the entity is wrong`() {
        // Arrange
        val wrongEntity = TestTruckDataProvider.getBaseEntity()

        // Call
        val exception = assertThrows<IllegalMappingArgumentException> {
            mapper.toDto(wrongEntity)
        }

        // Assertions
        assertTrue(exception.expected == Notification::class.simpleName)
        assertTrue(exception.received == Truck::class.simpleName)
    }

    @ParameterizedTest
    @MethodSource("arrInvalidDtosForMapping")
    fun `toEntity() should throw InvalidForMappingException when there are errors`(
        pDto: NotificationDto
    ) {
        // Call
        val exception = assertThrows<InvalidForMappingException> {
            mapper.toEntity(pDto)
        }
        // Assertions
        assertTrue(exception.dto is NotificationDto)
    }

}