package com.example.truckercore.view_model.view_models.verifying_email.state

import com.example.truckercore._shared.classes.Email
import com.example.truckercore.view_model._shared.components.TextComponent

data class VerifyingEmailComponents(
    val emailComponent: TextComponent = TextComponent(),
    val headerComponent: TextComponent = TextComponent()
) {

    fun initializeEmail(email: Email) = copy(emailComponent = TextComponent(text = email.value))

    fun updateHeader(cText: String) = copy(headerComponent = TextComponent(text = cText))

}