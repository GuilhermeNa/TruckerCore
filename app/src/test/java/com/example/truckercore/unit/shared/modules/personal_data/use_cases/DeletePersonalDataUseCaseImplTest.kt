package com.example.truckercore.unit.shared.modules.personal_data.use_cases

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.implementations.DeletePersonalDataUseCaseImpl
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.CheckPersonalDataExistenceUseCase
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.DeletePersonalDataUseCase
import com.example.truckercore.shared.utils.sealeds.Response
import com.google.common.base.Verify.verify
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

class DeletePersonalDataUseCaseImplTest {

    private val repository: PersonalDataRepository = mockk()
    private val checkExistence: CheckPersonalDataExistenceUseCase = mockk()
    private val permissionService: PermissionService = mockk()
    private lateinit var useCase: DeletePersonalDataUseCase
    private val user = TestUserDataProvider.getBaseEntity()
    private val id = "id"

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = DeletePersonalDataUseCaseImpl(repository, checkExistence, permissionService)
    }

    @Test
    fun `should delete entity when user has permission and data exists`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_PERSONAL_DATA) } returns true
        coEvery { checkExistence.execute(user, id) } returns flowOf(Response.Success(Unit))
        coEvery { repository.delete(id) } returns flowOf(Response.Success(Unit))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Success)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.DELETE_PERSONAL_DATA)
            checkExistence.execute(user, id)
            repository.delete(id)
        }
    }

    @Test
    fun `should return error when user does not have permission for delete`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_PERSONAL_DATA) } returns false

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        verify {
            permissionService.canPerformAction(user, Permission.DELETE_PERSONAL_DATA)
        }
    }

    @Test
    fun `should return error when any unexpected exception occurs`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(user, Permission.DELETE_PERSONAL_DATA)
        } throws NullPointerException()

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        verify {
            permissionService.canPerformAction(user, Permission.DELETE_PERSONAL_DATA)
        }
    }

    @Test
    fun `should return error when database returns an error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_PERSONAL_DATA) } returns true
        coEvery { checkExistence.execute(user, id) } returns flowOf(Response.Success(Unit))
        coEvery { repository.delete(id) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.DELETE_PERSONAL_DATA)
            checkExistence.execute(user, id)
            repository.delete(id)
        }
    }

    @Test
    fun `should return error when entity does not exist`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_PERSONAL_DATA) } returns true
        coEvery { checkExistence.execute(user, id) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is ObjectNotFoundException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.DELETE_PERSONAL_DATA)
            checkExistence.execute(user, id)
        }
    }

    @Test
    fun `should return error when existence check failed`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.DELETE_PERSONAL_DATA) } returns true
        coEvery {
            checkExistence.execute(user, id)
        } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.DELETE_PERSONAL_DATA)
            checkExistence.execute(user, id)
        }
    }

}