package com.example.truckercore.view_model.view_models.user_name.state

import com.example.truckercore.model.shared.utils.expressions.isFullNameFormat
import com.example.truckercore.view_model._shared.components.FabComponent
import com.example.truckercore.view_model._shared.components.TextInputComponent
import com.example.truckercore.view_model._shared.components.Visibility

data class UserNameComponents(
    val nameComponent: TextInputComponent = TextInputComponent(),
    val fabComponent: FabComponent = FabComponent(visibility = Visibility.GONE)
) {

    fun updateName(name: String): UserNameComponents {
        val updatedName = when {
            name.isEmpty() -> TextInputComponent(text = name, errorText = MSG_EMPTY_FIELD)
            !name.isFullNameFormat() -> TextInputComponent(
                text = name,
                errorText = MSG_INVALID_NAME
            )

            else -> TextInputComponent(text = name, isValid = true)
        }
        val updatedFab = if (updatedName.isValid) {
            FabComponent(visibility = Visibility.VISIBLE)
        } else FabComponent(visibility = Visibility.GONE)

        return copy(nameComponent = updatedName, fabComponent = updatedFab)
    }

    private companion object {
        private const val MSG_EMPTY_FIELD = "Este campo deve ser preenchido"
        private const val MSG_INVALID_NAME = "Preencha nome e sobrenome"
    }

}