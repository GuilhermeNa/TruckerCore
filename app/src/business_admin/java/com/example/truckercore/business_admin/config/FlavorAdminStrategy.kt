package com.example.truckercore.business_admin.config

import android.app.Activity
import android.content.Intent
import com.example.truckercore.business_admin.layers.presentation.check_in.view.CheckInActivity
import com.example.truckercore.core.config.flavor.FlavorStrategy
import com.example.truckercore.layers.domain.model.access.Role
import com.example.truckercore.layers.presentation.login.view_model.welcome_fragment.helpers.WelcomePagerData

class FlavorAdminStrategy : FlavorStrategy {

    override fun navigateToCheckIn(current: Activity) {
        val intent = Intent(current, CheckInActivity::class.java)
        current.startActivity(intent)
        current.finish()
    }

    override fun getRole() = Role.ADMIN

    override fun getFlavor() = Admin()

    override fun getWelcomePagerData(): List<WelcomePagerData> {
        return listOf(
            WelcomePagerData.welcomeData(),
            WelcomePagerData.documentData(),
            WelcomePagerData.integrationData(),
            WelcomePagerData.inProgressData()
        )
    }

}