package com.example.truckercore.unit.modules.fleet.shared.module.licensing.use_cases

import com.example.truckercore._test_data_provider.TestLicensingDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.shared.module.licensing.mapper.LicensingMapper
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations.UpdateLicensingUseCaseImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.CheckLicensingExistenceUseCase
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.UpdateLicensingUseCase
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

class UpdateLicensingUseCaseImplTest {

    private val repository: LicensingRepository = mockk()
    private val checkExistence: CheckLicensingExistenceUseCase = mockk()
    private val permissionService: PermissionService = mockk()
    private val validatorService: ValidatorService = mockk()
    private val mapper: LicensingMapper = mockk()
    private lateinit var useCase: UpdateLicensingUseCase

    private val user = TestUserDataProvider.getBaseEntity()
    private val licensing = TestLicensingDataProvider.getBaseEntity()
    private val dto = TestLicensingDataProvider.getBaseDto()

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = UpdateLicensingUseCaseImpl(
            repository,
            checkExistence,
            validatorService,
            mapper,
            permissionService,
            Permission.UPDATE_LICENSING
        )
    }

    @Test
    fun `should update licensing when have permission and data exists`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_LICENSING) } returns true
        coEvery { checkExistence.execute(user, licensing.id!!) } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(licensing) } returns Unit
        every { mapper.toDto(licensing) } returns dto
        coEvery { repository.update(dto) } returns flowOf(Response.Success(Unit))

        // Call
        val result = useCase.execute(user, licensing).single()

        // Assertions
        assertTrue(result is Response.Success)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_LICENSING)
            checkExistence.execute(user, licensing.id!!)
            validatorService.validateEntity(licensing)
            mapper.toDto(licensing)
            repository.update(dto)
        }
    }

    @Test
    fun `should return error when user does not have permission for update`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_LICENSING) } returns false

        // Call
        val result = useCase.execute(user, licensing).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_LICENSING)
        }
    }

    @Test
    fun `should return error when licensing existence check returns error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_LICENSING) } returns true
        coEvery { checkExistence.execute(user, licensing.id!!) } returns flowOf(
            Response.Error(NullPointerException())
        )

        // Call
        val result = useCase.execute(user, licensing).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_LICENSING)
            checkExistence.execute(user, licensing.id!!)
        }
    }

    @Test
    fun `should return error when licensing existence check returns empty`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_LICENSING) } returns true
        coEvery { checkExistence.execute(user, licensing.id!!) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, licensing).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is ObjectNotFoundException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_LICENSING)
            checkExistence.execute(user, licensing.id!!)
        }
    }

    @Test
    fun `should return error when repository returns an error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_LICENSING) } returns true
        coEvery { checkExistence.execute(user, licensing.id!!) } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(licensing) } returns Unit
        every { mapper.toDto(licensing) } returns dto
        coEvery { repository.update(dto) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, licensing).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_LICENSING)
            checkExistence.execute(user, licensing.id!!)
            validatorService.validateEntity(licensing)
            mapper.toDto(licensing)
            repository.update(dto)
        }
    }

    @Test
    fun `should return empty when repository returns an empty`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_LICENSING) } returns true
        coEvery { checkExistence.execute(user, licensing.id!!) } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(licensing) } returns Unit
        every { mapper.toDto(licensing) } returns dto
        coEvery { repository.update(dto) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, licensing).single()

        // Assertions
        assertTrue(result is Response.Empty)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_LICENSING)
            checkExistence.execute(user, licensing.id!!)
            validatorService.validateEntity(licensing)
            mapper.toDto(licensing)
            repository.update(dto)
        }
    }

    @Test
    fun `should return error when any error in flow occurs`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_LICENSING) } returns true
        coEvery { checkExistence.execute(user, licensing.id!!) } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(licensing) } returns Unit
        every { mapper.toDto(licensing) } throws NullPointerException()

        // Call
        val result = useCase.execute(user, licensing).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_LICENSING)
            checkExistence.execute(user, licensing.id!!)
            validatorService.validateEntity(licensing)
            mapper.toDto(licensing)
        }
    }

}