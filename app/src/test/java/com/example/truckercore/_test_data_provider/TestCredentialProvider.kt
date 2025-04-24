package com.example.truckercore._test_data_provider

import com.example.truckercore.model.infrastructure.integration.auth.for_app.requirements.EmailCredential
import com.example.truckercore.model.infrastructure.integration.auth.for_app.requirements.UserProfile
import com.example.truckercore.model.shared.value_classes.Email
import com.example.truckercore.model.shared.value_classes.FullName
import com.example.truckercore.model.shared.value_classes.Password

class TestCredentialProvider {

    val name = FullName("John Doe")

    val email = Email("email@email.com")

    val password = Password.from("123456")

    val userProfile = UserProfile(fullName = name)

    val credential = EmailCredential(
        name = name,
        email = email,
        password = password
    )

}