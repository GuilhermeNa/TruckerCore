/*
package com.example.truckercore.unit.shared.modules.personal_data.use_cases

import com.example.truckercore._test_data_provider.TestPersonalDataDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.shared.modules.personal_data.mapper.PersonalDataMapper
import com.example.truckercore.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetPersonalDataByIdUseCaseImplTest {

    private lateinit var useCase: GetPersonalDataByIdUseCase
    private var repository: PersonalDataRepository = mockk()
    private var permissionService: PermissionService = mockk()
    private var validatorService: ValidatorService = mockk()
    private var mapper: PersonalDataMapper = mockk()
    private val user = TestUserDataProvider.getBaseEntity()
    private val id = "id"
    private val personalData = TestPersonalDataDataProvider.getBaseEntity()
    private val dto = TestPersonalDataDataProvider.getBaseDto()

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase =
            GetPersonalDataByIdUseCaseImpl(
                repository,
                validatorService,
                mapper,
                permissionService,
                Permission.VIEW_PERSONAL_DATA
            )
    }

    @Test
    fun `should retrieve the entity when it's found and has permission`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_PERSONAL_DATA
            )
        } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Success(dto))
        every { validatorService.validateDto(dto) } returns Unit
        every { mapper.toEntity(dto) } returns personalData

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Success && result.data == personalData)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_PERSONAL_DATA)
            repository.fetchById(id)
            validatorService.validateDto(dto)
            mapper.toEntity(dto)
        }
    }

    @Test
    fun `should return an error when the user does not have auth`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_PERSONAL_DATA
            )
        } returns false

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
        coVerify {
            permissionService.canPerformAction(user, Permission.VIEW_PERSONAL_DATA)
        }
    }

    @Test
    fun `should return an error when the repository returns an error`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_PERSONAL_DATA
            )
        } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_PERSONAL_DATA)
            repository.fetchById(id)
        }
    }

    @Test
    fun `should return empty when the repository returns empty`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_PERSONAL_DATA
            )
        } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Empty)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_PERSONAL_DATA)
            repository.fetchById(id)
        }
    }

    @Test
    fun `should return error when any error in flow occurs`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_PERSONAL_DATA
            )
        } returns true
        coEvery { repository.fetchById(id) } returns flowOf(Response.Success(dto))
        every { validatorService.validateDto(dto) } returns Unit
        every { mapper.toEntity(dto) } throws NullPointerException()

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_PERSONAL_DATA)
            repository.fetchById(id)
            validatorService.validateDto(dto)
            mapper.toEntity(dto)
        }
    }

}*/
