package com.example.truckercore.unit.model.modules.person.employee.admin.mapper

import com.example.truckercore._test_data_provider.TestAdminDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.modules.person.employee.admin.dto.AdminDto
import com.example.truckercore.model.modules.person.employee.admin.entity.Admin
import com.example.truckercore.model.modules.person.employee.admin.mapper.AdminMapper
import com.example.truckercore.model.modules.person.employee.shared.enums.EmployeeStatus
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.enums.PersistenceStatus
import com.example.truckercore.model.shared.errors.mapping.IllegalMappingArgumentException
import com.example.truckercore.model.shared.errors.mapping.InvalidForMappingException
import com.example.truckercore.model.shared.utils.expressions.toDate
import com.example.truckercore.model.shared.utils.expressions.toLocalDateTime
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

internal class AdminMapperTest : KoinTest {

    private val mapper: AdminMapper by inject()

    private val entity = TestAdminDataProvider.getBaseEntity()
    private val dto = TestAdminDataProvider.getBaseDto()

    companion object {

        @BeforeAll
        @JvmStatic
        fun setup() {
            mockStaticLog()

            startKoin {
                modules(
                    module {
                        single { AdminMapper() }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()

        @JvmStatic
        fun getInvalidDtos(): Array<AdminDto> {
            return TestAdminDataProvider.arrInvalidDtos()
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
    fun `toDto()  should throw IllegalMappingArgumentException when the entity is wrong`() {
        // Arrange
        val wrongEntity = TestUserDataProvider.getBaseEntity()

        // Call
        val exception = assertThrows<IllegalMappingArgumentException> {
            mapper.toDto(wrongEntity)
        }

        // Assertions
        assertTrue(exception.expected == Admin::class.simpleName)
        assertTrue(exception.received == User::class.simpleName)

    }

    @ParameterizedTest
    @MethodSource("getInvalidDtos")
    fun `toEntity() should throw DriverMappingException when there are errors`(
        pDto: AdminDto
    ) {
        assertThrows<InvalidForMappingException> {
            mapper.toEntity(pDto)
        }
    }

}
