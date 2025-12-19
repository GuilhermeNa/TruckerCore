package com.example.truckercore.core.config.flavor

import android.app.Activity
import com.example.truckercore.layers.domain.model.access.Role
import com.example.truckercore.layers.presentation.login.view_model.welcome_fragment.helpers.WelcomePagerData

interface FlavorStrategy {

    fun navigateToCheckIn(current: Activity)

    fun getRole(): Role

    fun getFlavor(): Flavor

    fun navigateToMain(current: Activity)

    fun getWelcomePagerData(): List<WelcomePagerData>

}
