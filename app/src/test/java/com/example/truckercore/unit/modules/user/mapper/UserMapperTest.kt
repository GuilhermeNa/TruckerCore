package com.example.truckercore.unit.modules.user.mapper

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.errors.UserMappingException
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime
import io.mockk.every
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal class UserMapperTest {

    private lateinit var mapper: UserMapper
    private lateinit var entity: User
    private lateinit var dto: UserDto

    companion object {
        @JvmStatic
        fun getInvalidDtos(): Array<UserDto> {
            return TestUserDataProvider.arrInvalidDtos()
        }
    }

    @BeforeEach
    fun setup() {
        mockStaticLog()
        mapper = UserMapper()
        entity = TestUserDataProvider.getBaseEntity()
        dto = TestUserDataProvider.getBaseDto()
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
        assertEquals(entity.level.name, createdDto.level)
        assertEquals(entity.permissions.map { it.name }, createdDto.permissions)
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
        assertEquals(Level.convertString(dto.level), createdEntity.level)
        assertEquals(dto.permissions?.map { Permission.convertString(it) }?.toSet(), createdEntity.permissions)
    }

    @Test
    fun `toDto() should throw UserMappingException when there are errors`() {
        // Object
        val mockk = spyk(mapper, recordPrivateCalls = true)

        // Behavior
        every { mockk["handleEntityMapping"](entity) } throws NullPointerException("Simulated exception")

        // Call
        assertThrows<UserMappingException> {
            mockk.toDto(entity)
        }
    }

    @ParameterizedTest
    @MethodSource("getInvalidDtos")
    fun `toEntity() should throw UserMappingException when there are errors`(
        pDto: UserDto
    ) {
        assertThrows<UserMappingException> {
            mapper.toEntity(pDto)
        }
    }

}