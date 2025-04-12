package com.example.truckercore.unit.model.modules.person.employee.driver.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.person.employee.driver.dto.DriverDto
import com.example.truckercore.model.modules.person.employee.driver.entity.Driver
import com.example.truckercore.model.modules.person.employee.driver.mapper.DriverMapper
import com.example.truckercore.model.modules.person.employee.driver.repository.DriverRepository
import com.example.truckercore.model.modules.person.employee.driver.use_cases.implementations.UpdateDriverUseCaseImpl
import com.example.truckercore.model.modules.person.employee.driver.use_cases.interfaces.CheckDriverExistenceUseCase
import com.example.truckercore.model.modules.person.employee.driver.use_cases.interfaces.UpdateDriverUseCase
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
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.Test

class UpdateDriverUseCaseImplTest : KoinTest {

    private val permissionService: PermissionService by inject()
    private val repository: DriverRepository by inject()
    private val checkExistence: CheckDriverExistenceUseCase by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: DriverMapper by inject()
    private val useCase: UpdateDriverUseCase by inject()

    private val id = "driverId"
    private val driver: Driver = mockk()
    private val dto: DriverDto = mockk()
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
                        single<DriverRepository> { mockk() }
                        single<CheckDriverExistenceUseCase> { mockk() }
                        single<ValidatorService> { mockk() }
                        single<DriverMapper> { mockk() }
                        single<UpdateDriverUseCase> {
                            UpdateDriverUseCaseImpl(
                                Permission.UPDATE_DRIVER,
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
    fun `execute() should return success when driver exists and is updated successfully`() =
        runTest {
            // Arrange
            every { driver.id } returns id
            every { checkExistence.execute(any(), any()) } returns flowOf(AppResponse.Success(Unit))
            every { permissionService.canPerformAction(any(), any()) } returns true
            every { validatorService.validateEntity(any()) } returns Unit
            every { mapper.toDto(any()) } returns dto
            every { repository.update(any()) } returns flowOf(AppResponse.Success(Unit))

            // Call
            val result = useCase.execute(user, driver).single()

            // Assertions
            assertTrue(result is AppResponse.Success)
            verifyOrder {
                driver.id
                checkExistence.execute(user, id)
                permissionService.canPerformAction(user, Permission.UPDATE_DRIVER)
                validatorService.validateEntity(driver)
                mapper.toDto(driver)
                repository.update(dto)
            }
        }

    @Test
    fun `execute() should throw NullPointerException when the Driver id is null`() =
        runTest {
            // Arrange
            every { driver.id } returns null

            // Call
            assertThrows<NullPointerException> {
                useCase.execute(user, driver).single()
            }

            // Assertions
            verify {
                driver.id
            }
        }

    @Test
    fun `execute() should throw ObjectNotFoundException when checkExistence returns Empty`() =
        runTest {
            // Arrange
            every { driver.id } returns id
            every { checkExistence.execute(any(), any()) } returns flowOf(AppResponse.Empty)

            // Call
            assertThrows<ObjectNotFoundException> {
                useCase.execute(user, driver).single()
            }

            // Assertions
            verifyOrder {
                driver.id
                checkExistence.execute(user, id)
            }
        }

}
