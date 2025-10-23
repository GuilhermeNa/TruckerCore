package com.example.truckercore.layers.presentation.viewmodels._shared._contracts

import com.example.truckercore.domain._shared.components.Visibility

interface UiComponent {

    val isEnabled: Boolean
    val visibility: Visibility

}