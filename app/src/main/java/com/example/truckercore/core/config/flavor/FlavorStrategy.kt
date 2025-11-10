package com.example.truckercore.core.config.flavor

import android.app.Activity
import androidx.navigation.NavController
import com.example.truckercore.layers.domain.model.access.Role
import com.example.truckercore.layers.presentation.nav_login.fragments.login.navigator.LoginFragmentStrategy
import com.example.truckercore.layers.presentation.nav_login.fragments.welcome.navigator.WelcomeNavigatorStrategy
import com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.WelcomePagerData

interface FlavorStrategy {

    fun getWelcomeNavigatorStrategy(): WelcomeNavigatorStrategy

    fun getLoginNavigatorStrategy(navController: NavController): LoginFragmentStrategy

    fun getRole(): Role

    fun getFlavor(): Flavor

    fun navigateToMain(current: Activity)

    fun getWelcomePagerData(): List<WelcomePagerData>

}
