/*
package com.example.truckercore.unit.modules.fleet.truck.mapper

import com.example.truckercore._test_data_provider.TestTruckDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.fleet.truck.dto.TruckDto
import com.example.truckercore.modules.fleet.truck.enums.TruckBrand
import com.example.truckercore.modules.fleet.truck.errors.TruckMappingException
import com.example.truckercore.modules.fleet.truck.mapper.TruckMapper
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime
import io.mockk.every
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class TruckMapperTest {

    private val mapper = TruckMapper()
    private val entity = TestTruckDataProvider.getBaseEntity()
    private val dto = TestTruckDataProvider.getBaseDto()

    companion object {

        @BeforeAll
        @JvmStatic
        fun setup() {
            mockStaticLog()
        }

        @JvmStatic
        fun getInvalidDtos(): Array<TruckDto> {
            return TestTruckDataProvider.arrInvalidDtos()
        }

    }

    @Test
    fun `toDto() should return a valid dto when data is valid`() {
        // Call
        val createdDto = mapper.toDto(entity)

        // Assertions
        assertEquals(entity.businessCentralId, createdDto.businessCentralId)
        assertEquals(entity.id, createdDto.id)
        assertEquals(entity.lastModifierId, createdDto.lastModifierId)
        assertEquals(entity.creationDate.toDate(), createdDto.creationDate)
        assertEquals(entity.lastUpdate.toDate(), createdDto.lastUpdate)
        assertEquals(entity.persistenceStatus.name, createdDto.persistenceStatus)
        assertEquals(entity.plate, createdDto.plate)
        assertEquals(entity.color, createdDto.color)
        assertEquals(entity.brand.name, createdDto.brand)
    }

    @Test
    fun `toEntity() should return a valid entity when data is valid`() {
        // Call
        val createdEntity = mapper.toEntity(dto)

        // Assertions
        assertEquals(dto.businessCentralId, createdEntity.businessCentralId)
        assertEquals(dto.id, createdEntity.id)
        assertEquals(dto.lastModifierId, createdEntity.lastModifierId)
        assertEquals(dto.creationDate?.toLocalDateTime(), createdEntity.creationDate)
        assertEquals(dto.lastUpdate?.toLocalDateTime(), createdEntity.lastUpdate)
        assertEquals(
            PersistenceStatus.convertString(dto.persistenceStatus),
            createdEntity.persistenceStatus
        )
        assertEquals(dto.plate, createdEntity.plate)
        assertEquals(dto.color, createdEntity.color)
        assertEquals(
            TruckBrand.convertString(dto.brand),
            createdEntity.brand
        )
    }

    @Test
    fun `toDto() should throw TruckMappingException when there are errors`() {
        // Object
        val mockk = spyk(mapper, recordPrivateCalls = true)

        // Behavior
        every { mockk["handleEntityMapping"](entity) } throws NullPointerException("Simulated exception")

        // Call
        assertThrows<TruckMappingException> {
            mockk.toDto(entity)
        }
    }

    @ParameterizedTest
    @MethodSource("getInvalidDtos")
    fun `toEntity() should throw TruckMappingException when there are errors`(
        pDto: TruckDto
    ) {
        assertThrows<TruckMappingException> {
            mapper.toEntity(pDto)
        }
    }


}*/
