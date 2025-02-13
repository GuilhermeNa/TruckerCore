/*
package com.example.truckercore.unit.modules.fleet.trailer.use_cases

import com.example.truckercore._test_data_provider.TestTrailerDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.trailer.mapper.TrailerMapper
import com.example.truckercore.modules.fleet.trailer.repository.TrailerRepository
import com.example.truckercore.modules.fleet.trailer.use_cases.implementations.GetTrailerUseCaseImpl
import com.example.truckercore.modules.fleet.trailer.use_cases.interfaces.GetTrailerUseCase
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

class GetTrailerUseCaseImplTest {

    private lateinit var useCase: GetTrailerUseCase
    private var repository: TrailerRepository = mockk()
    private var permissionService: PermissionService = mockk()
    private var validatorService: ValidatorService = mockk()
    private var mapper: TrailerMapper = mockk()
    private val user = TestUserDataProvider.getBaseEntity()
    private val id = "id"
    private val trailer = TestTrailerDataProvider.getBaseEntity()
    private val dto = TestTrailerDataProvider.getBaseDto()

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = GetTrailerUseCaseImpl(repository, validatorService, mapper, permissionService, Permission.VIEW_TRAILER)
    }

    @Test
    fun `should retrieve the trailer when it's found and user has permission`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_TRAILER) } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Success(dto))
        every { validatorService.validateDto(dto) } returns Unit
        every { mapper.toEntity(dto) } returns trailer

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Success && result.data == trailer)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_TRAILER)
            repository.fetchById(id)
            validatorService.validateDto(dto)
            mapper.toEntity(dto)
        }
    }

    @Test
    fun `should return an error when the user does not have permission`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_TRAILER) } returns false

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        coVerify {
            permissionService.canPerformAction(user, Permission.VIEW_TRAILER)
        }
    }

    @Test
    fun `should return an error when the repository returns an error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_TRAILER) } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_TRAILER)
            repository.fetchById(id)
        }
    }

    @Test
    fun `should return empty when the repository returns empty`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_TRAILER) } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Empty)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_TRAILER)
            repository.fetchById(id)
        }
    }

    @Test
    fun `should return error when any error in flow occurs`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_TRAILER) } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Success(dto))
        every { validatorService.validateDto(dto) } returns Unit
        every { mapper.toEntity(dto) } throws NullPointerException()

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_TRAILER)
            repository.fetchById(id)
            validatorService.validateDto(dto)
            mapper.toEntity(dto)
        }
    }

}*/
