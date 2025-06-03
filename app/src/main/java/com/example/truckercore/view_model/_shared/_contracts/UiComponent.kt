package com.example.truckercore.view_model._shared._contracts

import com.example.truckercore.view_model._shared.components.Visibility

interface UiComponent {

    val isEnabled: Boolean
    val visibility: Visibility

}