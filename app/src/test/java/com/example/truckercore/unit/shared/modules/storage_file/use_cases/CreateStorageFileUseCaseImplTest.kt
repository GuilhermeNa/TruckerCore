package com.example.truckercore.unit.shared.modules.storage_file.use_cases

import com.example.truckercore._test_data_provider.TestStorageFileDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.shared.modules.storage_file.mapper.StorageFileMapper
import com.example.truckercore.shared.modules.storage_file.repository.StorageFileRepository
import com.example.truckercore.shared.modules.storage_file.use_cases.implementations.CreateStorageFileUseCaseImpl
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.CreateStorageFileUseCase
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
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

class CreateStorageFileUseCaseImplTest {

    private val repository: StorageFileRepository = mockk()
    private val validatorService: ValidatorService = mockk()
    private val permissionService: PermissionService = mockk()
    private val mapper: StorageFileMapper = mockk()
    private lateinit var useCase: CreateStorageFileUseCase
    private val user = TestUserDataProvider.getBaseEntity()
    private val storageFile = TestStorageFileDataProvider.getBaseEntity()
    private val dto = TestStorageFileDataProvider.getBaseDto()
    private val id = "id"

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase =
            CreateStorageFileUseCaseImpl(repository, validatorService, permissionService, mapper)
    }

    @Test
    fun `should create entity when user has permission and data is valid`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.CREATE_STORAGE_FILE
            )
        } returns true
        every { validatorService.validateForCreation(storageFile) } returns Unit
        every { mapper.toDto(storageFile) } returns dto
        coEvery { repository.create(dto) } returns flowOf(Response.Success(id))

        // Call
        val result = useCase.execute(user, storageFile).single()

        // Assertions
        assertTrue(result is Response.Success && result.data == id)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.CREATE_STORAGE_FILE)
            validatorService.validateForCreation(storageFile)
            mapper.toDto(storageFile)
            repository.create(dto)
        }
    }

    @Test
    fun `should return error when user does not have permission for creation`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.CREATE_STORAGE_FILE
            )
        } returns false

        // Call
        val result = useCase.execute(user, storageFile).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        verify {
            permissionService.canPerformAction(user, Permission.CREATE_STORAGE_FILE)
        }
    }

    @Test
    fun `should return error when any unexpected exception occurs`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.CREATE_STORAGE_FILE
            )
        } throws NullPointerException()

        // Call
        val result = useCase.execute(user, storageFile).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        verify {
            permissionService.canPerformAction(user, Permission.CREATE_STORAGE_FILE)
        }
    }

    @Test
    fun `should return error when database returns an error`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.CREATE_STORAGE_FILE
            )
        } returns true
        every { validatorService.validateForCreation(storageFile) } returns Unit
        every { mapper.toDto(storageFile) } returns dto
        coEvery { repository.create(dto) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, storageFile).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.CREATE_STORAGE_FILE)
            validatorService.validateForCreation(storageFile)
            mapper.toDto(storageFile)
            repository.create(dto)
        }
    }

}