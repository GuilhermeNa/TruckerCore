package com.example.truckercore.unit.model.shared.modules.personal_data.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.modules.personal_data.repository.PersonalDataRepository
import com.example.truckercore.model.shared.modules.personal_data.use_cases.implementations.CheckPersonalDataExistenceUseCaseImpl
import com.example.truckercore.model.shared.modules.personal_data.use_cases.interfaces.CheckPersonalDataExistenceUseCase
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class CheckPersonalDataExistenceUseCaseImplTest : KoinTest {

    private val requiredPermission: Permission = Permission.VIEW_PERSONAL_DATA
    private val permissionService: PermissionService by inject()
    private val repository: PersonalDataRepository by inject()
    private val useCase: CheckPersonalDataExistenceUseCase by inject()

    private val user = mockk<User>()
    private val id = "testId"

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
                        single<CheckPersonalDataExistenceUseCase> {
                            CheckPersonalDataExistenceUseCaseImpl(
                                Permission.VIEW_PERSONAL_DATA,
                                get(),
                                get()
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
    fun `execute() should return success when personal data exists`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(any(), any()) } returns true
        every { repository.entityExists(id) } returns flowOf(AppResponse.Success(Unit))

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is AppResponse.Success)
        verifyOrder {
            permissionService.canPerformAction(user, requiredPermission)
            repository.entityExists(id)
        }
    }

    @Test
    fun `execute() should throw UnauthorizedAccessException when user has no auth`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(any(), any()) } returns false

        // Call
        assertThrows<UnauthorizedAccessException> {
            useCase.execute(user, id).single()
        }

        // Assertions
        verify {
            permissionService.canPerformAction(user, requiredPermission)
        }
    }

    @Test
    fun `execute() should return empty when personal data does not exist`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(any(), any()) } returns true
        every { repository.entityExists(id) } returns flowOf(AppResponse.Empty)

        // Call
        val result = useCase.execute(user, id).single()

        // Assertions
        assertTrue(result is AppResponse.Empty)
        verifyOrder {
            permissionService.canPerformAction(user, requiredPermission)
            repository.entityExists(id)
        }
    }

}
