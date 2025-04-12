package com.example.truckercore.unit.model.modules.fleet.truck.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.fleet.truck.repository.TruckRepository
import com.example.truckercore.model.modules.fleet.truck.use_cases.implementations.CheckTruckExistenceUseCaseImpl
import com.example.truckercore.model.modules.fleet.truck.use_cases.interfaces.CheckTruckExistenceUseCase
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class CheckTruckExistenceUseCaseImplTest : KoinTest {

    private val permissionService: PermissionService by inject()
    private val repository: TruckRepository by inject()
    private val useCase: CheckTruckExistenceUseCase by inject()

    private val requiredPermission = Permission.VIEW_TRUCK
    private val user = mockk<User>(relaxed = true)
    private val id = "testTruckId"

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<PermissionService> { mockk() }
                        single<TruckRepository> { mockk() }
                        single<CheckTruckExistenceUseCase> {
                            CheckTruckExistenceUseCaseImpl(
                                get(),
                                get(),
                                Permission.VIEW_TRUCK
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
    fun `execute() should return a response success when truck object is found`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(any(), any()) } returns true
        every { repository.entityExists(id) } returns flowOf(AppResponse.Success(Unit))

        // Call
        val response = useCase.execute(user, id).single()

        // Assertions
        assertTrue(response is AppResponse.Success)

        verifyOrder {
            permissionService.canPerformAction(user, requiredPermission)
            repository.entityExists(id)
        }
    }

    @Test
    fun `execute() should throw UnauthorizedAccessException when user has no auth`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(any(), any()) } returns false

        // Call
        val exception = assertThrows<UnauthorizedAccessException> {
            useCase.execute(user, id).single()
        }

        // Assertions
        assertTrue(exception.permission == Permission.VIEW_TRUCK)
        verify {
            permissionService.canPerformAction(user, requiredPermission)
        }
    }

    @Test
    fun `execute() should return empty when the truck object is not found`() = runTest {
        // Behavior
        every { permissionService.canPerformAction(any(), any()) } returns true
        every { repository.entityExists(id) } returns flowOf(AppResponse.Empty)

        // Call
        val response = useCase.execute(user, id).single()

        // Assertions
        assertTrue(response is AppResponse.Empty)
        verifyOrder {
            permissionService.canPerformAction(user, requiredPermission)
            repository.entityExists(id)
        }
    }
}
