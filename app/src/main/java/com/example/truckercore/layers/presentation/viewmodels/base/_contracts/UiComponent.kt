package com.example.truckercore.layers.presentation.viewmodels.base._contracts

import com.example.truckercore.domain._shared.components.Visibility

interface UiComponent {

    val isEnabled: Boolean
    val visibility: Visibility

}