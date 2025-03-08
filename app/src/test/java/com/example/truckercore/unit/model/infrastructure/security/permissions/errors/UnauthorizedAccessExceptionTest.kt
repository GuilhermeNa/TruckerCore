package com.example.truckercore.unit.model.infrastructure.security.permissions.errors

import com.example.truckercore.model.infrastructure.security.permissions.enums.Permission
import com.example.truckercore.model.infrastructure.security.permissions.errors.UnauthorizedAccessException
import com.example.truckercore.model.modules.user.entity.User
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertTrue

class UnauthorizedAccessExceptionTest {

    @Test
    fun `should return correct message for UnauthorizedAccessException`() {
        // Arrange
        val userId = "uid"
        val user = mockk<User> {
            every { id } returns userId
        }
        val permission = Permission.VIEW_USER

        // Act
        val exception = assertThrows<UnauthorizedAccessException> {
            throw UnauthorizedAccessException(user, permission)
        }

        // Assert
        assertTrue(exception.message.contains(userId))
        assertTrue(exception.message.contains(permission.name))
    }

}