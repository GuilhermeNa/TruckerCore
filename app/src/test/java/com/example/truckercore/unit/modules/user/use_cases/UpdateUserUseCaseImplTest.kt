package com.example.truckercore.unit.modules.user.use_cases

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.implementations.UpdateUserUseCaseImpl
import com.example.truckercore.modules.user.use_cases.interfaces.CheckUserExistenceUseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
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

class UpdateUserUseCaseImplTest {

    private lateinit var updateUserUseCase: UpdateUserUseCaseImpl
    private val repository: UserRepository = mockk()
    private val checkExistence: CheckUserExistenceUseCase = mockk()
    private val permissionService: PermissionService = mockk()
    private val validatorService: ValidatorService = mockk()
    private val mapper: UserMapper = mockk()

    private val user = TestUserDataProvider.getBaseEntity()
    private val userToUpdate = TestUserDataProvider.getBaseEntity().copy(id = "toUpdate")
    private val dto =  TestUserDataProvider.getBaseDto().copy(id = "toUpdate")

    @BeforeEach
    fun setup() {
        mockStaticLog()
        updateUserUseCase = UpdateUserUseCaseImpl(repository, checkExistence, permissionService, validatorService, mapper)
    }

    @Test
    fun `should update user successfully if user exists and has permission`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_USER) } returns true
        coEvery { checkExistence.execute(user, userToUpdate.id!!) } returns flowOf(Response.Success(true))
        every { validatorService.validateEntity(userToUpdate) } returns Unit
        every { mapper.toDto(userToUpdate) } returns dto
        coEvery { repository.update(dto) } returns flowOf(Response.Success(Unit))

        // Act
        val result = updateUserUseCase.execute(user, userToUpdate).single()

        // Assert
        assertTrue(result is Response.Success)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_USER)
            checkExistence.execute(user, userToUpdate.id!!)
            validatorService.validateEntity(userToUpdate)
            mapper.toDto(userToUpdate)
            repository.update(dto)
        }
    }

    @Test
    fun `should handle unauthorized access when user lacks permission`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_USER) } returns false

        // Act
        val result = updateUserUseCase.execute(user, userToUpdate).single()

        // Assert
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        verify { permissionService.canPerformAction(user, Permission.UPDATE_USER) }
    }

    @Test
    fun `should handle non-existent user for update`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_USER) } returns true
        coEvery { checkExistence.execute(user, userToUpdate.id!!) } returns flowOf(Response.Success(false))

        // Act
        val result = updateUserUseCase.execute(user, userToUpdate).single()

        // Assert
        assertTrue(result is Response.Error && result.exception is ObjectNotFoundException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_USER)
            checkExistence.execute(user, userToUpdate.id!!)
        }
    }

    @Test
    fun `should handle failure in existence check`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_USER) } returns true
        coEvery { checkExistence.execute(user, userToUpdate.id!!) } returns flowOf(Response.Error(Exception("Existence check failed")))

        // Act
        val result = updateUserUseCase.execute(user, userToUpdate).single()

        // Assert
        assertTrue(result is Response.Error)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_USER)
            checkExistence.execute(user, userToUpdate.id!!)
        }
    }

    @Test
    fun `should handle unexpected error during update`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(user, Permission.UPDATE_USER) } returns true
        coEvery { checkExistence.execute(user, userToUpdate.id!!) } returns flowOf(Response.Success(true))
       every { validatorService.validateEntity(userToUpdate) } throws NullPointerException()

        // Act
        val result = updateUserUseCase.execute(user, userToUpdate).single()

        // Assert
        assertTrue(result is Response.Error)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_USER)
            checkExistence.execute(user, userToUpdate.id!!)
            validatorService.validateEntity(userToUpdate)
        }
    }

}