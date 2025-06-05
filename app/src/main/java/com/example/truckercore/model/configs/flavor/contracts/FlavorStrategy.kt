package com.example.truckercore.model.configs.flavor.contracts

import android.content.Context
import android.content.Intent
import androidx.navigation.NavController
import com.example.truckercore.model.infrastructure.security.data.enums.Role
import com.example.truckercore.view.fragments.login.navigator.LoginFragmentStrategy
import com.example.truckercore.view.fragments.welcome.navigator.WelcomeNavigatorStrategy
import com.example.truckercore.view.nav_login.fragments.login.navigator.LoginFragmentStrategy
import com.example.truckercore.view.nav_login.fragments.welcome.navigator.WelcomeNavigatorStrategy
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomePagerData

interface FlavorStrategy {

    fun getWelcomeNavigatorStrategy(): WelcomeNavigatorStrategy

    fun getLoginNavigatorStrategy(navController: NavController): LoginFragmentStrategy

    fun getRole(): Role

    fun getFlavor(): Flavor

    fun enterSystemIntent(context: Context): Intent

    fun getWelcomePagerData(): List<WelcomePagerData>

}
