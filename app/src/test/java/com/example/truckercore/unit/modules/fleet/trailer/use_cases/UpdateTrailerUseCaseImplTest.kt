package com.example.truckercore.unit.modules.fleet.trailer.use_cases

import com.example.truckercore._test_data_provider.TestTrailerDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.trailer.mapper.TrailerMapper
import com.example.truckercore.modules.fleet.trailer.repository.TrailerRepository
import com.example.truckercore.modules.fleet.trailer.use_cases.implementations.UpdateTrailerUseCaseImpl
import com.example.truckercore.modules.fleet.trailer.use_cases.interfaces.CheckTrailerExistenceUseCase
import com.example.truckercore.modules.fleet.trailer.use_cases.interfaces.UpdateTrailerUseCase
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

class UpdateTrailerUseCaseImplTest {

    private val repository: TrailerRepository = mockk()
    private val checkExistence: CheckTrailerExistenceUseCase = mockk()
    private val permissionService: PermissionService = mockk()
    private val validatorService: ValidatorService = mockk()
    private val mapper: TrailerMapper = mockk()
    private lateinit var useCase: UpdateTrailerUseCase

    private val user = TestUserDataProvider.getBaseEntity()
    private val trailer = TestTrailerDataProvider.getBaseEntity()
    private val dto = TestTrailerDataProvider.getBaseDto()

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = UpdateTrailerUseCaseImpl(
            repository,
            checkExistence,
            validatorService,
            mapper,
            permissionService,
            Permission.UPDATE_TRAILER
        )
    }

    @Test
    fun `should update entity when have permission and data exists`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_TRAILER) } returns true
        coEvery {
            checkExistence.execute(
                user,
                trailer.id!!
            )
        } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(trailer) } returns Unit
        every { mapper.toDto(trailer) } returns dto
        coEvery { repository.update(dto) } returns flowOf(Response.Success(Unit))

        // Call
        val result = useCase.execute(user, trailer).single()

        // Assertions
        assertTrue(result is Response.Success)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_TRAILER)
            checkExistence.execute(user, trailer.id!!)
            validatorService.validateEntity(trailer)
            mapper.toDto(trailer)
            repository.update(dto)
        }
    }

    @Test
    fun `should return error when user does not have permission for update`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_TRAILER) } returns false

        // Call
        val result = useCase.execute(user, trailer).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_TRAILER)
        }
    }

    @Test
    fun `should return error when trailer checkage returns error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_TRAILER) } returns true
        coEvery { checkExistence.execute(user, trailer.id!!) } returns flowOf(
            Response.Error(NullPointerException())
        )

        // Call
        val result = useCase.execute(user, trailer).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_TRAILER)
            checkExistence.execute(user, trailer.id!!)
        }
    }

    @Test
    fun `should return error when trailer existence check returns empty`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_TRAILER) } returns true
        coEvery { checkExistence.execute(user, trailer.id!!) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, trailer).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is ObjectNotFoundException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_TRAILER)
            checkExistence.execute(user, trailer.id!!)
        }
    }

    @Test
    fun `should return error when repository returns an error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_TRAILER) } returns true
        coEvery {
            checkExistence.execute(
                user,
                trailer.id!!
            )
        } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(trailer) } returns Unit
        every { mapper.toDto(trailer) } returns dto
        coEvery { repository.update(dto) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, trailer).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_TRAILER)
            checkExistence.execute(user, trailer.id!!)
            validatorService.validateEntity(trailer)
            mapper.toDto(trailer)
            repository.update(dto)
        }
    }

    @Test
    fun `should return empty when repository returns an empty`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_TRAILER) } returns true
        coEvery {
            checkExistence.execute(
                user,
                trailer.id!!
            )
        } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(trailer) } returns Unit
        every { mapper.toDto(trailer) } returns dto
        coEvery { repository.update(dto) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, trailer).single()

        // Assertions
        assertTrue(result is Response.Empty)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_TRAILER)
            checkExistence.execute(user, trailer.id!!)
            validatorService.validateEntity(trailer)
            mapper.toDto(trailer)
            repository.update(dto)
        }
    }

    @Test
    fun `should return error when any error in flow occurs`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_TRAILER) } returns true
        coEvery {
            checkExistence.execute(
                user,
                trailer.id!!
            )
        } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(trailer) } returns Unit
        every { mapper.toDto(trailer) } throws NullPointerException()

        // Call
        val result = useCase.execute(user, trailer).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_TRAILER)
            checkExistence.execute(user, trailer.id!!)
            validatorService.validateEntity(trailer)
            mapper.toDto(trailer)
        }
    }

}