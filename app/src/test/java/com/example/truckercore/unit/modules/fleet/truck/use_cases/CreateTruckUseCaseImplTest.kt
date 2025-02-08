package com.example.truckercore.unit.modules.fleet.truck.use_cases

import com.example.truckercore._test_data_provider.TestTruckDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.truck.mapper.TruckMapper
import com.example.truckercore.modules.fleet.truck.repository.TruckRepository
import com.example.truckercore.modules.fleet.truck.use_cases.implementations.CreateTruckUseCaseImpl
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.CreateTruckUseCase
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

class CreateTruckUseCaseImplTest {

    private val repository: TruckRepository = mockk()
    private val validatorService: ValidatorService = mockk()
    private val permissionService: PermissionService = mockk()
    private val mapper: TruckMapper = mockk()
    private lateinit var useCase: CreateTruckUseCase
    private val user = TestUserDataProvider.getBaseEntity()
    private val truck = TestTruckDataProvider.getBaseEntity()
    private val dto = TestTruckDataProvider.getBaseDto()
    private val id = "id"

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = CreateTruckUseCaseImpl(repository, validatorService, permissionService, mapper, Permission.CREATE_TRUCK)
    }

    @Test
    fun `should create truck when user has permission and data is valid`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.CREATE_TRUCK) } returns true
        every { validatorService.validateForCreation(truck) } returns Unit
        every { mapper.toDto(truck) } returns dto
        coEvery { repository.create(dto) } returns flowOf(Response.Success(id))

        // Call
        val result = useCase.execute(user, truck).single()

        // Assertions
        assertTrue(result is Response.Success && result.data == id)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.CREATE_TRUCK)
            validatorService.validateForCreation(truck)
            mapper.toDto(truck)
            repository.create(dto)
        }
    }

    @Test
    fun `should return error when user does not have permission for creation`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.CREATE_TRUCK) } returns false

        // Call
        val result = useCase.execute(user, truck).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        verify {
            permissionService.canPerformAction(user, Permission.CREATE_TRUCK)
        }
    }

    @Test
    fun `should return error when any unexpected exception occurs`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(user, Permission.CREATE_TRUCK)
        } throws NullPointerException()

        // Call
        val result = useCase.execute(user, truck).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        verify {
            permissionService.canPerformAction(user, Permission.CREATE_TRUCK)
        }
    }

    @Test
    fun `should return error when database returns an error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.CREATE_TRUCK) } returns true
        every { validatorService.validateForCreation(truck) } returns Unit
        every { mapper.toDto(truck) } returns dto
        coEvery { repository.create(dto) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, truck).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.CREATE_TRUCK)
            validatorService.validateForCreation(truck)
            mapper.toDto(truck)
            repository.create(dto)
        }
    }

}