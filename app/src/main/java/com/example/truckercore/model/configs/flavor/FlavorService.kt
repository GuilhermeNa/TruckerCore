package com.example.truckercore.model.configs.flavor

import android.content.Context
import android.content.Intent

object FlavorService {

    private lateinit var strategy: FlavorStrategy

    fun getFlavor(): Flavor = strategy.getFlavor()

    fun enterSystemIntent(context: Context): Intent = strategy.enterSystemIntent(context)

    fun setStrategy(newStrategy: FlavorStrategy) {
        strategy = newStrategy
    }

}