package com.example.truckercore.model.configs.flavor.contracts

import android.content.Context
import android.content.Intent
import com.example.truckercore.model.infrastructure.security.data.enums.Role
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomePagerData

interface FlavorStrategy {

    fun getRole(): Role

    fun getFlavor(): Flavor

    fun enterSystemIntent(context: Context): Intent

    fun getWelcomePagerData(): List<WelcomePagerData>

}