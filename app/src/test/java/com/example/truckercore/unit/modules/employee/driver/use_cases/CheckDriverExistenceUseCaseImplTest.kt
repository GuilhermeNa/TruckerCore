package com.example.truckercore.unit.modules.employee.driver.use_cases

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.employee.driver.repository.DriverRepository
import com.example.truckercore.modules.employee.driver.use_cases.implementations.CheckDriverExistenceUseCaseImpl
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.CheckDriverExistenceUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.sealeds.Response
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CheckDriverExistenceUseCaseImplTest {

    private lateinit var repository: DriverRepository
    private lateinit var permissionService: PermissionService
    private lateinit var useCase: CheckDriverExistenceUseCase
    private lateinit var user: User
    private val id = "id"

    @BeforeEach
    fun setup() {
        mockStaticLog()
        repository = mockk(relaxed = true)
        permissionService = mockk(relaxed = true)
        useCase = CheckDriverExistenceUseCaseImpl(repository, permissionService)
        user = TestUserDataProvider.getBaseEntity()
    }

    @Test
    fun`should execute correctly when data correspond the expected`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_DRIVER) } returns true
        coEvery { repository.entityExists(id) } returns flowOf(Response.Success(true))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Success && result.data)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_DRIVER)
            repository.entityExists(id)
        }
    }

    @Test
    fun`should return an error when user has no auth`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_DRIVER) } returns false

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        coVerify {
            permissionService.canPerformAction(user, Permission.VIEW_DRIVER)
        }
    }

    @Test
    fun`should return a false response when entity is not found`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_DRIVER) } returns true
        coEvery { repository.entityExists(id) } returns flowOf(Response.Success(false))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Success)
        assertFalse((result as Response.Success).data)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_DRIVER)
            repository.entityExists(id)
        }
    }

    @Test
    fun`should return error when receive an error from database`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_DRIVER) } returns true
        coEvery { repository.entityExists(id) } returns flowOf(Response.Error(Exception()))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_DRIVER)
            repository.entityExists(id)
        }
    }

    @Test
    fun`should return error when any unexpected error occurs`() = runTest {
        // Arrange
        val blankId = ""

        // Call
        val result = useCase.execute(user, blankId).single()

        // Assertions
        assertTrue(result is Response.Error)
    }

}