package com.example.truckercore.unit.infrastructure.security.permissions.service

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.infrastructure.security.permissions.service.PermissionService
import com.example.truckercore.infrastructure.security.permissions.service.PermissionServiceImpl
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PermissionServiceImplTest {

    private lateinit var service: PermissionService

    @BeforeEach
    fun setup() {
        service = PermissionServiceImpl()
    }

    @Test
    fun `canPerformAction() should return true when the user has required permission`() {
        // Object
        val user = TestUserDataProvider.getBaseEntity()

        // Call
        val result = service.canPerformAction(user, Permission.VIEW_USER)

        // Assert
        assertTrue(result)

    }

    @Test
    fun `canPerformAction() should return false when the user does not have the required permission`() {
        // Object
        val user = TestUserDataProvider.getBaseEntity()

        // Call
        val result = service.canPerformAction(user, Permission.CREATE_PERSONAL_DATA)

        // Assert
        assertFalse(result)

    }


}