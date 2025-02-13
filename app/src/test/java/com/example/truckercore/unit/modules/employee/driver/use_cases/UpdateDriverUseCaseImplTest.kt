/*
package com.example.truckercore.unit.modules.employee.driver.use_cases

import com.example.truckercore._test_data_provider.TestDriverDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.employee.driver.mapper.DriverMapper
import com.example.truckercore.modules.employee.driver.repository.DriverRepository
import com.example.truckercore.modules.employee.driver.use_cases.implementations.UpdateDriverUseCaseImpl
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.CheckDriverExistenceUseCase
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.UpdateDriverUseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UpdateDriverUseCaseImplTest {

    private lateinit var repository: DriverRepository
    private lateinit var  checkExistence: CheckDriverExistenceUseCase
    private lateinit var  permissionService: PermissionService
    private lateinit var  validatorService: ValidatorService
    private lateinit var  mapper: DriverMapper
    private lateinit var useCase: UpdateDriverUseCase
    private val user = TestUserDataProvider.getBaseEntity()
    private val driver = TestDriverDataProvider.getBaseEntity()
    private val dto = TestDriverDataProvider.getBaseDto()

    @BeforeEach
    fun setup() {
        mockStaticLog()
        permissionService = mockk(relaxed = true)
        checkExistence = mockk(relaxed = true)
        validatorService = mockk(relaxed = true)
        mapper = mockk(relaxed = true)
        repository = mockk(relaxed = true)
        useCase = UpdateDriverUseCaseImpl(
            repository,
            checkExistence,
            validatorService,
            mapper,
            permissionService,
            Permission.UPDATE_DRIVER
        )
    }

    @Test
    fun `should return success when driver is updated successfully`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_DRIVER) } returns true
        coEvery { checkExistence.execute(user, driver.id!!) } returns flowOf(Response.Success(Unit))
        every { validatorService.validateEntity(driver) } returns Unit
        every { mapper.toDto(driver) } returns dto
        coEvery { repository.update(dto) } returns flowOf(Response.Success(Unit))

        // Call
        val result = useCase.execute(user, driver).single()

        // Assertions
        assertTrue(result is Response.Success)
    }

    @Test
    fun `should return error when user does not have authorization`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_DRIVER) } returns false

        // Call
        val result = useCase.execute(user, driver).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
    }

    @Test
    fun `should return error when driver does not exist`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_DRIVER) } returns true
        coEvery { checkExistence.execute(user, driver.id!!) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, driver).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is ObjectNotFoundException)
    }

    @Test
    fun `should return error when there is a failure in the existence check`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_DRIVER) } returns true
        coEvery {
            checkExistence.execute(user, "driverId")
        } returns flowOf(Response.Error(Exception("Error occurred")))

        // Call
        val result = useCase.execute(user, driver).single()

        // Assertions
        assertTrue(result is Response.Error)
    }

    @Test
    fun `should return error when update fails due to unexpected error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_DRIVER) } returns true
        coEvery {
            checkExistence.execute(user, "driverId")
        } returns flowOf(Response.Success(mockk()))
        coEvery { repository.update(any()) } throws Exception("Unexpected error")

        // Call
        val result = useCase.execute(user, driver).single()

        // Assertions
        assertTrue(result is Response.Error)
    }

}*/
