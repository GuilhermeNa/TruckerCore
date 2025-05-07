package com.example.truckercore.business_admin.config.flavor

import android.content.Context
import android.content.Intent
import com.example.truckercore.business_admin.view.activities.MainActivity
import com.example.truckercore.model.configs.flavor.FlavorStrategy

class FlavorAdminStrategy : FlavorStrategy {

    override fun getFlavor() = Admin()

    override fun enterSystemIntent(context: Context): Intent =
        Intent(context, MainActivity::class.java)

}