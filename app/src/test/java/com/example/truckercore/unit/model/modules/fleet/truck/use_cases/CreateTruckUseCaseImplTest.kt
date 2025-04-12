package com.example.truckercore.unit.model.modules.fleet.truck.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.fleet.truck.dto.TruckDto
import com.example.truckercore.model.modules.fleet.truck.entity.Truck
import com.example.truckercore.model.modules.fleet.truck.mapper.TruckMapper
import com.example.truckercore.model.modules.fleet.truck.repository.TruckRepository
import com.example.truckercore.model.modules.fleet.truck.use_cases.implementations.CreateTruckUseCaseImpl
import com.example.truckercore.model.modules.fleet.truck.use_cases.interfaces.CreateTruckUseCase
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class CreateTruckUseCaseImplTest : KoinTest {

    private val requirePermission: Permission = Permission.CREATE_TRUCK
    private val permissionService: PermissionService by inject()
    private val repository: TruckRepository by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: TruckMapper by inject()
    private val useCase: CreateTruckUseCase by inject()

    private val user = mockk<User>()
    private val truck = mockk<Truck>()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<PermissionService> { mockk() }
                        single<TruckRepository> { mockk() }
                        single<ValidatorService> { mockk() }
                        single<TruckMapper> { mockk() }
                        single<CreateTruckUseCase> {
                            CreateTruckUseCaseImpl(
                                Permission.CREATE_TRUCK,
                                get(), get(), get(), get()
                            )
                        }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()
    }

    @Test
    fun `execute() should return success when truck is correctly created`() = runTest {
        // Arrange
        val dto = mockk<TruckDto>()
        val id = "newTruckObjectId"
        every { permissionService.canPerformAction(any(), any()) } returns true
        every { validatorService.validateForCreation(any()) } returns Unit
        every { mapper.toDto(any()) } returns dto
        every { repository.create(any()) } returns flowOf(Response.Success(id))

        // Call
        val result = useCase.execute(user, truck).single()

        // Assertions
        assertEquals(id, (result as Response.Success).data)
        verifyOrder {
            permissionService.canPerformAction(user, requirePermission)
            validatorService.validateForCreation(truck)
            mapper.toDto(truck)
            repository.create(dto)
        }
    }

    @Test
    fun `execute() should throw UnauthorizedAccessException when user has no auth`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(any(), any()) } returns false

        // Call
        assertThrows<UnauthorizedAccessException> {
            useCase.execute(user, truck).single()
        }

        // Assertions
        verify {
            permissionService.canPerformAction(user, requirePermission)
        }
    }

}
