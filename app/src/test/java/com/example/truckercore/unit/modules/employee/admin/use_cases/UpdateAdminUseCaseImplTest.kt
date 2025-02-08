package com.example.truckercore.unit.modules.employee.admin.use_cases

import com.example.truckercore._test_data_provider.TestAdminDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.employee.admin.mapper.AdminMapper
import com.example.truckercore.modules.employee.admin.repository.AdminRepository
import com.example.truckercore.modules.employee.admin.use_cases.implementations.UpdateAdminUseCaseImpl
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.CheckAdminExistenceUseCase
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.UpdateAdminUseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.utils.sealeds.Response
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

class UpdateAdminUseCaseImplTest {

    private val repository: AdminRepository = mockk()
    private val checkExistence: CheckAdminExistenceUseCase = mockk()
    private val permissionService: PermissionService = mockk()
    private val validatorService: ValidatorService = mockk()
    private val mapper: AdminMapper = mockk()
    private lateinit var useCase: UpdateAdminUseCase

    private val user = TestUserDataProvider.getBaseEntity()
    private val admin = TestAdminDataProvider.getBaseEntity()
    private val dto = TestAdminDataProvider.getBaseDto()

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = UpdateAdminUseCaseImpl(
            repository,
            checkExistence,
            permissionService,
            validatorService,
            mapper,
            Permission.UPDATE_ADMIN
        )
    }

    @Test
    fun `should update entity when have permission and data exists`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_ADMIN) } returns true
        coEvery { checkExistence.execute(user, admin.id!!) } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(admin) } returns Unit
        every { mapper.toDto(admin) } returns dto
        coEvery { repository.update(dto) } returns flowOf(Response.Success(Unit))

        // Call
        val result = useCase.execute(user, admin).single()

        // Assertions
        assertTrue(result is Response.Success)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_ADMIN)
            checkExistence.execute(user, admin.id!!)
            validatorService.validateEntity(admin)
            mapper.toDto(admin)
            repository.update(dto)
        }
    }

    @Test
    fun `should return error when user does not have permission for update`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_ADMIN) } returns false

        // Call
        val result = useCase.execute(user, admin).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_ADMIN)
        }
    }

    @Test
    fun `should return error when admin does not exists`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_ADMIN) } returns true
        coEvery { checkExistence.execute(user, admin.id!!) } returns flowOf(
            Response.Error(
                NullPointerException()
            )
        )

        // Call
        val result = useCase.execute(user, admin).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_ADMIN)
            checkExistence.execute(user, admin.id!!)
        }
    }

    @Test
    fun `should return error when admin existence check returns empty`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_ADMIN) } returns true
        coEvery { checkExistence.execute(user, admin.id!!) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, admin).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is ObjectNotFoundException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_ADMIN)
            checkExistence.execute(user, admin.id!!)
        }
    }

    @Test
    fun `should return error when repository returns an error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_ADMIN) } returns true
        coEvery { checkExistence.execute(user, admin.id!!) } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(admin) } returns Unit
        every { mapper.toDto(admin) } returns dto
        coEvery { repository.update(dto) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, admin).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_ADMIN)
            checkExistence.execute(user, admin.id!!)
            validatorService.validateEntity(admin)
            mapper.toDto(admin)
            repository.update(dto)
        }
    }

    @Test
    fun `should return empty when repository returns an empty`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_ADMIN) } returns true
        coEvery { checkExistence.execute(user, admin.id!!) } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(admin) } returns Unit
        every { mapper.toDto(admin) } returns dto
        coEvery { repository.update(dto) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, admin).single()

        // Assertions
        assertTrue(result is Response.Empty)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_ADMIN)
            checkExistence.execute(user, admin.id!!)
            validatorService.validateEntity(admin)
            mapper.toDto(admin)
            repository.update(dto)
        }
    }

    @Test
    fun `should return error when any error in flow occurs`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_ADMIN) } returns true
        coEvery { checkExistence.execute(user, admin.id!!) } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(admin) } returns Unit
        every { mapper.toDto(admin) } throws NullPointerException()

        // Call
        val result = useCase.execute(user, admin).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_ADMIN)
            checkExistence.execute(user, admin.id!!)
            validatorService.validateEntity(admin)
            mapper.toDto(admin)
        }
    }

}