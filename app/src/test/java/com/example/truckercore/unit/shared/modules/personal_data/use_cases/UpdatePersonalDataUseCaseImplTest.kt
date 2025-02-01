package com.example.truckercore.unit.shared.modules.personal_data.use_cases

import com.example.truckercore._test_data_provider.TestPersonalDataDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.shared.modules.personal_data.mapper.PersonalDataMapper
import com.example.truckercore.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.implementations.UpdatePersonalDataUseCaseImpl
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.CheckPersonalDataExistenceUseCase
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.UpdatePersonalDataUseCase
import com.example.truckercore.shared.utils.sealeds.Response
import com.example.truckercore.shared.services.ValidatorService
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UpdatePersonalDataUseCaseImplTest {

    private val repository: PersonalDataRepository = mockk()
    private val checkExistence: CheckPersonalDataExistenceUseCase = mockk()
    private val permissionService: PermissionService = mockk()
    private val validatorService: ValidatorService = mockk()
    private val mapper: PersonalDataMapper = mockk()
    private lateinit var useCase: UpdatePersonalDataUseCase

    private val user = TestUserDataProvider.getBaseEntity()
    private val personalData = TestPersonalDataDataProvider.getBaseEntity()
    private val dto = TestPersonalDataDataProvider.getBaseDto()

    @BeforeEach
    fun setup() {
        mockStaticLog()
        useCase = UpdatePersonalDataUseCaseImpl(
            repository,
            checkExistence,
            permissionService,
            validatorService,
            mapper
        )
    }

    @Test
    fun `should update personal data when user has permission and data exists`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.UPDATE_PERSONAL_DATA
            )
        } returns true
        coEvery { checkExistence.execute(user, personalData.id!!) } returns flowOf(
            Response.Success(
                Unit
            )
        )
        every { validatorService.validateEntity(personalData) } returns Unit
        every { mapper.toDto(personalData) } returns dto
        coEvery { repository.update(dto) } returns flowOf(Response.Success(Unit))

        // Call
        val result = useCase.execute(user, personalData).single()

        // Assertions
        assertTrue(result is Response.Success)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_PERSONAL_DATA)
            checkExistence.execute(user, personalData.id!!)
            validatorService.validateEntity(personalData)
            mapper.toDto(personalData)
            repository.update(dto)
        }
    }

    @Test
    fun `should return error when user does not have permission to update personal data`() =
        runTest {
            // Arrange
            every {
                permissionService.canPerformAction(
                    user,
                    Permission.UPDATE_PERSONAL_DATA
                )
            } returns false

            // Call
            val result = useCase.execute(user, personalData).single()

            // Assertions
            assertTrue(result is Response.Error && result.exception is UnauthorizedAccessException)
            coVerifyOrder {
                permissionService.canPerformAction(user, Permission.UPDATE_PERSONAL_DATA)
            }
        }

    @Test
    fun `should return error when personal existence check returns an error`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.UPDATE_PERSONAL_DATA
            )
        } returns true
        coEvery { checkExistence.execute(user, personalData.id!!) } returns flowOf(
            Response.Error(
                NullPointerException()
            )
        )

        // Call
        val result = useCase.execute(user, personalData).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_PERSONAL_DATA)
            checkExistence.execute(user, personalData.id!!)
        }
    }

    @Test
    fun `should return error when personal data existence check returns empty`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.UPDATE_PERSONAL_DATA
            )
        } returns true
        coEvery { checkExistence.execute(user, personalData.id!!) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, personalData).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is ObjectNotFoundException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_PERSONAL_DATA)
            checkExistence.execute(user, personalData.id!!)
        }
    }

    @Test
    fun `should return error when repository returns an error during update`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.UPDATE_PERSONAL_DATA
            )
        } returns true
        coEvery { checkExistence.execute(user, personalData.id!!) } returns flowOf(
            Response.Success(
                Unit
            )
        )
        every { validatorService.validateEntity(personalData) } returns Unit
        every { mapper.toDto(personalData) } returns dto
        coEvery { repository.update(dto) } returns flowOf(Response.Error(NullPointerException()))

        // Call
        val result = useCase.execute(user, personalData).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_PERSONAL_DATA)
            checkExistence.execute(user, personalData.id!!)
            validatorService.validateEntity(personalData)
            mapper.toDto(personalData)
            repository.update(dto)
        }
    }

    @Test
    fun `should return empty when repository returns empty`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.UPDATE_PERSONAL_DATA
            )
        } returns true
        coEvery { checkExistence.execute(user, personalData.id!!) } returns flowOf(
            Response.Success(
                Unit
            )
        )
        every { validatorService.validateEntity(personalData) } returns Unit
        every { mapper.toDto(personalData) } returns dto
        coEvery { repository.update(dto) } returns flowOf(Response.Empty)

        // Call
        val result = useCase.execute(user, personalData).single()

        // Assertions
        assertTrue(result is Response.Empty)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_PERSONAL_DATA)
            checkExistence.execute(user, personalData.id!!)
            validatorService.validateEntity(personalData)
            mapper.toDto(personalData)
            repository.update(dto)
        }
    }

    @Test
    fun `should return error when any unexpected error occurs in flow`() = runTest {
        // Arrange
        every {
            permissionService.canPerformAction(
                user,
                Permission.UPDATE_PERSONAL_DATA
            )
        } returns true
        coEvery { checkExistence.execute(user, personalData.id!!) } returns flowOf(
            Response.Success(
                Unit
            )
        )
        every { validatorService.validateEntity(personalData) } returns Unit
        every { mapper.toDto(personalData) } throws NullPointerException()

        // Call
        val result = useCase.execute(user, personalData).single()

        // Assertions
        assertTrue(result is Response.Error && result.exception is NullPointerException)
        coVerifyOrder {
            permissionService.canPerformAction(user, Permission.UPDATE_PERSONAL_DATA)
            checkExistence.execute(user, personalData.id!!)
            validatorService.validateEntity(personalData)
            mapper.toDto(personalData)
        }
    }

}