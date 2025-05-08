package com.example.truckercore.business_admin.config.flavor

import android.content.Context
import android.content.Intent
import com.example.truckercore.business_admin.view.activities.MainActivity
import com.example.truckercore.model.configs.flavor.contracts.FlavorStrategy
import com.example.truckercore.model.infrastructure.security.data.enums.Role
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomePagerData

class FlavorAdminStrategy : FlavorStrategy {

    override fun getRole() = Role.ADMIN

    override fun getFlavor() = Admin()

    override fun enterSystemIntent(context: Context): Intent =
        Intent(context, MainActivity::class.java)

    override fun getWelcomePagerData(): List<WelcomePagerData> {
        return listOf(
            WelcomePagerData.welcomeData(),
            WelcomePagerData.documentData(),
            WelcomePagerData.integrationData(),
            WelcomePagerData.inProgressData()
        )
    }

}