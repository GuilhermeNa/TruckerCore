package com.example.truckercore.unit.shared.modules.personal_data.use_cases

import com.example.truckercore._test_data_provider.TestPersonalDataDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.shared.modules.personal_data.mapper.PersonalDataMapper
import com.example.truckercore.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.implementations.CreatePersonalDataUseCaseImpl
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.CreatePersonalDataUseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.sealeds.Response
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

class CreatePersonalDataUseCaseImplTest {

    private val repository: PersonalDataRepository = mockk()
    private val validatorService: ValidatorService = mockk()
    private val permissionService: PermissionService = mockk()
    private val mapper: PersonalDataMapper = mockk()
    private lateinit var useCase: CreatePersonalDataUseCase
    private val user = TestUserDataProvider.getBaseEntity()
    private val personalData = TestPersonalDataDataProvider.getBaseEntity()
    private val dto = TestPersonalDataDataProvider.getBaseDto()
    private val id = "id"

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = CreatePersonalDataUseCaseImpl(
            repository,
            validatorService,
            permissionService,
            mapper,
            Permission.CREATE_PERSONAL_DATA
        )
    }

    @Test
    fun `should create entity when user has permission and data is valid`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.CREATE_PERSONAL_DATA
            )
        } returns true
        every { validatorService.validateForCreation(personalData) } returns Unit
        every { mapper.toDto(personalData) } returns dto
        coEvery { repository.create(dto) } returns flowOf(Response.Success(id))

        // Call
        val result = useCase.execute(user, personalData).single()

        // Assertions
        assertTrue(result is Response.Success && result.data == id)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.CREATE_PERSONAL_DATA)
            validatorService.validateForCreation(personalData)
            mapper.toDto(personalData)
            repository.create(dto)
        }
    }

    @Test
    fun `should return error when user does not have permission for creation`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.CREATE_PERSONAL_DATA
            )
        } returns false

        // Call
        val result = useCase.execute(user, personalData).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        verify {
            permissionService.canPerformAction(user, Permission.CREATE_PERSONAL_DATA)
        }
    }

    @Test
    fun `should return error when any unexpected exception occurs`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(user, Permission.CREATE_PERSONAL_DATA)
        } throws NullPointerException()

        // Call
        val result = useCase.execute(user, personalData).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        verify {
            permissionService.canPerformAction(user, Permission.CREATE_PERSONAL_DATA)
        }
    }

    @Test
    fun `should return error when database returns an error`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.CREATE_PERSONAL_DATA
            )
        } returns true
        every { validatorService.validateForCreation(personalData) } returns Unit
        every { mapper.toDto(personalData) } returns dto
        coEvery { repository.create(dto) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, personalData).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.CREATE_PERSONAL_DATA)
            validatorService.validateForCreation(personalData)
            mapper.toDto(personalData)
            repository.create(dto)
        }
    }

}