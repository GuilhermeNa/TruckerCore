/*
package com.example.truckercore.unit.modules.fleet.trailer.use_cases

import com.example.truckercore._test_data_provider.TestTrailerDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.trailer.mapper.TrailerMapper
import com.example.truckercore.modules.fleet.trailer.repository.TrailerRepository
import com.example.truckercore.modules.fleet.trailer.use_cases.implementations.CreateTrailerUseCaseImpl
import com.example.truckercore.modules.fleet.trailer.use_cases.interfaces.CreateTrailerUseCase
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

class CreateTrailerUseCaseImplTest {

    private val repository: TrailerRepository = mockk()
    private val validatorService: ValidatorService = mockk()
    private val permissionService: PermissionService = mockk()
    private val mapper: TrailerMapper = mockk()
    private lateinit var useCase: CreateTrailerUseCase
    private val user = TestUserDataProvider.getBaseEntity()
    private val trailer = TestTrailerDataProvider.getBaseEntity()
    private val dto = TestTrailerDataProvider.getBaseDto()
    private val id = "id"

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = CreateTrailerUseCaseImpl(repository, validatorService, permissionService, mapper, Permission.CREATE_TRAILER)
    }

    @Test
    fun `should create trailer when user has permission and data is valid`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.CREATE_TRAILER) } returns true
        every { validatorService.validateForCreation(trailer) } returns Unit
        every { mapper.toDto(trailer) } returns dto
        coEvery { repository.create(dto) } returns flowOf(Response.Success(id))

        // Call
        val result = useCase.execute(user, trailer).single()

        // Assertions
        assertTrue(result is Response.Success && result.data == id)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.CREATE_TRAILER)
            validatorService.validateForCreation(trailer)
            mapper.toDto(trailer)
            repository.create(dto)
        }
    }

    @Test
    fun `should return error when user does not have permission for creation`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.CREATE_TRAILER) } returns false

        // Call
        val result = useCase.execute(user, trailer).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        verify {
            permissionService.canPerformAction(user, Permission.CREATE_TRAILER)
        }
    }

    @Test
    fun `should return error when any unexpected exception occurs`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(user, Permission.CREATE_TRAILER)
        } throws NullPointerException()

        // Call
        val result = useCase.execute(user, trailer).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        verify {
            permissionService.canPerformAction(user, Permission.CREATE_TRAILER)
        }
    }

    @Test
    fun `should return error when database returns an error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.CREATE_TRAILER) } returns true
        every { validatorService.validateForCreation(trailer) } returns Unit
        every { mapper.toDto(trailer) } returns dto
        coEvery { repository.create(dto) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, trailer).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.CREATE_TRAILER)
            validatorService.validateForCreation(trailer)
            mapper.toDto(trailer)
            repository.create(dto)
        }
    }

}*/
