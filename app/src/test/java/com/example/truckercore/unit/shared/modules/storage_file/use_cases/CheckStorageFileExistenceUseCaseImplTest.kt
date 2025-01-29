package com.example.truckercore.unit.shared.modules.storage_file.use_cases

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.shared.modules.storage_file.repository.StorageFileRepository
import com.example.truckercore.shared.modules.storage_file.use_cases.implementations.CheckStorageFileExistenceUseCaseImpl
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.CheckStorageFileExistenceUseCase
import com.example.truckercore.shared.sealeds.Response
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CheckStorageFileExistenceUseCaseImplTest {

    private val repository: StorageFileRepository = mockk()
    private val permissionService: PermissionService = mockk()
    private lateinit var useCase: CheckStorageFileExistenceUseCase
    private val user = TestUserDataProvider.getBaseEntity()
    private val id = "id"

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = CheckStorageFileExistenceUseCaseImpl(repository, permissionService)
    }

    @Test
    fun `should return success when user has permission and file exists`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE) } returns true
        coEvery { repository.entityExists(id) } returns flowOf(Response.Success(Unit))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Success)
    }

    @Test
    fun `should return empty when file does not exist`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE) } returns true
        coEvery { repository.entityExists(id) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Empty)
    }

    @Test
    fun `should return error when user does not have permission`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE) } returns false

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
    }

    @Test
    fun `should return error when database returns an error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE) } returns true
        coEvery { repository.entityExists(id) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
    }

    @Test
    fun `should return error when id is blank`() = runTest {
        // Call
        val result = useCase.execute(user, "").single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is IllegalArgumentException)
    }

}