/*
package com.example.truckercore.unit.modules.employee.admin.use_cases

import com.example.truckercore._test_data_provider.TestAdminDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.employee.admin.mapper.AdminMapper
import com.example.truckercore.modules.employee.admin.repository.AdminRepository
import com.example.truckercore.modules.employee.admin.use_cases.implementations.GetAdminUseCaseImpl
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.GetAdminUseCase
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

class GetAdminUseCaseImplTest {

    private lateinit var useCase: GetAdminUseCase
    private var repository: AdminRepository = mockk()
    private var permissionService: PermissionService = mockk()
    private var validatorService: ValidatorService = mockk()
    private var mapper: AdminMapper = mockk()
    private val user = TestUserDataProvider.getBaseEntity()
    private val id = "id"
    private val admin = TestAdminDataProvider.getBaseEntity()
    private val dto = TestAdminDataProvider.getBaseDto()

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = GetAdminUseCaseImpl(repository, permissionService, validatorService, mapper, Permission.VIEW_ADMIN)
    }

    @Test
    fun `should retrieve the entity when it's found and has permission`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_ADMIN) } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Success(dto))
        every { validatorService.validateDto(dto) } returns Unit
        every { mapper.toEntity(dto) } returns admin

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Success && result.data == admin)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_ADMIN)
            repository.fetchById(id)
            validatorService.validateDto(dto)
            mapper.toEntity(dto)
        }
    }

    @Test
    fun `should return an error when the user does not have auth`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_ADMIN) } returns false

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error)
        coVerify {
            permissionService.canPerformAction(user, Permission.VIEW_ADMIN)
        }
    }

    @Test
    fun `should return an error when the repository returns an error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_ADMIN) } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_ADMIN)
            repository.fetchById(id)
        }
    }

    @Test
    fun `should return empty when the repository returns empty`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_ADMIN) } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Empty)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_ADMIN)
            repository.fetchById(id)
        }
    }

    @Test
    fun `should return error when any error in flow occurs`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_ADMIN) } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Success(dto))
        every { validatorService.validateDto(dto) } returns Unit
        every { mapper.toEntity(dto) } throws NullPointerException()

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_ADMIN)
            repository.fetchById(id)
            validatorService.validateDto(dto)
            mapper.toEntity(dto)
        }
    }

}*/
