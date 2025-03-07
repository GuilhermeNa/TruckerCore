package com.example.truckercore.unit.model.infrastructure.security.permissions.service

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.infrastructure.security.permissions.service.PermissionServiceImpl
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class PermissionServiceTest : KoinTest {

    private val service: PermissionService by inject()
    private val userWithViewPermission = getUserWithViewPermission()

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            startKoin {
                modules(
                    module {
                        single<PermissionService> { PermissionServiceImpl() }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()

    }

    private fun getUserWithViewPermission() =
        TestUserDataProvider.getBaseEntity().copy(permissions = hashSetOf(Permission.VIEW_USER))

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


}