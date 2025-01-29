package com.example.truckercore.unit.shared.modules.storage_file.use_cases

import com.example.truckercore._test_data_provider.TestStorageFileDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.modules.storage_file.mapper.StorageFileMapper
import com.example.truckercore.shared.modules.storage_file.repository.StorageFileRepository
import com.example.truckercore.shared.modules.storage_file.use_cases.implementations.UpdateStorageFileUseCaseImpl
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.CheckStorageFileExistenceUseCase
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.UpdateStorageFileUseCase
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UpdateStorageFileUseCaseImplTest {

    private val repository: StorageFileRepository = mockk()
    private val checkExistence: CheckStorageFileExistenceUseCase = mockk()
    private val permissionService: PermissionService = mockk()
    private val validatorService: ValidatorService = mockk()
    private val mapper: StorageFileMapper = mockk()
    private lateinit var useCase: UpdateStorageFileUseCase

    private val user = TestUserDataProvider.getBaseEntity()
    private val file = TestStorageFileDataProvider.getBaseEntity()
    private val dto = TestStorageFileDataProvider.getBaseDto()

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = UpdateStorageFileUseCaseImpl(
            repository,
            checkExistence,
            permissionService,
            validatorService,
            mapper
        )
    }

    @Test
    fun `should update file when user has permission and file exists`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.UPDATE_STORAGE_FILE
            )
        } returns true
        coEvery { checkExistence.execute(user, file.id!!) } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(file) } returns Unit
        every { mapper.toDto(file) } returns dto
        coEvery { repository.update(dto) } returns flowOf(Response.Success(Unit))

        // Call
        val result = useCase.execute(user, file).single()

        // Assertions
        assertTrue(result is Response.Success)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_STORAGE_FILE)
            checkExistence.execute(user, file.id!!)
            validatorService.validateEntity(file)
            mapper.toDto(file)
            repository.update(dto)
        }
    }

    @Test
    fun `should return error when user does not have permission to update`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.UPDATE_STORAGE_FILE
            )
        } returns false

        // Call
        val result = useCase.execute(user, file).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_STORAGE_FILE)
        }
    }

    @Test
    fun `should return error when file existence check returns an error`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.UPDATE_STORAGE_FILE
            )
        } returns true
        coEvery { checkExistence.execute(user, file.id!!) } returns flowOf(
            Response.Error(
                NullPointerException()
            )
        )

        // Call
        val result = useCase.execute(user, file).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_STORAGE_FILE)
            checkExistence.execute(user, file.id!!)
        }
    }

    @Test
    fun `should return error when file existence check returns empty`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.UPDATE_STORAGE_FILE
            )
        } returns true
        coEvery { checkExistence.execute(user, file.id!!) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, file).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is ObjectNotFoundException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_STORAGE_FILE)
            checkExistence.execute(user, file.id!!)
        }
    }

    @Test
    fun `should return error when repository returns an error during update`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.UPDATE_STORAGE_FILE
            )
        } returns true
        coEvery { checkExistence.execute(user, file.id!!) } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(file) } returns Unit
        every { mapper.toDto(file) } returns dto
        coEvery { repository.update(dto) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, file).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_STORAGE_FILE)
            checkExistence.execute(user, file.id!!)
            validatorService.validateEntity(file)
            mapper.toDto(file)
            repository.update(dto)
        }
    }

    @Test
    fun `should return empty when repository returns empty`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.UPDATE_STORAGE_FILE
            )
        } returns true
        coEvery { checkExistence.execute(user, file.id!!) } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(file) } returns Unit
        every { mapper.toDto(file) } returns dto
        coEvery { repository.update(dto) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, file).single()

        // Assertions
        assertTrue(result is Response.Empty)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_STORAGE_FILE)
            checkExistence.execute(user, file.id!!)
            validatorService.validateEntity(file)
            mapper.toDto(file)
            repository.update(dto)
        }
    }

    @Test
    fun `should return error when any unexpected error occurs in flow`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.UPDATE_STORAGE_FILE
            )
        } returns true
        coEvery { checkExistence.execute(user, file.id!!) } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(file) } returns Unit
        every { mapper.toDto(file) } throws NullPointerException()

        // Call
        val result = useCase.execute(user, file).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_STORAGE_FILE)
            checkExistence.execute(user, file.id!!)
            validatorService.validateEntity(file)
            mapper.toDto(file)
        }
    }

}