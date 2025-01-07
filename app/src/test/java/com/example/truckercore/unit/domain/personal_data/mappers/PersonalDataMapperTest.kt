package com.example.truckercore.unit.domain.personal_data.mappers

import com.example.truckercore._test_data_provider.TestPersonalDataDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.shared.errors.InvalidPersistenceStatusException
import com.example.truckercore.shared.errors.MissingFieldException
import com.example.truckercore.shared.errors.UnknownErrorException
import com.example.truckercore.shared.modules.personal_data.dtos.PersonalDataDto
import com.example.truckercore.shared.modules.personal_data.mappers.PersonalDataMapper
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

internal class PersonalDataMapperTest {

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
        }

        @JvmStatic
        fun getArrWithMissingFields(): Array<PersonalDataDto> {
            return TestPersonalDataDataProvider.getArrWithMissingFields()
        }

    }

    @ParameterizedTest
    @MethodSource("getArrWithMissingFields")
    fun `toEntity() should throw IllegalArgumentException when there is missing fields`(
        dto: PersonalDataDto
    ) {
        assertThrows<MissingFieldException> {
            PersonalDataMapper.toEntity(dto)
        }
    }

    @Test
    fun `toEntity() should return an entity`() {
        // Object
        val dto = TestPersonalDataDataProvider.getBaseDto()

        // Call
        val entity = PersonalDataMapper.toEntity(dto)

        // Asserts
        assertEquals(dto.masterUid, entity.masterUid)
        assertEquals(dto.id, entity.id)
        assertEquals(dto.lastModifierId, entity.lastModifierId)
        assertEquals(dto.creationDate?.toLocalDateTime(), entity.creationDate)
        assertEquals(dto.lastUpdate?.toLocalDateTime(), entity.lastUpdate)
        assertEquals(dto.persistenceStatus, entity.persistenceStatus.name)
        assertEquals(dto.parentId, entity.parentId)
        assertEquals(dto.name, entity.name)
        assertEquals(dto.number, entity.number)
        assertEquals(dto.emissionDate?.toLocalDateTime(), entity.emissionDate)
        assertEquals(dto.expirationDate?.toLocalDateTime(), entity.expirationDate)
    }

    @Test
    fun `toEntity() should throw InvalidPersistenceStatusException when the state is provided but wrong`() {
        // Object
        val dto = TestPersonalDataDataProvider.getBaseDto().copy(persistenceStatus = "WRONG")

        // Call
        val result = assertThrows<InvalidPersistenceStatusException> {
            PersonalDataMapper.toEntity(dto)
        }

        // Assertion
        val expected = "Failed while mapping a personal data. Expecting a valid persistence " +
                "status, and received: ${dto.persistenceStatus} "
        val actual = result.message
        assertEquals(expected, actual)
    }

    @Test
    fun `toEntity() should throw UnknownErrorException when any unknown error happens`() {
        // Object
        val dto = TestPersonalDataDataProvider.getBaseDto()
        val mapper = spyk(PersonalDataMapper, recordPrivateCalls = true)
        val exception = NullPointerException("Simulated exception")

        // Behavior
        every { mapper["mapDtoToEntity"](dto) } throws exception

        // Call
        val result = assertThrows<UnknownErrorException> {
            mapper.toEntity(dto)
        }

        // Assertions
        val expected = "Unknown error occurred while mapping a storage file."
        val actual = result.message
        assertEquals(expected, actual)

        val actual2 = result.cause
        assertEquals(exception, actual2)
    }

    @Test
    fun `toDto() should return a dto`() {
        // Object
        val entity = TestPersonalDataDataProvider.getBaseEntity()

        // Call
        val dto = PersonalDataMapper.toDto(entity)

        // Asserts
        assertEquals(entity.masterUid, dto.masterUid)
        assertEquals(entity.id, dto.id)
        assertEquals(entity.lastModifierId, dto.lastModifierId)
        assertEquals(entity.creationDate.toDate(), dto.creationDate)
        assertEquals(entity.lastUpdate.toDate(), dto.lastUpdate)
        assertEquals(entity.persistenceStatus.name, dto.persistenceStatus)
        assertEquals(entity.parentId, dto.parentId)
        assertEquals(entity.name, dto.name)
        assertEquals(entity.number, dto.number)
        assertEquals(entity.emissionDate.toDate(), dto.emissionDate)
        assertEquals(entity.expirationDate?.toDate(), dto.expirationDate)

    }

}