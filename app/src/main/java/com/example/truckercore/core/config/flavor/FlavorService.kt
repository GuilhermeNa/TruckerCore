package com.example.truckercore.core.config.flavor

import android.app.Activity
import androidx.navigation.NavController
import com.example.truckercore.layers.domain.model.access.Role
import com.example.truckercore.layers.presentation.nav_login.fragments.login.navigator.LoginFragmentStrategy
import com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.WelcomePagerData

class FlavorService(private val strategy: FlavorStrategy) {

    fun getLoginFragmentStrategy(navController: NavController): LoginFragmentStrategy =
        strategy.getLoginNavigatorStrategy(navController)

    fun getRole(): Role = strategy.getRole()

    private fun getFlavor(): Flavor = strategy.getFlavor()

    fun getAppName(): String = getFlavor().appName

    fun getWelcomeFragmentPagerData(): List<WelcomePagerData> = strategy.getWelcomePagerData()

    fun navigateToMain(current: Activity) = strategy.navigateToMain(current)

}