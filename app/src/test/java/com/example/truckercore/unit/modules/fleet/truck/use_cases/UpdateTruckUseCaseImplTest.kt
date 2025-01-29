package com.example.truckercore.unit.modules.fleet.truck.use_cases

import com.example.truckercore._test_data_provider.TestTruckDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.truck.mapper.TruckMapper
import com.example.truckercore.modules.fleet.truck.repository.TruckRepository
import com.example.truckercore.modules.fleet.truck.use_cases.implementations.UpdateTruckUseCaseImpl
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.CheckTruckExistenceUseCase
import com.example.truckercore.modules.fleet.truck.use_cases.interfaces.UpdateTruckUseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.sealeds.Response
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

class UpdateTruckUseCaseImplTest {

    private val repository: TruckRepository = mockk()
    private val checkExistence: CheckTruckExistenceUseCase = mockk()
    private val permissionService: PermissionService = mockk()
    private val validatorService: ValidatorService = mockk()
    private val mapper: TruckMapper = mockk()
    private lateinit var useCase: UpdateTruckUseCase

    private val user = TestUserDataProvider.getBaseEntity()
    private val truck = TestTruckDataProvider.getBaseEntity()
    private val dto = TestTruckDataProvider.getBaseDto()

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = UpdateTruckUseCaseImpl(
            repository,
            checkExistence,
            permissionService,
            validatorService,
            mapper
        )
    }

    @Test
    fun `should update entity when have permission and data exists`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_TRUCK) } returns true
        coEvery { checkExistence.execute(user, truck.id!!) } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(truck) } returns Unit
        every { mapper.toDto(truck) } returns dto
        coEvery { repository.update(dto) } returns flowOf(Response.Success(Unit))

        // Call
        val result = useCase.execute(user, truck).single()

        // Assertions
        assertTrue(result is Response.Success)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_TRUCK)
            checkExistence.execute(user, truck.id!!)
            validatorService.validateEntity(truck)
            mapper.toDto(truck)
            repository.update(dto)
        }
    }

    @Test
    fun `should return error when user does not have permission for update`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_TRUCK) } returns false

        // Call
        val result = useCase.execute(user, truck).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_TRUCK)
        }
    }

    @Test
    fun `should return error when truck check returns error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_TRUCK) } returns true
        coEvery { checkExistence.execute(user, truck.id!!) } returns flowOf(
            Response.Error(NullPointerException())
        )

        // Call
        val result = useCase.execute(user, truck).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_TRUCK)
            checkExistence.execute(user, truck.id!!)
        }
    }

    @Test
    fun `should return error when truck existence check returns empty`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_TRUCK) } returns true
        coEvery { checkExistence.execute(user, truck.id!!) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, truck).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is ObjectNotFoundException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_TRUCK)
            checkExistence.execute(user, truck.id!!)
        }
    }

    @Test
    fun `should return error when repository returns an error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_TRUCK) } returns true
        coEvery { checkExistence.execute(user, truck.id!!) } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(truck) } returns Unit
        every { mapper.toDto(truck) } returns dto
        coEvery { repository.update(dto) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, truck).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_TRUCK)
            checkExistence.execute(user, truck.id!!)
            validatorService.validateEntity(truck)
            mapper.toDto(truck)
            repository.update(dto)
        }
    }

    @Test
    fun `should return empty when repository returns an empty`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_TRUCK) } returns true
        coEvery { checkExistence.execute(user, truck.id!!) } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(truck) } returns Unit
        every { mapper.toDto(truck) } returns dto
        coEvery { repository.update(dto) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, truck).single()

        // Assertions
        assertTrue(result is Response.Empty)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_TRUCK)
            checkExistence.execute(user, truck.id!!)
            validatorService.validateEntity(truck)
            mapper.toDto(truck)
            repository.update(dto)
        }
    }

    @Test
    fun `should return error when any error in flow occurs`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_TRUCK) } returns true
        coEvery { checkExistence.execute(user, truck.id!!) } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(truck) } returns Unit
        every { mapper.toDto(truck) } throws NullPointerException()

        // Call
        val result = useCase.execute(user, truck).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_TRUCK)
            checkExistence.execute(user, truck.id!!)
            validatorService.validateEntity(truck)
            mapper.toDto(truck)
        }
    }

}