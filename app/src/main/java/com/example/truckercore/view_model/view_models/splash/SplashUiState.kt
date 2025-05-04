package com.example.truckercore.view_model.view_models.splash

import com.example.truckercore.model.configs.build.Flavor

sealed class SplashUiState(open val flavor: Flavor) {
    data class Initial(override val flavor: Flavor): SplashUiState(flavor)
    data class Loading(override val flavor: Flavor): SplashUiState(flavor)
    data class Loaded(override val flavor: Flavor): SplashUiState(flavor)
}