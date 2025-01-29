package com.example.truckercore.unit.shared.modules.personal_data.use_cases

import com.example.truckercore._test_data_provider.TestPersonalDataDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.shared.modules.personal_data.mapper.PersonalDataMapper
import com.example.truckercore.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.implementations.GetPersonalDataByParentIdUseCaseImpl
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.GetPersonalDataByParentIdUseCase
import com.example.truckercore.shared.sealeds.Response
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

class GetPersonalDataByParentIdUseCaseImplTest {

    private lateinit var useCase: GetPersonalDataByParentIdUseCase
    private var repository: PersonalDataRepository = mockk()
    private var permissionService: PermissionService = mockk()
    private var validatorService: ValidatorService = mockk()
    private var mapper: PersonalDataMapper = mockk()
    private val user = TestUserDataProvider.getBaseEntity()
    private val parentId = "parentId"
    private val personalData = TestPersonalDataDataProvider.getBaseEntity()
    private val dto = TestPersonalDataDataProvider.getBaseDto()

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = GetPersonalDataByParentIdUseCaseImpl(
            repository,
            permissionService,
            validatorService,
            mapper
        )
    }

    @Test
    fun `should retrieve the list of personal data when user has permission and data exists`() =
        runTest {
            // Arrange
            val dtoList = listOf(dto) // Lista com o dto
            val personalDataList = listOf(personalData) // Lista com a entidade convertida
            every {
                permissionService.canPerformAction(
                    user,
                    Permission.VIEW_PERSONAL_DATA
                )
            } returns true
            coEvery { repository.fetchByParentId(parentId) } returns flowOf(Response.Success(dtoList))
            every { validatorService.validateDto(dto) } returns Unit
            every { mapper.toEntity(dto) } returns personalData

            // Call
            val result = useCase.execute(user, parentId).single()

            // Assertions
            assertTrue(result is Response.Success && result.data == personalDataList)
            coVerifyOrder {
                permissionService.canPerformAction(user, Permission.VIEW_PERSONAL_DATA)
                repository.fetchByParentId(parentId)
                validatorService.validateDto(dto)
                mapper.toEntity(dto)
            }
        }

    @Test
    fun `should return an error when the user does not have permission`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_PERSONAL_DATA
            )
        } returns false

        // Call
        val result = useCase.execute(user, parentId).single()

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
        coEvery { repository.fetchByParentId(parentId) } returns flowOf(
            Response.Error(
                NullPointerException()
            )
        )

        // Call
        val result = useCase.execute(user, parentId).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_PERSONAL_DATA)
            repository.fetchByParentId(parentId)
        }
    }

    @Test
    fun `should return empty when no personal data is found for parent id`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_PERSONAL_DATA
            )
        } returns true
        coEvery { repository.fetchByParentId(parentId) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, parentId).single()

        // Assertions
        assertTrue(result is Response.Empty)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_PERSONAL_DATA)
            repository.fetchByParentId(parentId)
        }
    }

    @Test
    fun `should return error when any error occurs during dto validation or mapping`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.VIEW_PERSONAL_DATA
            )
        } returns true
        val dtoList = listOf(dto)
        coEvery { repository.fetchByParentId(parentId) } returns flowOf(Response.Success(dtoList))
        every { validatorService.validateDto(dto) } throws NullPointerException()

        // Call
        val result = useCase.execute(user, parentId).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.VIEW_PERSONAL_DATA)
            repository.fetchByParentId(parentId)
            validatorService.validateDto(dto)
        }
    }

}