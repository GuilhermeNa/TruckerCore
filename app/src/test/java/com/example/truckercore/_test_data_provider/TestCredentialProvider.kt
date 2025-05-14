package com.example.truckercore._test_data_provider

import com.example.truckercore.model.infrastructure.integration.auth.for_app.data.EmailCredential
import com.example.truckercore._utils.classes.Email
import com.example.truckercore._utils.classes.FullName
import com.example.truckercore._utils.classes.Password

class TestCredentialProvider {

    val name = FullName.from("John Doe")

    val email = Email.from("email@email.com")

    val password = Password.from("123456")

    val userProfile = UserCategory(fullName = name)

    val credential = EmailCredential(
        email = email,
        password = password
    )

}