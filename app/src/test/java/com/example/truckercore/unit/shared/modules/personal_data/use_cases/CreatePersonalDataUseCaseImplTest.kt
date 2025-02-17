package com.example.truckercore.unit.shared.modules.personal_data.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.modules.personal_data.dto.PersonalDataDto
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.modules.personal_data.mapper.PersonalDataMapper
import com.example.truckercore.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.shared.modules.personal_data.use_cases.implementations.CreatePersonalDataUseCaseImpl
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.CreatePersonalDataUseCase
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.sealeds.Response
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class CreatePersonalDataUseCaseImplTest : KoinTest {

    private val requiredPermission: Permission = Permission.CREATE_PERSONAL_DATA
    private val permissionService: PermissionService by inject()
    private val repository: PersonalDataRepository by inject()
    private val validatorService: ValidatorService by inject()
    private val mapper: PersonalDataMapper by inject()
    private val useCase: CreatePersonalDataUseCase by inject()

    private val user = mockk<User>()
    private val personalData = mockk<PersonalData>()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<PermissionService> { mockk() }
                        single<PersonalDataRepository> { mockk() }
                        single<ValidatorService> { mockk() }
                        single<PersonalDataMapper> { mockk() }
                        single<CreatePersonalDataUseCase> {
                            CreatePersonalDataUseCaseImpl(
                                Permission.CREATE_PERSONAL_DATA,
                                get(), get(), get(), get()
                            )
                        }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()
    }

    @Test
    fun `execute() should return success when personal data is correctly created`() = runTest {
        // Arrange
        val dto = mockk<PersonalDataDto>()
        val id = "newPersonalDataId"
        every { permissionService.canPerformAction(any(), any()) } returns true
        every { validatorService.validateForCreation(any()) } returns Unit
        every { mapper.toDto(any()) } returns dto
        every { repository.create(any()) } returns flowOf(Response.Success(id))

        // Call
        val result = useCase.execute(user, personalData).single()

        // Assertions
        assertEquals(id, (result as Response.Success).data)
        verifyOrder {
            permissionService.canPerformAction(user, requiredPermission)
            validatorService.validateForCreation(personalData)
            mapper.toDto(personalData)
            repository.create(dto)
        }
    }

    @Test
    fun `execute() should throw UnauthorizedAccessException when user has no auth`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(any(), any()) } returns false

        // Call
        assertThrows<UnauthorizedAccessException> {
            useCase.execute(user, personalData).single()
        }

        // Assertions
        verify {
            permissionService.canPerformAction(user, requiredPermission)
        }
    }

    @Test
    fun `execute() should throw InvalidStateException when user is unauthorized to create personal data`() =
        runTest {
            // Arrange
            every { permissionService.canPerformAction(any(), any()) } returns false

            // Call
            assertThrows<UnauthorizedAccessException> {
                useCase.execute(user, personalData).single()
            }

            // Assertions
            verify {
                permissionService.canPerformAction(user, requiredPermission)
            }
        }

}
