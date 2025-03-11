package com.example.truckercore.unit.model.modules.person.employee.driver.factory

import com.example.truckercore.model.modules.person.employee.driver.factory.DriverFactory
import com.example.truckercore.model.modules.person.employee.driver.mapper.DriverMapper
import com.example.truckercore.model.modules.person.employee.shared.enums.EmployeeStatus
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

internal class DriverFactoryTest : KoinTest {

    private val validator: ValidatorService by inject()
    private val factory: DriverFactory by inject()

    @BeforeEach
    fun setup() {
        startKoin {
            modules(
                module {
                    single<ValidatorService> { mockk() }
                    single<DriverMapper> { DriverMapper() }
                    single { DriverFactory(get(), get()) }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()

    @Test
    fun `should create a driver correctly`() {
        // Arrange
        val centralId = "centralId"
        val uid = "userId"
        val personId = "personId"
        val name = "Test Name"
        val email = "a@email.com"

        every { validator.validateForCreation(any()) } just Runs

        // Call
        val result = factory.create(
            centralId = centralId, userId = uid, personId = personId, name = name, email = email
        )

        // Assert
        assertEquals(result.businessCentralId, centralId)
        assertEquals(result.id, personId)
        assertEquals(result.lastModifierId, uid)
        assertTrue(
            Duration.between(result.creationDate?.toLocalDateTime(), LocalDateTime.now())
                .abs() <= Duration.ofSeconds(1)
        )
        assertTrue(
            Duration.between(result.lastUpdate?.toLocalDateTime(), LocalDateTime.now())
                .abs() <= Duration.ofSeconds(1)
        )
        assertEquals(result.persistenceStatus, PersistenceStatus.PERSISTED.name)
        assertEquals(result.userId, uid)
        assertEquals(result.name, name)
        assertEquals(result.email, email)
        assertEquals(result.employeeStatus, EmployeeStatus.WORKING.name)

        verify { validator.validateForCreation(any()) }

    }

}