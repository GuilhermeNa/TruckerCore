package com.example.truckercore.unit.model.modules.fleet.truck.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.fleet.truck.dto.TruckDto
import com.example.truckercore.model.modules.fleet.truck.entity.Truck
import com.example.truckercore.model.modules.fleet.truck.mapper.TruckMapper
import com.example.truckercore.model.modules.fleet.truck.repository.TruckRepository
import com.example.truckercore.model.modules.fleet.truck.use_cases.implementations.UpdateTruckUseCaseImpl
import com.example.truckercore.model.modules.fleet.truck.use_cases.interfaces.CheckTruckExistenceUseCase
import com.example.truckercore.model.modules.fleet.truck.use_cases.interfaces.UpdateTruckUseCase
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.errors.ObjectNotFoundException
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
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class UpdateTruckUseCaseImplTest : KoinTest {

    private val permissionService: PermissionService by inject()
    private val repository: TruckRepository by inject()
    private val checkExistence: CheckTruckExistenceUseCase by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: TruckMapper by inject()
    private val useCase: UpdateTruckUseCase by inject()

    private val id = "truckId"
    private val truck: Truck = mockk()
    private val dto: TruckDto = mockk()
    private val user: User = mockk()

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
                        single<CheckTruckExistenceUseCase> { mockk() }
                        single<ValidatorService> { mockk() }
                        single<TruckMapper> { mockk() }
                        single<UpdateTruckUseCase> {
                            UpdateTruckUseCaseImpl(
                                Permission.UPDATE_TRUCK,
                                get(), get(), get(), get(), get()
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
    fun `execute() should return success when truck exists and is updated successfully`() =
        runTest {
            // Arrange
            every { truck.id } returns id
            every { checkExistence.execute(any(), any()) } returns flowOf(Response.Success(Unit))
            every { permissionService.canPerformAction(any(), any()) } returns true
            every { validatorService.validateEntity(any()) } returns Unit
            every { mapper.toDto(any()) } returns dto
            every { repository.update(any()) } returns flowOf(Response.Success(Unit))

            // Call
            val result = useCase.execute(user, truck).single()

            // Assertions
            assertTrue(result is Response.Success)
            verifyOrder {
                truck.id
                checkExistence.execute(user, id)
                permissionService.canPerformAction(user, Permission.UPDATE_TRUCK)
                validatorService.validateEntity(truck)
                mapper.toDto(truck)
                repository.update(dto)
            }
        }

    @Test
    fun `execute() should throw NullPointerException when the Truck id is null`() =
        runTest {
            // Arrange
            every { truck.id } returns null

            // Call
            assertThrows<NullPointerException> {
                useCase.execute(user, truck).single()
            }

            // Assertions
            verify {
                truck.id
            }
        }

    @Test
    fun `execute() should throw ObjectNotFoundException when checkExistence returns Empty`() =
        runTest {
            // Arrange
            every { truck.id } returns id
            every { checkExistence.execute(any(), any()) } returns flowOf(Response.Empty)

            // Call
            assertThrows<ObjectNotFoundException> {
                useCase.execute(user, truck).single()
            }

            // Assertions
            verifyOrder {
                truck.id
                checkExistence.execute(user, id)
            }
        }

}
