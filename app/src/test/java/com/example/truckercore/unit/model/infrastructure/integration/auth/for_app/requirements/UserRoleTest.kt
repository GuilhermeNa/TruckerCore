package com.example.truckercore.unit.model.infrastructure.integration.auth.for_app.requirements

import com.example.truckercore._shared.classes.FullName
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserRoleTest {

    @Test
    fun `UserProfile should correctly assign fullName to its property`() {
        // Arrange
        val fullName = FullName.from("John Doe")

        // Act
        val userProfile = UserCategory(fullName)

        // Assert
        assertEquals(fullName, userProfile.fullName)
    }

}