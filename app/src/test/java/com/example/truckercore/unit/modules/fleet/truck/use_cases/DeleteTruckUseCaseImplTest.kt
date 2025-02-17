package com.example.truckercore.unit.modules.fleet.truck.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.truck.repository.TruckRepository
import com.example.truckercore.modules.fleet.truck.use_cases.implementations.DeleteTruckUseCaseImpl
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.CheckTruckExistenceUseCase
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.DeleteTruckUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.utils.sealeds.Response
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
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class DeleteTruckUseCaseImplTest : KoinTest {

    private val requiredPermission = Permission.DELETE_TRUCK
    private val permissionService: PermissionService by inject()
    private val repository: TruckRepository by inject()
    private val checkExistence: CheckTruckExistenceUseCase by inject()
    private val useCase: DeleteTruckUseCase by inject()

    val user: User = mockk(relaxed = true)
    val id = "idToDelete"

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
                        single<CheckTruckExistenceUseCase> { mockk() }
                        single<DeleteTruckUseCase> {
                            DeleteTruckUseCaseImpl(
                                Permission.DELETE_TRUCK,
                                get(), get(), get()
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
    fun `execute() should return success when user has permission and object is found`() = runTest {
        // Arrange
        every { checkExistence.execute(any(), any()) } returns flowOf(Response.Success(Unit))
        every { permissionService.canPerformAction(any(), any()) } returns true
        every { repository.delete(any()) } returns flowOf(Response.Success(Unit))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Success)
        verifyOrder {
            checkExistence.execute(user, id)
            permissionService.canPerformAction(user, requiredPermission)
            repository.delete(id)
        }
    }

    @Test
    fun `execute() should throw ObjectNotFoundException when the entity does not exist`() =
        runTest {
            // Arrange
            every { checkExistence.execute(any(), any()) } returns flowOf(Response.Empty)

            // Call
            assertThrows<ObjectNotFoundException> {
                useCase.execute(user, id).single()
            }

            // Assertions
            verify {
                checkExistence.execute(user, id)
            }
        }

    @Test
    fun `execute() should throw UnauthorizedAccessException when the user does not have auth`() =
        runTest {
            // Arrange
            every { checkExistence.execute(any(), any()) } returns flowOf(Response.Success(Unit))
            every { permissionService.canPerformAction(any(), any()) } returns false

            // Call
            assertThrows<UnauthorizedAccessException> {
                useCase.execute(user, id).single()
            }

            // Assertions
            verifyOrder {
                checkExistence.execute(user, id)
                permissionService.canPerformAction(user, requiredPermission)
            }
        }

}
