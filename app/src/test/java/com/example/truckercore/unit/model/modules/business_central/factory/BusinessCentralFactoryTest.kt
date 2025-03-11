package com.example.truckercore.unit.model.modules.business_central.factory

import com.example.truckercore.model.modules.business_central.factory.BusinessCentralFactory
import com.example.truckercore.model.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.model.shared.enums.PersistenceStatus
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.expressions.toLocalDateTime
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import java.time.Duration
import java.time.LocalDateTime
import kotlin.test.assertEquals

internal class BusinessCentralFactoryTest : KoinTest {

    private val validator: ValidatorService by inject()
    private val factory: BusinessCentralFactory by inject()

    @BeforeEach
    fun setup() {
        startKoin {
            modules(
                module {
                    single<ValidatorService> { mockk() }
                    single<BusinessCentralMapper> { BusinessCentralMapper() }
                    single { BusinessCentralFactory(get(), get()) }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()

    @Test
    fun `should create a business central correctly`() {
        // Arrange
        val centralId = "centralId"
        val userId = "userId"

        every { validator.validateForCreation(any()) } just Runs

        // Call
        val result = factory.create(centralId = centralId, userId = userId)

        // Assert
        assertEquals(result.businessCentralId, "")
        assertEquals(result.id, centralId)
        assertEquals(result.lastModifierId, userId)
        assertTrue(
            Duration.between(result.creationDate?.toLocalDateTime(), LocalDateTime.now())
                .abs() <= Duration.ofSeconds(1)
        )
        assertTrue(
            Duration.between(result.lastUpdate?.toLocalDateTime(), LocalDateTime.now())
                .abs() <= Duration.ofSeconds(1)
        )
        assertEquals(result.persistenceStatus, PersistenceStatus.PERSISTED.name)
        assertTrue(result.authorizedUserIds!!.contains(userId))
        assertEquals(result.keys, 1)

        verify { validator.validateForCreation(any()) }
    }


}