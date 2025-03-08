package com.example.truckercore.unit.model.modules.person.employee.driver.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.person.employee.driver.dto.DriverDto
import com.example.truckercore.model.modules.person.employee.driver.entity.Driver
import com.example.truckercore.model.modules.person.employee.driver.mapper.DriverMapper
import com.example.truckercore.model.modules.person.employee.driver.repository.DriverRepository
import com.example.truckercore.model.modules.person.employee.driver.use_cases.implementations.CreateDriverUseCaseImpl
import com.example.truckercore.model.modules.person.employee.driver.use_cases.interfaces.CreateDriverUseCase
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.services.ValidatorService
import com.example.truckercore.model.shared.utils.sealeds.Response
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
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class CreateDriverUseCaseImplTest : KoinTest {

    private val requirePermission: Permission = Permission.CREATE_DRIVER
    private val permissionService: PermissionService by inject()
    private val repository: DriverRepository by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: DriverMapper by inject()
    private val useCase: CreateDriverUseCase by inject()

    private val user = mockk<User>()
    private val driver = mockk<Driver>()

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
                        single<ValidatorService> { mockk() }
                        single<DriverMapper> { mockk() }
                        single<CreateDriverUseCase> {
                            CreateDriverUseCaseImpl(
                                Permission.CREATE_DRIVER,
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
    fun `execute() should return success when driver is correctly created`() = runTest {
        // Arrange
        val dto = mockk<DriverDto>()
        val id = "newDriverObjectId"
        every { permissionService.canPerformAction(any(), any()) } returns true
        every { validatorService.validateForCreation(any()) } returns Unit
        every { mapper.toDto(any()) } returns dto
        every { repository.create(any()) } returns flowOf(Response.Success(id))

        // Call
        val result = useCase.execute(user, driver).single()

        // Assertions
        assertEquals(id, (result as Response.Success).data)
        verifyOrder {
            permissionService.canPerformAction(user, requirePermission)
            validatorService.validateForCreation(driver)
            mapper.toDto(driver)
            repository.create(dto)
        }
    }

    @Test
    fun `execute() should throw UnauthorizedAccessException when user has no auth`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(any(), any()) } returns false

        // Call
        assertThrows<UnauthorizedAccessException> {
            useCase.execute(user, driver).single()
        }

        // Assertions
        verify {
            permissionService.canPerformAction(user, requirePermission)
        }
    }

}
