package com.example.truckercore.unit.model.modules.vip.mapper

import com.example.truckercore._test_data_provider.TestTruckDataProvider
import com.example.truckercore._test_data_provider.TestVipDataProvider
import com.example.truckercore.model.modules.fleet.truck.dto.TruckDto
import com.example.truckercore.model.modules.fleet.truck.entity.Truck
import com.example.truckercore.model.modules.user.dto.UserDto
import com.example.truckercore.model.modules.vip.dto.VipDto
import com.example.truckercore.model.modules.vip.entity.Vip
import com.example.truckercore.model.modules.vip.mapper.VipMapper
import com.example.truckercore.model.shared.errors.mapping.IllegalMappingArgumentException
import com.example.truckercore.model.shared.errors.mapping.InvalidForMappingException
import com.example.truckercore.model.shared.utils.expressions.toDate
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

internal class VipMapperTest {

    private val mapper = VipMapper()
    private val provider = TestVipDataProvider

    companion object {
        @JvmStatic
        fun arrInvalidDtosForMapping(): Array<VipDto> {
            return TestVipDataProvider.arrInvalidDtosForMapping()
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
        assertEquals(entity.vipStart.toDate(), createdDto.vipStart)
        assertEquals(entity.vipEnd.toDate(), createdDto.vipEnd)
        assertEquals(entity.notificationDate.toDate(), createdDto.notificationDate)
        assertEquals(entity.userId, createdDto.userId)
        assertEquals(entity.isActive, createdDto.isActive)

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
        assertEquals(dto.vipStart, createdEntity.vipStart.toDate())
        assertEquals(dto.vipEnd, createdEntity.vipEnd.toDate())
        assertEquals(dto.notificationDate, createdEntity.notificationDate.toDate())
        assertEquals(dto.userId, createdEntity.userId)
        assertEquals(dto.isActive, createdEntity.isActive)
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
        assertTrue(exception.expected == VipDto::class.simpleName)
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
        assertTrue(exception.expected == Vip::class.simpleName)
        assertTrue(exception.received == Truck::class.simpleName)
    }

    @ParameterizedTest
    @MethodSource("arrInvalidDtosForMapping")
    fun `toEntity() should throw InvalidForMappingException when there are errors`(
        pDto: VipDto
    ) {
        // Call
        val exception = assertThrows<InvalidForMappingException> {
            mapper.toEntity(pDto)
        }
        // Assertions
        assertTrue(exception.dto is VipDto)
    }

}