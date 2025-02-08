package com.example.truckercore.unit.modules.employee.admin.use_cases

import com.example.truckercore._test_data_provider.TestAdminDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.employee.admin.mapper.AdminMapper
import com.example.truckercore.modules.employee.admin.repository.AdminRepository
import com.example.truckercore.modules.employee.admin.use_cases.implementations.CreateAdminUseCaseImpl
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.CreateAdminUseCase
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

class CreateAdminUseCaseImplTest {

    private val repository: AdminRepository = mockk()
    private val validatorService: ValidatorService = mockk()
    private val permissionService: PermissionService = mockk()
    private val mapper: AdminMapper = mockk()
    private lateinit var useCase: CreateAdminUseCase
    private val user = TestUserDataProvider.getBaseEntity()
    private val admin = TestAdminDataProvider.getBaseEntity()
    private val dto = TestAdminDataProvider.getBaseDto()
    private val id = "id"

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = CreateAdminUseCaseImpl(
            repository,
            validatorService,
            mapper,
            permissionService,
            Permission.CREATE_ADMIN
        )
    }

    @Test
    fun `should create entity when have permission and data is valid`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.CREATE_ADMIN) } returns true
        every { validatorService.validateForCreation(admin) } returns Unit
        every { mapper.toDto(admin) } returns dto
        coEvery { repository.create(dto) } returns flowOf(Response.Success(id))

        // Call
        val result = useCase.execute(user, admin).single()

        // Assertions
        assertTrue(result is Response.Success && result.data == id)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.CREATE_ADMIN)
            validatorService.validateForCreation(admin)
            mapper.toDto(admin)
            repository.create(dto)
        }
    }

    @Test
    fun `should return error when user does not have permission for creation`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.CREATE_ADMIN) } returns false

        // Call
        val result = useCase.execute(user, admin).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        verify {
            permissionService.canPerformAction(user, Permission.CREATE_ADMIN)
        }
    }

    @Test
    fun `should return error when any unexpected exception occurs`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(user, Permission.CREATE_ADMIN)
        } throws NullPointerException()

        // Call
        val result = useCase.execute(user, admin).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        verify {
            permissionService.canPerformAction(user, Permission.CREATE_ADMIN)
        }
    }

    @Test
    fun `should return error when database returns an error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.CREATE_ADMIN) } returns true
        every { validatorService.validateForCreation(admin) } returns Unit
        every { mapper.toDto(admin) } returns dto
        coEvery { repository.create(dto) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, admin).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.CREATE_ADMIN)
            validatorService.validateForCreation(admin)
            mapper.toDto(admin)
            repository.create(dto)
        }
    }

}