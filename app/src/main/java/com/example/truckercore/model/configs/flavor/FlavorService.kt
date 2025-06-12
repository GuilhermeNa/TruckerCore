package com.example.truckercore.model.configs.flavor

import android.app.Activity
import androidx.navigation.NavController
import com.example.truckercore.model.configs.flavor.contracts.Flavor
import com.example.truckercore.model.configs.flavor.contracts.FlavorStrategy
import com.example.truckercore.model.infrastructure.security.data.enums.Role
import com.example.truckercore.view.nav_login.fragments.login.navigator.LoginFragmentStrategy
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomePagerData
import java.lang.ref.WeakReference

class FlavorService(private val strategy: FlavorStrategy) {

    fun getLoginFragmentStrategy(navController: NavController): LoginFragmentStrategy =
        strategy.getLoginNavigatorStrategy(navController)

    fun getRole(): Role = strategy.getRole()

    private fun getFlavor(): Flavor = strategy.getFlavor()

    fun getAppName(): String = getFlavor().appName

    fun getWelcomePagerData(): List<WelcomePagerData> = strategy.getWelcomePagerData()

    fun navigateToMain(current: Activity) = strategy.navigateToMain(current)

}