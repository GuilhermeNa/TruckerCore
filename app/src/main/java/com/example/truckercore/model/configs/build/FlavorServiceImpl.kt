package com.example.truckercore.model.configs.build

import android.content.Context

class FlavorServiceImpl(private val context: Context) : FlavorService {

    private val nameFlavorMap = Flavor.entries.associateBy { it.description }

    override fun getFlavor(): Flavor {
        val appName = getAppName()
        return nameFlavorMap[appName] ?: throw BuildException(message = ERROR_MESSAGE)
    }

    private fun getAppName() = context.applicationContext.packageManager
        .getApplicationLabel(context.applicationInfo).toString()

    companion object {
        private const val ERROR_MESSAGE = "Unrecognized app name. Unable to determine flavor. " +
                "Ensure the application label matches one of the expected values."
    }

}