package com.example.truckercore.unit.modules.fleet.shared.module.licensing.use_cases

import com.example.truckercore._test_data_provider.TestLicensingDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.shared.module.licensing.mapper.LicensingMapper
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations.CreateLicensingUseCaseImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.CreateLicensingUseCase
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

class CreateLicensingUseCaseImplTest {

    private val repository: LicensingRepository = mockk()
    private val validatorService: ValidatorService = mockk()
    private val permissionService: PermissionService = mockk()
    private val mapper: LicensingMapper = mockk()
    private lateinit var useCase: CreateLicensingUseCase
    private val user = TestUserDataProvider.getBaseEntity()
    private val licensing = TestLicensingDataProvider.getBaseEntity()
    private val dto = TestLicensingDataProvider.getBaseDto()
    private val id = "id"

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase =
            CreateLicensingUseCaseImpl(repository, validatorService, mapper, permissionService, Permission.CREATE_LICENSING)
    }

    @Test
    fun `should create entity when have permission and data is valid`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.CREATE_LICENSING) } returns true
        every { validatorService.validateForCreation(licensing) } returns Unit
        every { mapper.toDto(licensing) } returns dto
        coEvery { repository.create(dto) } returns flowOf(Response.Success(id))

        // Call
        val result = useCase.execute(user, licensing).single()

        // Assertions
        assertTrue(result is Response.Success && result.data == id)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.CREATE_LICENSING)
            validatorService.validateForCreation(licensing)
            mapper.toDto(licensing)
            repository.create(dto)
        }
    }

    @Test
    fun `should return error when user does not have permission for creation`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.CREATE_LICENSING
            )
        } returns false

        // Call
        val result = useCase.execute(user, licensing).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        verify {
            permissionService.canPerformAction(user, Permission.CREATE_LICENSING)
        }
    }

    @Test
    fun `should return error when any unexpected exception occurs`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.CREATE_LICENSING
            )
        } throws NullPointerException()

        // Call
        val result = useCase.execute(user, licensing).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        verify {
            permissionService.canPerformAction(user, Permission.CREATE_LICENSING)
        }
    }

    @Test
    fun `should return error when database returns an error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.CREATE_LICENSING) } returns true
        every { validatorService.validateForCreation(licensing) } returns Unit
        every { mapper.toDto(licensing) } returns dto
        coEvery { repository.create(dto) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, licensing).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.CREATE_LICENSING)
            validatorService.validateForCreation(licensing)
            mapper.toDto(licensing)
            repository.create(dto)
        }
    }

}