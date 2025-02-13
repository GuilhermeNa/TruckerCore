/*
package com.example.truckercore.unit.modules.employee.driver.use_cases

import android.content.pm.PermissionGroupInfo
import com.example.truckercore._test_data_provider.TestDriverDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.employee.driver.mapper.DriverMapper
import com.example.truckercore.modules.employee.driver.repository.DriverRepository
import com.example.truckercore.modules.employee.driver.use_cases.implementations.GetDriverUseCaseImpl
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.GetDriverUseCase
import com.example.truckercore.modules.user.entity.User
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

class GetDriverUseCaseImplTestTest {

    private lateinit var repository: DriverRepository
    private lateinit var permissionService: PermissionService
    private lateinit var validatorService: ValidatorService
    private lateinit var mapper: DriverMapper
    private lateinit var useCase: GetDriverUseCase
    private lateinit var user: User
    private val driver = TestDriverDataProvider.getBaseEntity()
    private val dto = TestDriverDataProvider.getBaseDto()
    private val id = "id"

    @BeforeEach
    fun setup() {
        mockStaticLog()
        repository = mockk(relaxed = true)
        permissionService = mockk(relaxed = true)
        validatorService = mockk(relaxed = true)
        mapper = mockk(relaxed = true)
        useCase = GetDriverUseCaseImpl(repository, validatorService, mapper, permissionService, Permission.VIEW_DRIVER)
        user = mockk(relaxed = true)
    }

    @Test
    fun `should return data correctly when its found`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_DRIVER) } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Success(dto))
        every { validatorService.validateDto(dto) } returns Unit
        every { mapper.toEntity(dto) } returns driver

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Success)

    }

    @Test
    fun `should return error when user does not have authorization`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_DRIVER) } returns false

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
    }

    @Test
    fun `should return empty when nothing is found in database`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_DRIVER) } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Empty)
    }

    @Test
    fun `should return error when there is any unknown error`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_DRIVER) } returns true
        coEvery { repository.fetchById(id) } throws Exception()

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error)
    }

}*/
