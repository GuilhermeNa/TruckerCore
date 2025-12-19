package com.example.truckercore.core.config.flavor

import android.app.Activity
import com.example.truckercore.layers.domain.model.access.Role
import com.example.truckercore.layers.presentation.login.view_model.welcome_fragment.helpers.WelcomePagerData

class FlavorService(private val strategy: FlavorStrategy) {

    fun navigateToCheckIn(current: Activity) = strategy.navigateToCheckIn(current)

    fun getRole(): Role = strategy.getRole()

    private fun getFlavor(): Flavor = strategy.getFlavor()

    fun getAppName(): String = getFlavor().appName

    fun getWelcomeFragmentPagerData(): List<WelcomePagerData> = strategy.getWelcomePagerData()

}