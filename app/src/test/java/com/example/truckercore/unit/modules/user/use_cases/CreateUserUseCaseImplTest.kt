package com.example.truckercore.unit.modules.user.use_cases

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.implementations.CreateUserUseCaseImpl
import com.example.truckercore.modules.user.use_cases.interfaces.CreateUserUseCase
import com.example.truckercore.shared.enums.PersistenceStatus
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreateUserUseCaseImplTest {

    private val repository: UserRepository = mockk()
    private val validatorService: ValidatorService = mockk()
    private val mapper: UserMapper = mockk()
    private val permissionService: PermissionService = mockk()
    private lateinit var useCase: CreateUserUseCase

    private val user = TestUserDataProvider.getBaseEntity()
    private val newUser = TestUserDataProvider.getBaseEntity()
        .copy(id = null, persistenceStatus = PersistenceStatus.PENDING)
    private val dto = TestUserDataProvider.getBaseDto()
        .copy(id = null, persistenceStatus = PersistenceStatus.PENDING.name)

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = CreateUserUseCaseImpl(repository, validatorService, permissionService, mapper)
    }

    @Test
    fun `execute() should successfully create user and emit response`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.CREATE_USER) } returns true
        every { validatorService.validateForCreation(newUser) } returns Unit
        every { mapper.toDto(newUser) } returns dto
        coEvery { repository.create(any()) } returns flowOf(Response.Success("userId"))

        // Act
        val result = useCase.execute(user, newUser).first()

        // Assert
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.CREATE_USER)
            validatorService.validateForCreation(newUser)
            mapper.toDto(newUser)
            repository.create(dto)
        }

        assertTrue(result is Response.Success && result.data == "userId")
    }

    @Test
    fun `execute() should handle unexpected error from repository`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.CREATE_USER) } returns true
        coEvery { validatorService.validateForCreation(newUser) } returns Unit
        coEvery { mapper.toDto(newUser) } returns dto
        coEvery { repository.create(any()) } returns flowOf(Response.Error(NullPointerException()))

        // Act
        val result = useCase.execute(user, newUser).first()

        // Assert
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.CREATE_USER)
            validatorService.validateForCreation(newUser) // 1st call
            mapper.toDto(newUser) // 2nd call
            repository.create(dto) // 3rd call
        }

        assertTrue(result is Response.Error)
    }

    @Test
    fun `execute() should handle empty response from repository`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.CREATE_USER) } returns true
        every { validatorService.validateForCreation(newUser) } returns Unit
        every { mapper.toDto(newUser) } returns dto
        coEvery { repository.create(any()) } returns flowOf(Response.Empty)

        // Act
        val result = useCase.execute(user, newUser).first()

        // Assert
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.CREATE_USER)
            validatorService.validateForCreation(newUser) // 1st call
            mapper.toDto(newUser) // 2nd call
            repository.create(dto) // 3rd call
        }

        assertTrue(result is Response.Empty)
    }

    @Test
    fun `execute() should handle validation error during creation`() = runTest {
        // Arrange
        val validationException = IllegalArgumentException("Validation failed")
        every { permissionService.canPerformAction(user, Permission.CREATE_USER) } returns true
        coEvery { validatorService.validateForCreation(newUser) } throws validationException

        // Act
        val result = useCase.execute(user, newUser).first()

        // Assert
        verifyOrder {
            permissionService.canPerformAction(user, Permission.CREATE_USER)
            validatorService.validateForCreation(newUser)
        }

        assertTrue(result is Response.Error && result.exception is IllegalArgumentException)
    }

    @Test
    fun `execute() should handle unexpected error during mapping to DTO`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.CREATE_USER) } returns true
        every { validatorService.validateForCreation(newUser) } returns Unit
        every { mapper.toDto(newUser) } throws NullPointerException()

        // Act
        val result = useCase.execute(user, newUser).first()

        // Assert
        verifyOrder {
            permissionService.canPerformAction(user, Permission.CREATE_USER)
            validatorService.validateForCreation(newUser)
            mapper.toDto(newUser)
        }

        assertTrue(result is Response.Error && result.exception is NullPointerException)
    }

    @Test
    fun `execute() should handle unauthorized permission`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.CREATE_USER) } returns false

        // Act
        val result = useCase.execute(user, newUser).first()

        // Assert
        verify { permissionService.canPerformAction(user, Permission.CREATE_USER) }

        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
    }

}