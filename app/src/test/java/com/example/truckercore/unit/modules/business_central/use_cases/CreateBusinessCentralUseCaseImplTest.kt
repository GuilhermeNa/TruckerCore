package com.example.truckercore.unit.modules.business_central.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.entity.BusinessCentral
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.business_central.repository.BusinessCentralRepository
import com.example.truckercore.modules.business_central.use_cases.implementations.CreateBusinessCentralUseCaseImpl
import com.example.truckercore.modules.business_central.use_cases.interfaces.CreateBusinessCentralUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.InvalidStateException
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.sealeds.Response
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

class CreateBusinessCentralUseCaseImplTest : KoinTest {

    private val requirePermission: Permission = Permission.CREATE_BUSINESS_CENTRAL
    private val permissionService: PermissionService by inject()
    private val repository: BusinessCentralRepository by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: BusinessCentralMapper by inject()
    private val useCase: CreateBusinessCentralUseCase by inject()

    private val user = mockk<User>()
    private val bCentral = mockk<BusinessCentral>()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<PermissionService> { mockk() }
                        single<BusinessCentralRepository> { mockk() }
                        single<ValidatorService> { mockk() }
                        single<BusinessCentralMapper> { mockk() }
                        single<CreateBusinessCentralUseCase> {
                            CreateBusinessCentralUseCaseImpl(
                                Permission.CREATE_BUSINESS_CENTRAL,
                                get(), get(), get(), get()
                            )
                        }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()
    }

    @Test
    fun `execute() should return success when data correctly created`() = runTest {
        // Arrange
        val dto = mockk<BusinessCentralDto>()
        val id = "newObjectId"
        every { user.level } returns Level.MASTER
        every { permissionService.canPerformAction(any(), any()) } returns true
        every { validatorService.validateForCreation(any()) } returns Unit
        every { mapper.toDto(any()) } returns dto
        every { repository.create(any()) } returns flowOf(Response.Success(id))

        // Call
        val result = useCase.execute(user, bCentral).single()

        // Assertions
        assertEquals(id, (result as Response.Success).data)
        verifyOrder {
            user.level
            permissionService.canPerformAction(user, requirePermission)
            validatorService.validateForCreation(bCentral)
            mapper.toDto(bCentral)
            repository.create(dto)
        }
    }

    @Test
    fun `execute() should throw InvalidStateException when user is in wrong Level`() = runTest {
        // Arrange
        every { user.level } returns Level.MODERATOR

        // Call
        assertThrows<InvalidStateException> {
            useCase.execute(user, bCentral).single()
        }
    }

    @Test
    fun `execute() should throw UnauthorizedAccessException when user has no auth`() = runTest {
        // Arrange
        every { user.level } returns Level.MASTER
        every { permissionService.canPerformAction(any(), any()) } returns false

        // Call
        assertThrows<UnauthorizedAccessException> {
            useCase.execute(user, bCentral).single()
        }

        // Assertions
        verifyOrder {
            user.level
            permissionService.canPerformAction(user, requirePermission)
        }
    }

}
