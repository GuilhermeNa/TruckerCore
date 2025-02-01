package com.example.truckercore.unit.shared.modules.storage_file.use_cases

import com.example.truckercore._test_data_provider.TestStorageFileDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.shared.modules.storage_file.mapper.StorageFileMapper
import com.example.truckercore.shared.modules.storage_file.repository.StorageFileRepository
import com.example.truckercore.shared.modules.storage_file.use_cases.implementations.GetStorageFileByParentIdUseCaseImpl
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.GetStorageFileByParentIdUseCase
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
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

class GetStorageFileByParentIdUseCaseImplTest {

    private lateinit var useCase: GetStorageFileByParentIdUseCase
    private var repository: StorageFileRepository = mockk()
    private var permissionService: PermissionService = mockk()
    private var validatorService: ValidatorService = mockk()
    private var mapper: StorageFileMapper = mockk()
    private val user = TestUserDataProvider.getBaseEntity()
    private val parentId = "parentId"
    private val storageFile = TestStorageFileDataProvider.getBaseEntity()
    private val dto = TestStorageFileDataProvider.getBaseDto()

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = GetStorageFileByParentIdUseCaseImpl(
            repository,
            permissionService,
            validatorService,
            mapper
        )
    }

    @Test
    fun `should retrieve the list of files when user has permission and data exists`() = runTest {
        // Arrange
        val dtoList = listOf(dto)
        val storageFileList = listOf(storageFile)
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_STORAGE_FILE
            )
        } returns true
        coEvery { repository.fetchByParentId(parentId) } returns flowOf(Response.Success(dtoList))
        every { validatorService.validateDto(dto) } returns Unit
        every { mapper.toEntity(dto) } returns storageFile

        // Call
        val result = useCase.execute(user, parentId).single()

        // Assertions
        assertTrue(result is Response.Success && result.data == storageFileList)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)
            repository.fetchByParentId(parentId)
            validatorService.validateDto(dto)
            mapper.toEntity(dto)
        }
    }

    @Test
    fun `should return an error when the user does not have permission`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_STORAGE_FILE
            )
        } returns false

        // Call
        val result = useCase.execute(user, parentId).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        coVerify {
            permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)
        }
    }

    @Test
    fun `should return an error when the repository returns an error`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_STORAGE_FILE
            )
        } returns true
        coEvery { repository.fetchByParentId(parentId) } returns flowOf(
            Response.Error(
                NullPointerException()
            )
        )

        // Call
        val result = useCase.execute(user, parentId).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)
            repository.fetchByParentId(parentId)
        }
    }

    @Test
    fun `should return empty when no files are found for parent id`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_STORAGE_FILE
            )
        } returns true
        coEvery { repository.fetchByParentId(parentId) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, parentId).single()

        // Assertions
        assertTrue(result is Response.Empty)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)
            repository.fetchByParentId(parentId)
        }
    }

    @Test
    fun `should return error when any error occurs during dto validation or mapping`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_STORAGE_FILE
            )
        } returns true
        val dtoList = listOf(dto)
        coEvery { repository.fetchByParentId(parentId) } returns flowOf(Response.Success(dtoList))
        every { validatorService.validateDto(dto) } throws NullPointerException()

        // Call
        val result = useCase.execute(user, parentId).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)
            repository.fetchByParentId(parentId)
            validatorService.validateDto(dto)
        }
    }

}