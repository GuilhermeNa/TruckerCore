package com.example.truckercore.unit.modules.employee.driver.use_cases

import com.example.truckercore._test_data_provider.TestDriverDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.employee.driver.dto.DriverDto
import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.modules.employee.driver.mapper.DriverMapper
import com.example.truckercore.modules.employee.driver.repository.DriverRepository
import com.example.truckercore.modules.employee.driver.use_cases.implementations.CreateDriverUseCaseImpl
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.CreateDriverUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreateDriverUseCaseImplTest {

    private lateinit var repository: DriverRepository
    private lateinit var validatorService: ValidatorService
    private lateinit var permissionService: PermissionService
    private lateinit var mapper: DriverMapper
    private lateinit var driver: Driver
    private lateinit var dto: DriverDto
    private lateinit var user: User
    private lateinit var useCase: CreateDriverUseCase

    @BeforeEach
    fun setup() {
        mockStaticLog()
        driver = TestDriverDataProvider.getBaseEntity()
        dto = TestDriverDataProvider.getBaseDto()
        user = TestUserDataProvider.getBaseEntity()
        repository = mockk()
        validatorService = mockk()
        permissionService = mockk()
        mapper = mockk()
        useCase = CreateDriverUseCaseImpl(repository, validatorService, mapper, permissionService, Permission.CREATE_DRIVER)
    }

    @Test
    fun `should execute correctly when data correspond the expected`() = runTest {
        // Arrange
        val id = "id"
        every { permissionService.canPerformAction(user, Permission.CREATE_DRIVER) } returns true
        every { validatorService.validateForCreation(driver) } returns Unit
        every { mapper.toDto(driver) } returns dto
        coEvery { repository.create(dto) } returns flowOf(Response.Success(id))

        // Call
        val result = useCase.execute(user, driver).single()

        // Assertions
        assertTrue(result is Response.Success && result.data == id)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.CREATE_DRIVER)
            validatorService.validateForCreation(driver)
            mapper.toDto(driver)
            repository.create(dto)
        }
    }

    @Test
    fun `should return an error when user has no auth`() = runTest {
        // Arrange
        val id = "id"
        every { permissionService.canPerformAction(user, Permission.CREATE_DRIVER) } returns false

        // Call
        val result = useCase.execute(user, driver).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        verify {
            permissionService.canPerformAction(user, Permission.CREATE_DRIVER)
        }
    }

    @Test
    fun `should return error when any unexpected error occurs`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.CREATE_DRIVER) } returns true
        every { validatorService.validateForCreation(driver) } throws Exception()

        // Call
        val result = useCase.execute(user, driver).single()

        // Assertions
        assertTrue(result is Response.Error)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.CREATE_DRIVER)
            validatorService.validateForCreation(driver)
        }
    }

}