package com.example.truckercore.unit.model.infrastructure.integration.auth.for_app.requirements

import com.example.truckercore.model.infrastructure.integration.auth.for_app.requirements.UserProfile
import com.example.truckercore.model.shared.value_classes.FullName
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserProfileTest {

    @Test
    fun `UserProfile should correctly assign fullName to its property`() {
        // Arrange
        val fullName = FullName.from("John Doe")

        // Act
        val userProfile = UserProfile(fullName)

        // Assert
        assertEquals(fullName, userProfile.fullName)
    }

}