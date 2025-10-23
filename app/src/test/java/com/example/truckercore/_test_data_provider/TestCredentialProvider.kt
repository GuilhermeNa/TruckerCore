package com.example.truckercore._test_data_provider

import com.example.truckercore.core.classes.EmailCredential
import com.example.truckercore.core.classes.Email
import com.example.truckercore.core.classes.FullName
import com.example.truckercore.core.my_lib.classes.Password

class TestCredentialProvider {

    val name = FullName.from("John Doe")

    val email = Email.from("email@email.com")

    val password = com.example.truckercore.core.my_lib.classes.Password.from("123456")

   // val userProfile = UserCategory(fullName = name)

    val credential = EmailCredential(
        email = email,
        password = password
    )

}