package com.example.truckercore.unit.modules.user.use_cases

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Level
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.modules.user.repository.UserRepository
import com.example.truckercore.modules.user.use_cases.implementations.CreateMasterUserUseCaseImpl
import com.example.truckercore.modules.user.use_cases.interfaces.CreateMasterUserUseCase
import com.example.truckercore.shared.errors.InvalidStateException
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreateMasterUserUseCaseImplTest {

    private lateinit var useCase: CreateMasterUserUseCase
    private val repository: UserRepository = mockk()
    private val validatorService: ValidatorService = mockk()
    private val mapper: UserMapper = mockk()

    private val masterUser = TestUserDataProvider.getBaseEntity()
    private val invalidMasterUser = TestUserDataProvider.getBaseEntity()
        .copy(level = Level.MODERATOR)

    @BeforeEach
    fun setup() {
        useCase = CreateMasterUserUseCaseImpl(repository, validatorService, mapper)
        mockStaticLog()
    }

    @Test
    fun `execute() should call properties in correct order and return success when user has correct level`() =
        runTest {
            // Arrange
            coEvery { validatorService.validateForCreation(masterUser) } returns Unit
            coEvery { mapper.toDto(masterUser) } returns UserDto(
                id = masterUser.id,
                businessCentralId = masterUser.businessCentralId
            )
            coEvery { repository.create(any()) } returns flowOf(Response.Success("user-created-id"))

            // Act
            val result = useCase.execute(masterUser).first()

            // Assert
            coVerifySequence {
                validatorService.validateForCreation(masterUser)
                mapper.toDto(masterUser)
                repository.create(any())
            }

            assertTrue(result is Response.Success && result.data == "user-created-id")
        }

    @Test
    fun `execute() should return error when user has incorrect level`() = runTest {
        // Arrange
        invalidMasterUser

        // Act
        val result = useCase.execute(invalidMasterUser).first()

        // Assert

        assertTrue(result is Response.Error && result.exception is InvalidStateException)

    }

    @Test
    fun `execute should handle unexpected error when mapper throws exception`() = runTest {
        // Arrange
        coEvery { validatorService.validateForCreation(masterUser) } returns Unit
        coEvery { mapper.toDto(masterUser) } throws NullPointerException()

        // Act
        val result = useCase.execute(masterUser).first()

        // Assert
        coVerifySequence {
            validatorService.validateForCreation(masterUser) // 1st call
            mapper.toDto(masterUser) // 2nd call (throws exception)
        }

        assertTrue(result is Response.Error && result.exception is NullPointerException)

    }

    @Test
    fun `execute should handle unexpected error from repository`() = runTest {
        // Arrange
        coEvery { validatorService.validateForCreation(masterUser) } returns Unit
        coEvery { mapper.toDto(masterUser) } returns UserDto(
            id = masterUser.id,
            businessCentralId = masterUser.businessCentralId
        )
        coEvery { repository.create(any()) } returns flowOf(Response.Error(NullPointerException()))

        // Act
        val result = useCase.execute(masterUser).first()

        // Assert
        coVerifySequence {
            validatorService.validateForCreation(masterUser) // 1st call
            mapper.toDto(masterUser) // 2nd call
            repository.create(any()) // 3rd call
        }

        assertTrue(result is Response.Error)

    }

    @Test
    fun `execute should handle empty response from repository`() = runTest {
        // Arrange
        coEvery { validatorService.validateForCreation(masterUser) } returns Unit
        coEvery { mapper.toDto(masterUser) } returns UserDto(
            id = masterUser.id,
            businessCentralId = masterUser.businessCentralId
        )
        coEvery { repository.create(any()) } returns flowOf(Response.Empty)

        // Act
        val result = useCase.execute(masterUser).first()

        // Assert
        coVerifySequence {
            validatorService.validateForCreation(masterUser) // 1st call
            mapper.toDto(masterUser) // 2nd call
            repository.create(any()) // 3rd call
        }

        assertTrue(result is Response.Empty)
    }

}