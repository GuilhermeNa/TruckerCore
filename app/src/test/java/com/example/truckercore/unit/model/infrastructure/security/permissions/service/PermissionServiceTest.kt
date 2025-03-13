package com.example.truckercore.unit.model.infrastructure.security.permissions.service

import com.example.truckercore._test_data_provider.TestBusinessCentralDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.model.infrastructure.security.permissions.service.PermissionServiceImpl
import com.example.truckercore.model.modules.user.entity.User
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class PermissionServiceTest : KoinTest {

    private val centralProvider = TestBusinessCentralDataProvider
    private val service: PermissionService by inject()

    private val userWithViewPermission =
        TestUserDataProvider.getBaseEntity().copy(permissions = hashSetOf(Permission.VIEW_USER))

    @BeforeEach
    fun setup() {
        startKoin {
            modules(
                module {
                    single<PermissionService> { PermissionServiceImpl() }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()

    @Test
    fun `canPerformAction() should return true when the user has required permission`() {
        // Call
        val result = service.canPerformAction(userWithViewPermission, Permission.VIEW_USER)

        // Assert
        assertTrue(result)
    }

    @Test
    fun `canPerformAction() should return false when the user does not have the required permission`() {
        // Call
        val result =
            service.canPerformAction(userWithViewPermission, Permission.CREATE_PERSONAL_DATA)

        // Assert
        assertFalse(result)
    }

    @Test
    fun `hasSystemAccess() should return true when the user can access a business central`() {
        // Arrange
        val user: User = mockk(relaxed = true) { every { id } returns "userId" }
        val central = centralProvider.getBaseEntity().copy(authorizedUserIds = hashSetOf("userId"))

        // Call
        val result = service.hasSystemAccess(user, central)

        // Assert
        assertTrue(result)

    }

    @Test
    fun `hasSystemAccess() should return false when the user cant access a business central`() {
        // Arrange
        val user: User = mockk(relaxed = true) { every { id } returns "userId" }
        val central = centralProvider.getBaseEntity().copy(authorizedUserIds = hashSetOf("otherId"))

        // Call
        val result = service.hasSystemAccess(user, central)

        // Assert
        assertFalse(result)

    }

    @Test
    fun `hasSystemAccess() should return false when the business central set is empty`() {
        // Arrange
        val user: User = mockk(relaxed = true) { every { id } returns "userId" }
        val central = centralProvider.getBaseEntity().copy(authorizedUserIds = hashSetOf())

        // Call
        val result = service.hasSystemAccess(user, central)

        // Assert
        assertFalse(result)

    }

}