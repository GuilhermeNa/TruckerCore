package com.example.truckercore.unit.modules.fleet.trailer.use_cases

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.trailer.repository.TrailerRepository
import com.example.truckercore.modules.fleet.trailer.use_cases.implementations.DeleteTrailerUseCaseImpl
import com.example.truckercore.modules.fleet.trailer.use_cases.interfaces.CheckTrailerExistenceUseCase
import com.example.truckercore.modules.fleet.trailer.use_cases.interfaces.DeleteTrailerUseCase
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

class DeleteTrailerUseCaseImplTest {

    private val repository: TrailerRepository = mockk()
    private val checkExistence: CheckTrailerExistenceUseCase = mockk()
    private val permissionService: PermissionService = mockk()
    private lateinit var useCase: DeleteTrailerUseCase
    private val user = TestUserDataProvider.getBaseEntity()
    private val id = "id"

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = DeleteTrailerUseCaseImpl(repository, checkExistence, permissionService)
    }

    @Test
    fun `should delete trailer when user has permission and trailer exists`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_TRAILER) } returns true
        coEvery { checkExistence.execute(user, id) } returns flowOf(Response.Success(Unit))
        coEvery { repository.delete(id) } returns flowOf(Response.Success(Unit))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Success)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.DELETE_TRAILER)
            checkExistence.execute(user, id)
            repository.delete(id)
        }
    }

    @Test
    fun `should return error when user does not have permission for delete`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_TRAILER) } returns false

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        verify {
            permissionService.canPerformAction(user, Permission.DELETE_TRAILER)
        }
    }

    @Test
    fun `should return error when any unexpected exception occurs`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(user, Permission.DELETE_TRAILER)
        } throws NullPointerException()

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        verify {
            permissionService.canPerformAction(user, Permission.DELETE_TRAILER)
        }
    }

    @Test
    fun `should return error when database returns an error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_TRAILER) } returns true
        coEvery { checkExistence.execute(user, id) } returns flowOf(Response.Success(Unit))
        coEvery { repository.delete(id) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.DELETE_TRAILER)
            checkExistence.execute(user, id)
            repository.delete(id)
        }
    }

    @Test
    fun `should return error when trailer does not exist`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_TRAILER) } returns true
        coEvery { checkExistence.execute(user, id) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is ObjectNotFoundException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.DELETE_TRAILER)
            checkExistence.execute(user, id)
        }
    }

    @Test
    fun `should return error when existence check failed`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_TRAILER) } returns true
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
            permissionService.canPerformAction(user, Permission.DELETE_TRAILER)
            checkExistence.execute(user, id)
        }
    }

}