package com.example.truckercore.business_admin.config.flavor

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.navigation.NavController
import com.example.truckercore.business_admin.view.activities.MainActivity
import com.example.truckercore.model.configs.flavor.contracts.FlavorStrategy
import com.example.truckercore.model.infrastructure.security.data.enums.Role
import com.example.truckercore.view.nav_login.fragments.welcome.navigator.WelcomeNavigatorStrategy
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomePagerData

class FlavorAdminStrategy : FlavorStrategy {

    override fun getWelcomeNavigatorStrategy(): WelcomeNavigatorStrategy {
        return AdminWelcomeNavigatorStrategy()
    }

    override fun getLoginNavigatorStrategy(navController: NavController) =
        AdminLoginNavigatorStrategy(navController)

    override fun getRole() = Role.ADMIN

    override fun getFlavor() = Admin()

    override fun navigateToMain(current: Activity) {
        val intent = Intent(current, MainActivity::class.java)
        current.startActivity(intent)
        current.finish()
    }


    override fun getWelcomePagerData(): List<WelcomePagerData> {
        return listOf(
            WelcomePagerData.welcomeData(),
            WelcomePagerData.documentData(),
            WelcomePagerData.integrationData(),
            WelcomePagerData.inProgressData()
        )
    }

}