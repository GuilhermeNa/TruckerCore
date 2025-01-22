package com.example.truckercore.unit.modules.employee.driver.use_cases

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.employee.driver.repository.DriverRepository
import com.example.truckercore.modules.employee.driver.use_cases.implementations.DeleteDriverUseCaseImpl
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.DeleteDriverUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.use_cases.interfaces.CheckUserExistenceUseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.sealeds.Response
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

class DeleteDriverUseCaseImplTest {

    private lateinit var repository: DriverRepository
    private lateinit var checkExistence: CheckUserExistenceUseCase
    private lateinit var permissionService: PermissionService
    private lateinit var useCase: DeleteDriverUseCase
    private lateinit var user: User
    private val id = "id"

    @BeforeEach
    fun setup() {
        mockStaticLog()
        user = TestUserDataProvider.getBaseEntity()
        permissionService = mockk(relaxed = true)
        checkExistence = mockk(relaxed = true)
        repository = mockk(relaxed = true)
        useCase = DeleteDriverUseCaseImpl(repository, checkExistence, permissionService)
    }

    @Test
    fun `should delete correctly the entity when its found`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_DRIVER) } returns true
        coEvery { checkExistence.execute(user, id) } returns flowOf(Response.Success(Unit))
        coEvery { repository.delete(id) } returns flowOf(Response.Success(Unit))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Success)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.DELETE_DRIVER)
            checkExistence.execute(user, id)
            repository.delete(id)
        }
    }

    @Test
    fun `should return an error when the user does not have auth`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(user, Permission.DELETE_DRIVER)
        } returns false

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        verify { permissionService.canPerformAction(user, Permission.DELETE_DRIVER) }
    }

    @Test
    fun `should return an error when the entity is not found in database`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_DRIVER) } returns true
        coEvery { checkExistence.execute(user, id) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is ObjectNotFoundException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.DELETE_DRIVER)
            checkExistence.execute(user, id)
        }
    }

    @Test
    fun `should return an error when the database returns an error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_DRIVER) } returns true
        coEvery { checkExistence.execute(user, id) } returns flowOf(Response.Success(Unit))
        coEvery { repository.delete(id) } returns flowOf(Response.Error(Exception()))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.DELETE_DRIVER)
            checkExistence.execute(user, id)
            repository.delete(id)
        }
    }

}