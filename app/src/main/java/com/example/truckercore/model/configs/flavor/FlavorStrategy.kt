package com.example.truckercore.model.configs.flavor

import android.content.Context
import android.content.Intent

interface FlavorStrategy {

    fun getFlavor(): Flavor

    fun enterSystemIntent(context: Context): Intent

}