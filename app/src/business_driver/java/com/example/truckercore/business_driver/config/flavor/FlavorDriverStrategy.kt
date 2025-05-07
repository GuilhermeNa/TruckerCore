package com.example.truckercore.business_driver.config.flavor

import android.content.Context
import android.content.Intent
import com.example.truckercore.business_driver.view.activities.MainActivity
import com.example.truckercore.model.configs.flavor.FlavorStrategy

class FlavorDriverStrategy : FlavorStrategy {

    override fun getFlavor() = Driver()

    override fun enterSystemIntent(context: Context): Intent =
        Intent(context, MainActivity::class.java)

}