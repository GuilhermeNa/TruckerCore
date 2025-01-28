package com.example.truckercore.unit.shared.modules.personal_data.mapper

import com.example.truckercore._test_data_provider.TestPersonalDataDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.InvalidPersistenceStatusException
import com.example.truckercore.shared.errors.MissingFieldException
import com.example.truckercore.shared.errors.UnknownErrorException
import com.example.truckercore.shared.modules.personal_data.dto.PersonalDataDto
import com.example.truckercore.shared.modules.personal_data.errors.PersonalDataMappingException
import com.example.truckercore.shared.modules.personal_data.mapper.PersonalDataMapper
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

internal class PersonalDataMapperITestImpl {

    private val mapper = PersonalDataMapper()
    private val entity = TestPersonalDataDataProvider.getBaseEntity()
    private val dto = TestPersonalDataDataProvider.getBaseDto()

    companion object {

        @BeforeAll
        @JvmStatic
        fun setup() {
            mockStaticLog()
        }

        @JvmStatic
        fun getInvalidDtos(): Array<PersonalDataDto> {
            return TestPersonalDataDataProvider.arrInvalidDtos()
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
        assertEquals(entity.name, createdDto.name)
        assertEquals(entity.number, createdDto.number)
        assertEquals(entity.emissionDate.toDate(), createdDto.emissionDate)
        assertEquals(entity.expirationDate?.toDate(), createdDto.expirationDate)
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
        assertEquals(dto.name, createdEntity.name)
        assertEquals(dto.number, createdEntity.number)
        assertEquals(dto.emissionDate?.toLocalDateTime(), createdEntity.emissionDate)
        assertEquals(dto.expirationDate?.toLocalDateTime(), createdEntity.expirationDate)
    }

    @Test
    fun `toDto() should throw PersonalDataMappingException when there are errors`() {
        // Object
        val mockk = spyk(mapper, recordPrivateCalls = true)

        // Behavior
        every { mockk["handleEntityMapping"](entity) } throws NullPointerException("Simulated exception")

        // Call
        assertThrows<PersonalDataMappingException> {
            mockk.toDto(entity)
        }
    }

    @ParameterizedTest
    @MethodSource("getInvalidDtos")
    fun `toEntity() should throw PersonalDataMappingException when there are errors`(
        pDto: PersonalDataDto
    ) {
        assertThrows<PersonalDataMappingException> {
            mapper.toEntity(pDto)
        }
    }

}