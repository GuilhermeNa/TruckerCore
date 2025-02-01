package com.example.truckercore.unit.modules.fleet.truck.use_cases

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.truck.repository.TruckRepository
import com.example.truckercore.modules.fleet.truck.use_cases.implementations.CheckTruckExistenceUseCaseImpl
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.CheckTruckExistenceUseCase
import com.example.truckercore.shared.utils.sealeds.Response
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CheckTruckExistenceUseCaseImplTest {

    private val repository: TruckRepository = mockk()
    private val permissionService: PermissionService = mockk()
    private lateinit var useCase: CheckTruckExistenceUseCase
    private val user = TestUserDataProvider.getBaseEntity()
    private val id = "id"

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = CheckTruckExistenceUseCaseImpl(repository, permissionService)
    }

    @Test
    fun `should return success when user has permission and truck exists`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_TRUCK) } returns true
        coEvery { repository.entityExists(id) } returns flowOf(Response.Success(Unit))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Success)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_TRUCK)
            repository.entityExists(id)
        }
    }

    @Test
    fun `should return empty when truck does not exist`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_TRUCK) } returns true
        coEvery { repository.entityExists(id) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Empty)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_TRUCK)
            repository.entityExists(id)
        }
    }

    @Test
    fun `should return error when user does not have permission`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_TRUCK) } returns false

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        coVerify {
            permissionService.canPerformAction(user, Permission.VIEW_TRUCK)
        }
    }

    @Test
    fun `should return error when database returns an error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_TRUCK) } returns true
        coEvery { repository.entityExists(id) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_TRUCK)
            repository.entityExists(id)
        }
    }

    @Test
    fun `should return error when id is blank`() = runTest {
        // Call
        val result = useCase.execute(user, "").single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is IllegalArgumentException)
    }

}