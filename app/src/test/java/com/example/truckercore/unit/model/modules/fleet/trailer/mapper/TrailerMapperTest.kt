package com.example.truckercore.unit.model.modules.fleet.trailer.mapper

import com.example.truckercore._test_data_provider.TestTrailerDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.modules.fleet.trailer.dto.TrailerDto
import com.example.truckercore.model.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.model.modules.fleet.trailer.enums.TrailerBrand
import com.example.truckercore.model.modules.fleet.trailer.enums.TrailerCategory
import com.example.truckercore.model.modules.fleet.trailer.mapper.TrailerMapper
import com.example.truckercore.model.modules.user.dto.UserDto
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

class TrailerMapperTest : KoinTest {

    private val mapper: TrailerMapper by inject()

    private val entity = TestTrailerDataProvider.getBaseEntity()
    private val dto = TestTrailerDataProvider.getBaseDto()

    companion object {
        @BeforeAll
        @JvmStatic
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single { TrailerMapper() }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()

        @JvmStatic
        fun getInvalidDtos(): Array<TrailerDto> {
            return TestTrailerDataProvider.arrInvalidDtos()
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
        assertEquals(entity.plate, createdDto.plate)
        assertEquals(entity.color, createdDto.color)
        assertEquals(entity.brand.name, createdDto.brand)
        assertEquals(entity.category.name, createdDto.category)
        assertEquals(entity.truckId, createdDto.truckId)
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
        assertEquals(dto.plate, createdEntity.plate)
        assertEquals(dto.color, createdEntity.color)
        assertEquals(
            TrailerBrand.convertString(dto.brand),
            createdEntity.brand
        )
        assertEquals(
            TrailerCategory.convertString(dto.category),
            createdEntity.category
        )
        assertEquals(dto.truckId, createdEntity.truckId)
    }

    @Test
    fun `toDto() should throw IllegalMappingArgumentException when the dto is wrong`() {
        // Object
        val wrongDto = TestUserDataProvider.getBaseDto()

        // Call
        val exception = assertThrows<IllegalMappingArgumentException> {
            mapper.toEntity(wrongDto)
        }

        // Assertions
        assertTrue(exception.expected == TrailerDto::class.simpleName)
        assertTrue(exception.received == UserDto::class.simpleName)
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
        assertTrue(exception.expected == Trailer::class.simpleName)
        assertTrue(exception.received == User::class.simpleName)
    }

    @ParameterizedTest
    @MethodSource("getInvalidDtos")
    fun `toEntity() should throw InvalidForMappingException when there are errors`(
        pDto: TrailerDto
    ) {
        val exception = assertThrows<InvalidForMappingException> {
            mapper.toEntity(pDto)
        }
        assertTrue(exception.dto is TrailerDto)
    }

}
