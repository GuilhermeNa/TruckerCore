package com.example.truckercore.unit.shared.modules.storage_file.use_cases

import com.example.truckercore._test_data_provider.TestStorageFileDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.modules.storage_file.mapper.StorageFileMapper
import com.example.truckercore.shared.modules.storage_file.repository.StorageFileRepository
import com.example.truckercore.shared.modules.storage_file.use_cases.implementations.GetStorageFileUseCaseImpl
import com.example.truckercore.shared.modules.storage_file.use_cases.interfaces.GetStorageFileUseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.example.truckercore.shared.utils.sealeds.Response
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

class GetStorageFileUseCaseImplTest {
/*

    private var repository: StorageFileRepository = mockk()
    private var permissionService: PermissionService = mockk()
    private var validatorService: ValidatorService = mockk()
    private var mapper: StorageFileMapper = mockk()
    private lateinit var useCase: GetStorageFileUseCase

    private val user = TestUserDataProvider.getBaseEntity()
    private val id = "id"
    private val settings = QuerySettings(Field.PARENT_ID, QueryType.WHERE_EQUALS, id)

    private val storageFile = TestStorageFileDataProvider.getBaseEntity()
    private val dto = TestStorageFileDataProvider.getBaseDto()

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = GetStorageFileUseCaseImpl(repository, permissionService, validatorService, mapper)
    }

    @Test
    fun `entity search should retrieve the entity when it's found and has permission`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_STORAGE_FILE
            )
        } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Success(dto))
        every { validatorService.validateDto(dto) } returns Unit
        every { mapper.toEntity(dto) } returns storageFile

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Success && result.data == storageFile)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)
            repository.fetchById(id)
            validatorService.validateDto(dto)
            mapper.toEntity(dto)
        }
    }

    @Test
    fun `entity search should return an error when the user does not have auth`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_STORAGE_FILE
            )
        } returns false

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        coVerify {
            permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)
        }
    }

    @Test
    fun `entity search should return an error when the repository returns an error`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_STORAGE_FILE
            )
        } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)
            repository.fetchById(id)
        }
    }

    @Test
    fun `entity search should return empty when the repository returns empty`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_STORAGE_FILE
            )
        } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Empty)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)
            repository.fetchById(id)
        }
    }

    @Test
    fun `entity search should return error when any error in flow occurs`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_STORAGE_FILE
            )
        } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Success(dto))
        every { validatorService.validateDto(dto) } returns Unit
        every { mapper.toEntity(dto) } throws NullPointerException()

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)
            repository.fetchById(id)
            validatorService.validateDto(dto)
            mapper.toEntity(dto)
        }
    }

    //--

    @Test
    fun `list search should retrieve the entity when it's found and has permission`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)
        } returns true
        coEvery { repository.fetchByQuery(settings) } returns flowOf(Response.Success(listOf(dto)))
        every { validatorService.validateDto(dto) } returns Unit
        every { mapper.toEntity(dto) } returns storageFile

        // Call
        val result = useCase.execute(user, settings).single()

        // Assertions
        assertTrue(result is Response.Success && result.data == listOf(storageFile))
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)
            repository.fetchByQuery(settings)
            validatorService.validateDto(dto)
            mapper.toEntity(dto)
        }
    }

    @Test
    fun `list search should return an error when the user does not have auth`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)
        } returns false

        // Call
        val result = useCase.execute(user, settings).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        coVerify {
            permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)
        }
    }

    @Test
    fun `list search should return an error when the repository returns an error`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)
        } returns true
        coEvery {
            repository.fetchByQuery(settings)
        } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, settings).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)
            repository.fetchByQuery(settings)
        }
    }

    @Test
    fun `list search should return empty when the repository returns empty`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)
        } returns true
        coEvery { repository.fetchByQuery(settings) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, settings).single()

        // Assertions
        assertTrue(result is Response.Empty)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)
            repository.fetchByQuery(settings)
        }
    }

    @Test
    fun `list search should return error when any error in flow occurs`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)
        } returns true
        coEvery { repository.fetchByQuery(settings) } returns flowOf(Response.Success(listOf(dto)))
        every { validatorService.validateDto(dto) } returns Unit
        every { mapper.toEntity(dto) } throws NullPointerException()

        // Call
        val result = useCase.execute(user, settings).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_STORAGE_FILE)
            repository.fetchByQuery(settings)
            validatorService.validateDto(dto)
            mapper.toEntity(dto)
        }
    }
*/

}