package com.example.truckercore.unit.modules.user.use_cases

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.implementations.CheckUserExistenceUseCaseImpl
import com.example.truckercore.modules.user.use_cases.interfaces.CheckUserExistenceUseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.errors.UnknownErrorException
import com.example.truckercore.shared.sealeds.Response
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CheckUserExistenceUseCaseImplTest {

    private lateinit var useCase: CheckUserExistenceUseCase
    private val repository: UserRepository = mockk()
    private val permissionService: PermissionService = mockk()

    private val user = TestUserDataProvider.getBaseEntity()
    private val id = "id"

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = CheckUserExistenceUseCaseImpl(repository, permissionService)
    }

    @Test
    fun `execute() should call canPerformAction and entityExists in the correct order when user has permission`() =
        runTest {
            // Arrange
            coEvery { permissionService.canPerformAction(user, Permission.VIEW_USER) } returns true
            coEvery { repository.entityExists(id) } returns flowOf(Response.Success(true))

            // Act
            val result = useCase.execute(user, id).first()

            // Assert
            coVerifySequence {
                permissionService.canPerformAction(user, Permission.VIEW_USER)
                repository.entityExists(id)
            }

            assertTrue(result is Response.Success && result.data)
        }

    @Test
    fun `execute() should call handleUnauthorizedPermission when user does not have permission`() =
        runTest {
            // Arrange
            coEvery { permissionService.canPerformAction(user, Permission.VIEW_USER) } returns false

            // Act
            val result = useCase.execute(user, id).first()

            // Assert
            coVerify {
                permissionService.canPerformAction(user, Permission.VIEW_USER) // First call
            }
            assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        }

    @Test
    fun `execute() should handle blank id and return error`() = runTest {
        // Arrange
        val blankId = ""

        // Act
        val result = useCase.execute(user, blankId).first()

        // Assert
        assertTrue(result is Response.Error && result.exception is IllegalArgumentException)
    }

    @Test
    fun `execute() should handle user not found in repository`() = runTest {
        // Arrange
        coEvery { permissionService.canPerformAction(user, Permission.VIEW_USER) } returns true
        coEvery { repository.entityExists(id) } returns flowOf(
            Response.Error(
                ObjectNotFoundException()
            )
        )

        // Act
        val result = useCase.execute(user, id).first()

        // Assert
        assertTrue(result is Response.Error && result.exception is ObjectNotFoundException)
    }

    @Test
    fun `execute() should handle empty response from repository`() = runTest {
        // Arrange
        coEvery { permissionService.canPerformAction(user, Permission.VIEW_USER) } returns true
        coEvery { repository.entityExists(id) } returns flowOf(
            Response.Error(
                ObjectNotFoundException()
            )
        )

        // Act
        val result = useCase.execute(user, id).first()

        // Assert
        assertTrue(result is Response.Error && result.exception is ObjectNotFoundException)
    }

    @Test
    fun `execute() should handle unexpected error`() = runTest {
        // Arrange
        coEvery { permissionService.canPerformAction(user, Permission.VIEW_USER) } returns true
        coEvery { repository.entityExists(id) } throws UnknownErrorException("Unexpected error")

        // Act
        val result = useCase.execute(user, id).first()

        // Assert
        assertTrue(result is Response.Error && result.exception is UnknownErrorException)
    }

}