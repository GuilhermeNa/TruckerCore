package com.example.truckercore.unit.modules.business_central.mapper

import com.example.truckercore._test_data_provider.TestBusinessCentralDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.errors.BusinessCentralMappingException
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.utils.expressions.logWarn
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime
import io.mockk.every
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal class BusinessCentralMapperITestImpl {

    private lateinit var mapper: BusinessCentralMapper
    private lateinit var entity: BusinessCentral
    private lateinit var dto: BusinessCentralDto

    @BeforeEach
    fun setup() {
        mockStaticLog()
        mapper = BusinessCentralMapper()
        entity = TestBusinessCentralDataProvider.getBaseEntity()
        dto = TestBusinessCentralDataProvider.getBaseDto()
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
            dto.persistenceStatus?.let { PersistenceStatus.valueOf(it) },
            createdEntity.persistenceStatus
        )
    }

    @Test
    fun `toDto() should throw BusinessCentralMappingException when there are errors`() {
        // Object
        val mockk = spyk(mapper, recordPrivateCalls = true)

        // Behavior
        every { mockk["handleEntityMapping"](entity) } throws NullPointerException("Simulated exception")

        // Call
      assertThrows<BusinessCentralMappingException> {
            mockk.toDto(entity)
        }

    }

    @ParameterizedTest
    @MethodSource("getInvalidDtos")
    fun `toEntity() should throw BusinessCentralMappingException when there are errors`(
        pDto: BusinessCentralDto
    ) {
       assertThrows<BusinessCentralMappingException> {
            mapper.toEntity(pDto)
        }

    }

    companion object {
        @JvmStatic
        fun getInvalidDtos(): Array<BusinessCentralDto> {
            return TestBusinessCentralDataProvider.arrInvalidDtos()
        }
    }

}