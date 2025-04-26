package com.example.truckercore.unit.model.infrastructure.integration.auth.for_app.requirements

import com.example.truckercore.model.infrastructure.integration.auth.for_app.requirements.EmailCredential
import com.example.truckercore.model.shared.value_classes.Email
import com.example.truckercore.model.shared.value_classes.FullName
import com.example.truckercore.model.shared.value_classes.Password
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EmailCredentialTest {

    @Test
    fun `EmailCredential should correctly assign values to its properties`() {
        // Arrange
        val name = FullName("John Doe")
        val email = Email("john.doe@example.com")
        val password = Password.from("123456")

        // Act
        val emailCredential = EmailCredential(name, email, password)

        // Assert
        assertEquals(name, emailCredential.name)
        assertEquals(email, emailCredential.email)
        assertEquals(password, emailCredential.password)
    }

}