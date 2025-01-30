package com.example.truckercore.unit.modules.fleet.shared.module.licensing.mapper

import com.example.truckercore._test_data_provider.TestLicensingDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.fleet.shared.module.licensing.dto.LicensingDto
import com.example.truckercore.modules.fleet.shared.module.licensing.errors.LicensingMappingException
import com.example.truckercore.modules.fleet.shared.module.licensing.mapper.LicensingMapper
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

internal class LicensingMapperTest {

    private val mapper = LicensingMapper()
    private val entity = TestLicensingDataProvider.getBaseEntity()
    private val dto = TestLicensingDataProvider.getBaseDto()

    companion object {
        @BeforeAll
        @JvmStatic
        fun setup() {
            mockStaticLog()
        }

        @JvmStatic
        fun getInvalidDtos(): Array<LicensingDto> {
            return TestLicensingDataProvider.arrInvalidDtosForMapping()
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
        assertEquals(entity.parentId, createdDto.parentId)
        assertEquals(entity.emissionDate.toDate(), createdDto.emissionDate)
        assertEquals(entity.expirationDate.toDate(), createdDto.expirationDate)
        assertEquals(entity.plate, createdDto.plate)
        assertEquals(entity.exercise.toDate(), createdDto.exercise)
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
        assertEquals(dto.parentId, createdEntity.parentId)
        assertEquals(dto.emissionDate?.toLocalDateTime(), createdEntity.emissionDate)
        assertEquals(dto.expirationDate?.toLocalDateTime(), createdEntity.expirationDate)
        assertEquals(dto.plate, createdEntity.plate)
        assertEquals(dto.exercise?.toLocalDateTime(), createdEntity.exercise)
    }

    @Test
    fun `toDto() should throw LicensingMappingException when there are errors`() {
        // Object
        val mockk = spyk(mapper, recordPrivateCalls = true)

        // Behavior
        every { mockk["handleEntityMapping"](entity) } throws NullPointerException("Simulated exception")

        // Call
        assertThrows<LicensingMappingException> {
            mockk.toDto(entity)
        }
    }

    @ParameterizedTest
    @MethodSource("getInvalidDtos")
    fun `toEntity() should throw LicensingMappingException when there are errors`(
        pDto: LicensingDto
    ) {
        assertThrows<LicensingMappingException> {
            mapper.toEntity(pDto)
        }
    }

}