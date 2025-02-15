/*
package com.example.truckercore.unit.modules.fleet.truck.use_cases

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.truck.repository.TruckRepository
import com.example.truckercore.modules.fleet.truck.use_cases.implementations.DeleteTruckUseCaseImpl
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.CheckTruckExistenceUseCase
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.DeleteTruckUseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.utils.sealeds.Response
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteTruckUseCaseImplTest {

    private val repository: TruckRepository = mockk()
    private val checkExistence: CheckTruckExistenceUseCase = mockk()
    private val permissionService: PermissionService = mockk()
    private lateinit var useCase: DeleteTruckUseCase
    private val user = TestUserDataProvider.getBaseEntity()
    private val id = "id"

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = DeleteTruckUseCaseImpl(repository, checkExistence, permissionService, Permission.DELETE_TRUCK)
    }

    @Test
    fun `should delete truck when user has permission and truck exists`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_TRUCK) } returns true
        coEvery { checkExistence.execute(user, id) } returns flowOf(Response.Success(Unit))
        coEvery { repository.delete(id) } returns flowOf(Response.Success(Unit))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Success)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.DELETE_TRUCK)
            checkExistence.execute(user, id)
            repository.delete(id)
        }
    }

    @Test
    fun `should return error when user does not have permission for delete`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_TRUCK) } returns false

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        verify {
            permissionService.canPerformAction(user, Permission.DELETE_TRUCK)
        }
    }

    @Test
    fun `should return error when any unexpected exception occurs`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.DELETE_TRUCK
            )
        } throws NullPointerException()

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        verify {
            permissionService.canPerformAction(user, Permission.DELETE_TRUCK)
        }
    }

    @Test
    fun `should return error when database returns an error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_TRUCK) } returns true
        coEvery { checkExistence.execute(user, id) } returns flowOf(Response.Success(Unit))
        coEvery { repository.delete(id) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.DELETE_TRUCK)
            checkExistence.execute(user, id)
            repository.delete(id)
        }
    }

    @Test
    fun `should return error when truck does not exist`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_TRUCK) } returns true
        coEvery { checkExistence.execute(user, id) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is ObjectNotFoundException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.DELETE_TRUCK)
            checkExistence.execute(user, id)
        }
    }

    @Test
    fun `should return error when existence check failed`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_TRUCK) } returns true
        coEvery { checkExistence.execute(user, id) } returns flowOf(
            Response.Error(
                NullPointerException()
            )
        )

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.DELETE_TRUCK)
            checkExistence.execute(user, id)
        }
    }

}*/
