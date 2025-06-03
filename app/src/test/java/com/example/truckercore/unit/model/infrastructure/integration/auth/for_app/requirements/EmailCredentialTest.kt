package com.example.truckercore.unit.model.infrastructure.integration.auth.for_app.requirements

import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.EmailCredential
import com.example.truckercore._shared.classes.Email
import com.example.truckercore._shared.classes.Password
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EmailCredentialTest {

    @Test
    fun `EmailCredential should correctly assign values to its properties`() {
        // Arrange
        val email = Email.from("john.doe@example.com")
        val password = Password.from("123456")

        // Act
        val emailCredential = EmailCredential(email, password)

        // Assert
        assertEquals(email, emailCredential.email)
        assertEquals(password, emailCredential.password)
    }

}