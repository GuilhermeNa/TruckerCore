package com.example.truckercore.unit.model.modules.user.factory

import com.example.truckercore.model.infrastructure.security.permissions.configs.DefaultPermissions
import com.example.truckercore.model.infrastructure.security.permissions.enums.Level
import com.example.truckercore.model.modules.user.enums.PersonCategory
import com.example.truckercore.model.modules.user.factory.UserFactory
import com.example.truckercore.model.modules.user.mapper.UserMapper
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

internal class UserFactoryTest : KoinTest {

    private val validator: ValidatorService by inject()
    private val factory: UserFactory by inject()

    @BeforeEach
    fun setup() {
        startKoin {
            modules(
                module {
                    single<ValidatorService> { mockk() }
                    single<UserMapper> { UserMapper() }
                    single { UserFactory(get(), get()) }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()

    @Test
    fun `should create an user correctly`() {
        // Arrange
        val centralId = "centralId"
        val uid = "userId"
        val personFlag = PersonCategory.ADMIN
        val personLevel = Level.MASTER

        every { validator.validateForCreation(any()) } just Runs

        // Call
        val result = factory.create(
            centralId = centralId, uid = uid, personCategory = personFlag, personLevel = personLevel
        )

        // Assert
        assertEquals(result.businessCentralId, centralId)
        assertEquals(result.id, uid)
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
        assertEquals(result.isVip, false)
        assertEquals(result.vipStart, null)
        assertEquals(result.vipEnd, null)
        assertEquals(result.level, Level.MASTER.name)
        assertEquals(result.personFLag, PersonCategory.ADMIN.name)
        assertEquals(result.permissions?.size, DefaultPermissions.get(personLevel).size)
        DefaultPermissions.get(personLevel).forEach {
            assertTrue(result.permissions!!.contains(it.name))
        }

        verify { validator.validateForCreation(any()) }
    }
}