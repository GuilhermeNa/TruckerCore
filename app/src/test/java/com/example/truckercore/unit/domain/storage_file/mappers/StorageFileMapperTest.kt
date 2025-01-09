package com.example.truckercore.unit.domain.storage_file.mappers

import com.example.truckercore._test_data_provider.TestStorageFileDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.shared.modules.storage_file.dtos.StorageFileDto
import com.example.truckercore.shared.errors.InvalidPersistenceStatusException
import com.example.truckercore.shared.errors.InvalidUrlFormatException
import com.example.truckercore.shared.errors.MissingFieldException
import com.example.truckercore.shared.errors.UnknownErrorException
import com.example.truckercore.shared.modules.storage_file.mappers.StorageFileMapper
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

internal class StorageFileMapperTest {

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
        }

        @JvmStatic
        fun getArrWithMissingFields(): Array<StorageFileDto> {
            return TestStorageFileDataProvider.getArrWithMissingFields()
        }

    }

    @ParameterizedTest
    @MethodSource("getArrWithMissingFields")
    fun `toEntity() should throw IllegalArgumentException when there is missing fields`(
        dto: StorageFileDto
    ) {
        assertThrows<MissingFieldException> {
            StorageFileMapper.toEntity(dto)
        }
    }

    @Test
    fun `toEntity() should return an entity`() {
        // Object
        val dto = TestStorageFileDataProvider.getBaseDto()

        // Call
        val entity = StorageFileMapper.toEntity(dto)


        // Asserts
        assertEquals(dto.businessCentralId, entity.businessCentralId)
        assertEquals(dto.id, entity.id)
        assertEquals(dto.lastModifierId, entity.lastModifierId)
        assertEquals(dto.creationDate?.toLocalDateTime(), entity.creationDate)
        assertEquals(dto.lastUpdate?.toLocalDateTime(), entity.lastUpdate)
        assertEquals(dto.persistenceStatus, entity.persistenceStatus.name)
        assertEquals(dto.parentId, entity.parentId)
        assertEquals(dto.url, entity.url)
        assertEquals(dto.isUpdating, entity.isUpdating)

    }

    @Test
    fun `toEntity() should throw InvalidPersistenceStatusException when the state is provided but wrong`() {
        // Object
        val dto = TestStorageFileDataProvider.getBaseDto().copy(persistenceStatus = "WRONG")

        // Call
        val result = assertThrows<InvalidPersistenceStatusException> {
            StorageFileMapper.toEntity(dto)
        }

        // Assertion
        val expected = "Failed while mapping a storage file. Expecting a valid persistence " +
                "status, and received: ${dto.persistenceStatus} "
        val actual = result.message
        assertEquals(expected, actual)
    }

    @Test
    fun `toEntity() should throw InvalidUrlFormatException when url is malformed`() {
        // Object
        val dto = TestStorageFileDataProvider.getBaseDto().copy(url = "malFormedUrl")

        // Call
        val result = assertThrows<InvalidUrlFormatException> {
            StorageFileMapper.toEntity(dto)
        }

        // Assertion
        val expected = "Invalid file URL: ${dto.url}"
        val actual = result.message
        assertEquals(expected, actual)
    }

    @Test
    fun `toEntity() should throw UnknownErrorException when any unknown error happens`() {
        // Object
        val dto = TestStorageFileDataProvider.getBaseDto()
        val mapper = spyk(StorageFileMapper, recordPrivateCalls = true)
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
        val entity = TestStorageFileDataProvider.getBaseEntity()

        // Call
        val dto = StorageFileMapper.toDto(entity)

        // Asserts
        assertEquals(entity.businessCentralId, dto.businessCentralId)
        assertEquals(entity.id, dto.id)
        assertEquals(entity.lastModifierId, dto.lastModifierId)
        assertEquals(entity.creationDate.toDate(), dto.creationDate)
        assertEquals(entity.lastUpdate.toDate(), dto.lastUpdate)
        assertEquals(entity.persistenceStatus.name, dto.persistenceStatus)
        assertEquals(entity.parentId, dto.parentId)
        assertEquals(entity.url, dto.url)
        assertEquals(entity.isUpdating, dto.isUpdating)
    }

}