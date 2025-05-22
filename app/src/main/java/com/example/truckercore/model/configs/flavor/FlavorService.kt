package com.example.truckercore.model.configs.flavor

import android.content.Context
import android.content.Intent
import com.example.truckercore.model.configs.flavor.contracts.Flavor
import com.example.truckercore.model.configs.flavor.contracts.FlavorStrategy
import com.example.truckercore.model.infrastructure.security.data.enums.Role
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomePagerData

class FlavorService(private val strategy: FlavorStrategy) {

    fun getRole(): Role = strategy.getRole()

    private fun getFlavor(): Flavor = strategy.getFlavor()

    fun getAppName(): String = getFlavor().appName

    fun enterSystemIntent(context: Context): Intent = strategy.enterSystemIntent(context)

    fun getWelcomePagerData(): List<WelcomePagerData> = strategy.getWelcomePagerData()

}