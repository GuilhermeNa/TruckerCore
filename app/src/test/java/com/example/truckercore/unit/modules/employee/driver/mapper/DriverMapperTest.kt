package com.example.truckercore.unit.modules.employee.driver.mapper

import com.example.truckercore._test_data_provider.TestDriverDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.employee.driver.dto.DriverDto
import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.modules.employee.driver.errors.DriverMappingException
import com.example.truckercore.modules.employee.driver.mapper.DriverMapper
import com.example.truckercore.modules.employee.shared.enums.EmployeeStatus
import com.example.truckercore.shared.enums.PersistenceStatus
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

internal class DriverMapperTest {

    private lateinit var mapper: DriverMapper
    private lateinit var entity: Driver
    private lateinit var dto: DriverDto

    companion object {
        @JvmStatic
        fun getInvalidDtos(): Array<DriverDto> {
            return TestDriverDataProvider.arrInvalidDtos()
        }
    }

    @BeforeEach
    fun setup() {
        mockStaticLog()
        mapper = DriverMapper()
        entity = TestDriverDataProvider.getBaseEntity()
        dto = TestDriverDataProvider.getBaseDto()
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
        assertEquals(entity.userId, createdDto.userId)
        assertEquals(entity.name, createdDto.name)
        assertEquals(entity.email, createdDto.email)
        assertEquals(entity.employeeStatus.name, createdDto.employeeStatus)
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
        assertEquals(dto.userId, createdEntity.userId)
        assertEquals(dto.name, createdEntity.name)
        assertEquals(dto.email, createdEntity.email)
        assertEquals(
            EmployeeStatus.convertString(dto.employeeStatus),
            createdEntity.employeeStatus
        )
    }

    @Test
    fun `toDto() should throw DriverMappingException when there are errors`() {
        // Object
        val mockk = spyk(mapper, recordPrivateCalls = true)

        // Behavior
        every { mockk["handleEntityMapping"](entity) } throws NullPointerException("Simulated exception")

        // Call
        assertThrows<DriverMappingException> {
            mockk.toDto(entity)
        }
    }

    @ParameterizedTest
    @MethodSource("getInvalidDtos")
    fun `toEntity() should throw DriverMappingException when there are errors`(
        pDto: DriverDto
    ) {
        assertThrows<DriverMappingException> {
            mapper.toEntity(pDto)
        }
    }

}