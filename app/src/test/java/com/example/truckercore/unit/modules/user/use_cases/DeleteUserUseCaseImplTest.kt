package com.example.truckercore.unit.modules.user.use_cases

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.implementations.DeleteUserUseCaseImpl
import com.example.truckercore.modules.user.use_cases.interfaces.CheckUserExistenceUseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.utils.sealeds.Response
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteUserUseCaseImplTest {

    private lateinit var deleteUserUseCase: DeleteUserUseCaseImpl
    private val repository: UserRepository = mockk()
    private val checkExistence: CheckUserExistenceUseCase = mockk()
    private val permissionService: PermissionService = mockk()

    private val user = TestUserDataProvider.getBaseEntity()
    private val id = "userId"

    @BeforeEach
    fun setup() {
        mockStaticLog()
        deleteUserUseCase = DeleteUserUseCaseImpl(
            repository,
            checkExistence,
            permissionService,
            Permission.DELETE_USER
        )
    }

    @Test
    fun `should delete user successfully if user exists and has permission`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_USER) } returns true
        coEvery { checkExistence.execute(user, id) } returns flowOf(Response.Success(Unit))
        coEvery { repository.delete(id) } returns flowOf(Response.Success(Unit))

        // Act
        val result = deleteUserUseCase.execute(user, id).single()

        // Assert
        assertTrue(result is Response.Success)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.DELETE_USER)
            checkExistence.execute(user, id)
            repository.delete(id)
        }
    }

    @Test
    fun `should handle unauthorized access when user lacks permission`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_USER) } returns false

        // Act
        val result = deleteUserUseCase.execute(user, id).toList()

        // Assert
        assertTrue(result.first() is Response.Error)
        verify {
            permissionService.canPerformAction(user, Permission.DELETE_USER)
        }

    }

    @Test
    fun `should handle non-existent user for deletion`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_USER) } returns true
        coEvery { checkExistence.execute(user, id) } returns flowOf(Response.Empty)

        // Act
        val result = deleteUserUseCase.execute(user, id).single()

        // Assert
        assertTrue(result is Response.Error && result.exception is ObjectNotFoundException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.DELETE_USER)
            checkExistence.execute(user, id)
        }

    }

    @Test
    fun `should handle failure in existence check`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_USER) } returns true
        coEvery {
            checkExistence.execute(
                user,
                id
            )
        } returns flowOf(Response.Error(Exception("Existence check failed")))

        // Act
        val result = deleteUserUseCase.execute(user, id).single()

        // Assert
        assertTrue(result is Response.Error)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.DELETE_USER)
            checkExistence.execute(user, id)
        }
    }

    @Test
    fun `should handle unexpected error during deletion`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_USER) } returns true
        coEvery { checkExistence.execute(user, id) } returns flowOf(Response.Success(Unit))
        coEvery { repository.delete(id) } returns flowOf(Response.Error(Exception("Unexpected error")))

        // Act
        val result = deleteUserUseCase.execute(user, id).single()

        // Assert
        assertTrue(result is Response.Error)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.DELETE_USER)
            checkExistence.execute(user, id)
            repository.delete(id)
        }
    }

}