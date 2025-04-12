package com.example.truckercore.unit.model.shared.abstractions

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionServiceImpl
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.abstractions.UseCase
import com.example.truckercore.model.shared.utils.sealeds.AppResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertTrue

class UseCaseTest : KoinTest {

    private class SomeUseCase(
        override val requiredPermission: Permission,
        override val permissionService: PermissionService
    ) : UseCase(permissionService) {

        fun testRun(user: User): Flow<Response<Unit>> {
            val block = flow { emit(Response.Success(Unit)) }
            return user.runIfPermitted { block }
        }

    }

    private val useCase: SomeUseCase by inject()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            startKoin {
                modules(
                    module {
                        single<PermissionService> { PermissionServiceImpl() }
                        single { SomeUseCase(Permission.VIEW_USER, get()) }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()

    }

    @Test
    fun `should run the block when the user have the correct permission`() = runTest {
        // Arrange
        val user = TestUserDataProvider.getBaseEntity()
            .copy(permissions = hashSetOf(Permission.VIEW_USER))

        // Call
        val result = useCase.testRun(user).single()

        // Assertions
        assertTrue(result is Response.Success)
    }

    @Test
    fun `should throw UnauthorizedAccessException when the user have the correct permission`() =
        runTest {
            // Arrange
            val user = TestUserDataProvider.getBaseEntity()
                .copy(permissions = hashSetOf())

            // Call
            assertThrows<UnauthorizedAccessException> {
                useCase.testRun(user).single()
            }
        }

}