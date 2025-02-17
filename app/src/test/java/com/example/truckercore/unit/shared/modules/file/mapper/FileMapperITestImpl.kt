package com.example.truckercore.unit.shared.modules.file.mapper

import com.example.truckercore._test_data_provider.TestStorageFileDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.errors.mapping.IllegalMappingArgumentException
import com.example.truckercore.shared.errors.mapping.InvalidForMappingException
import com.example.truckercore.shared.modules.file.dto.FileDto
import com.example.truckercore.shared.modules.file.entity.File
import com.example.truckercore.shared.modules.file.mapper.FileMapper
import com.example.truckercore.shared.utils.expressions.toDate
import com.example.truckercore.shared.utils.expressions.toLocalDateTime
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

internal class FileMapperITestImpl : KoinTest {

    private val mapper: FileMapper by inject()

    private val entity = TestStorageFileDataProvider.getBaseEntity()
    private val dto = TestStorageFileDataProvider.getBaseDto()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single { FileMapper() }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()

        @JvmStatic
        fun getInvalidDtos(): Array<FileDto> {
            return TestStorageFileDataProvider.arrInvalidDtos()
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
        assertEquals(entity.url, createdDto.url)
        assertEquals(entity.isUpdating, createdDto.isUpdating)
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
        assertEquals(dto.url, createdEntity.url)
        assertEquals(dto.isUpdating, createdEntity.isUpdating)
    }

    @Test
    fun `toDto() should throw IllegalMappingArgumentException when the dto is wrong`() {
        // Arrange
        val wrongDto = TestUserDataProvider.getBaseDto()

        // Call
        val exception = assertThrows<IllegalMappingArgumentException> {
            mapper.toEntity(wrongDto)
        }

        // Assertions
        assertTrue(exception.expected == FileDto::class)
        assertTrue(exception.received == UserDto::class)
    }

    @Test
    fun `toEntity() should throw IllegalMappingArgumentException when the entity is wrong`() {
        // Arrange
        val wrongEntity = TestUserDataProvider.getBaseEntity()

        // Call
        val exception = assertThrows<IllegalMappingArgumentException> {
            mapper.toDto(wrongEntity)
        }

        // Assertions
        assertTrue(exception.expected == File::class)
        assertTrue(exception.received == User::class)
    }

    @ParameterizedTest
    @MethodSource("getInvalidDtos")
    fun `toEntity() should throw InvalidForMappingException when there are errors`(
        pDto: FileDto
    ) {
        // Call
        val exception = assertThrows<InvalidForMappingException> {
            mapper.toEntity(pDto)
        }
        // Assertions
        assertTrue(exception.dto is FileDto)
    }

}
