package com.example.truckercore.unit.modules.user.use_cases

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.errors.UserMappingException
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.implementations.GetUserByIdUseCaseImpl
import com.example.truckercore.modules.user.use_cases.interfaces.GetUserByIdUseCase
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import com.google.common.base.Verify.verify
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

class GetUserByIdUseCaseImplTest {

    private lateinit var useCase: GetUserByIdUseCase
    private val repository: UserRepository = mockk()
    private val permissionService: PermissionService = mockk()
    private val validatorService: ValidatorService = mockk()
    private val mapper: UserMapper = mockk()

    private val dto = TestUserDataProvider.getBaseDto()
    private val user = TestUserDataProvider.getBaseEntity()
    private val id = "userId"

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase =
            GetUserByIdUseCaseImpl(repository, permissionService, validatorService, mapper)
    }

    @Test
    fun `should fetch user successfully if user exists and has permission`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_USER) } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Success(dto))
        every { validatorService.validateDto(dto) } returns Unit
        every { mapper.toEntity(any()) } returns user

        // Act
        val result = useCase.execute(user, id).single()

        // Assert
        assertTrue(result is Response.Success)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_USER)
            repository.fetchById(id)
            validatorService.validateDto(dto)
            mapper.toEntity(any())
        }
    }

    @Test
    fun `should handle unauthorized access when user lacks permission`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_USER) } returns false

        // Act
        val result = useCase.execute(user, id).single()

        // Assert
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        verify {
            permissionService.canPerformAction(user, Permission.VIEW_USER)
        }

    }

    @Test
    fun `should handle failure when user is not found`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_USER) } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Error(NullPointerException()))

        // Act
        val result = useCase.execute(user, id).single()

        // Assert
        assertTrue(result is Response.Error)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_USER)
            repository.fetchById(id)
        }
    }

    @Test
    fun `should handle empty response when user is not found in database`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_USER) } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Empty)

        // Act
        val result = useCase.execute(user, id).single()

        // Assert
        assertTrue(result is Response.Empty)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_USER)
            repository.fetchById(id)
        }
    }

    @Test
    fun `should handle unexpected error during execution`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.VIEW_USER) } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Success(dto))
        every { validatorService.validateDto(dto) } returns Unit
        every { mapper.toEntity(any()) } throws NullPointerException()

        // Act
        val result = useCase.execute(user, id).single()

        // Assert
        assertTrue(result is Response.Error)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_USER)
            repository.fetchById(id)
            validatorService.validateDto(dto)
            mapper.toEntity(any())
        }
    }

}