package com.example.truckercore.unit.modules.business_central.mapper

import com.example.truckercore._test_data_provider.TestBusinessCentralDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.mapping.IllegalMappingArgumentException
import com.example.truckercore.shared.errors.mapping.InvalidForMappingException
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal class BusinessCentralMapperTest {

    private val mapper = BusinessCentralMapper()
    private val entity = TestBusinessCentralDataProvider.getBaseEntity()
    private val dto = TestBusinessCentralDataProvider.getBaseDto()

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
        }

        @JvmStatic
        fun getInvalidDtos(): Array<BusinessCentralDto> =
            TestBusinessCentralDataProvider.arrInvalidDtos()

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
    fun `toEntity()  should throw IllegalMappingArgumentException when the dto is wrong`() {
        // Arrange
        val wrongDto = TestUserDataProvider.getBaseDto()

        // Call
        val exception = assertThrows<IllegalMappingArgumentException> {
            mapper.toEntity(wrongDto)
        }

        // Assertions
        assertTrue(exception.expected is BusinessCentralDto)
        assertTrue(exception.received is UserDto)

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
        assertTrue(exception.expected is BusinessCentral)
        assertTrue(exception.received is User)

    }

    @ParameterizedTest
    @MethodSource("getInvalidDtos")
    fun `toEntity() should throw InvalidForMappingException when there are errors`(
        pDto: BusinessCentralDto
    ) {
        assertThrows<InvalidForMappingException> {
            mapper.toEntity(pDto)
        }
    }

}