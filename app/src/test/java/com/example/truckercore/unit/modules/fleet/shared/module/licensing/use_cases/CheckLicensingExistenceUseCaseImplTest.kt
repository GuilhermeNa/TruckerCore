package com.example.truckercore.unit.modules.fleet.shared.module.licensing.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.modules.fleet.shared.module.licensing.repository.LicensingRepository
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations.CheckLicensingExistenceUseCaseImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.CheckLicensingExistenceUseCase
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.shared.utils.sealeds.Response
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
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class CheckLicensingExistenceUseCaseImplTest : KoinTest {

    private val permissionService: PermissionService by inject()
    private val repository: LicensingRepository by inject()
    private val useCase: CheckLicensingExistenceUseCase by inject()

    private val requiredPermission = Permission.VIEW_LICENSING
    private val user = mockk<User>(relaxed = true)
    private val id = "testLicensingId"

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<PermissionService> { mockk() }
                        single<LicensingRepository> { mockk() }
                        single<CheckLicensingExistenceUseCase> {
                            CheckLicensingExistenceUseCaseImpl(
                                Permission.VIEW_LICENSING,
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
    fun `execute() should return a response success when licensing object is found`() = runTest {
        // Arrange
        every { permissionService.canPerformAction(any(), any()) } returns true
        every { repository.entityExists(id) } returns flowOf(Response.Success(Unit))

        // Call
        val response = useCase.execute(user, id).single()

        // Assertions
        assertTrue(response is Response.Success)

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
        val exception = assertThrows<UnauthorizedAccessException> {
            useCase.execute(user, id).single()
        }

        // Assertions
        assertTrue(exception.permission == Permission.VIEW_LICENSING)
        verify {
            permissionService.canPerformAction(user, requiredPermission)
        }
    }

    @Test
    fun `execute() should return empty when the licensing object is not found`() = runTest {
        // Behavior
        every { permissionService.canPerformAction(any(), any()) } returns true
        every { repository.entityExists(id) } returns flowOf(Response.Empty)

        // Call
        val response = useCase.execute(user, id).single()

        // Assertions
        assertTrue(response is Response.Empty)
        verifyOrder {
            permissionService.canPerformAction(user, requiredPermission)
            repository.entityExists(id)
        }
    }

}
