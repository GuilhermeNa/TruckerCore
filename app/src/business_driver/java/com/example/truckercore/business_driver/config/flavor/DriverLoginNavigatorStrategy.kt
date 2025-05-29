package com.example.truckercore.business_driver.config.flavor

import android.content.Context
import android.content.Intent
import androidx.navigation.NavController
import com.example.truckercore.R
import com.example.truckercore.business_driver.view.activities.MainActivity
import com.example.truckercore.view.fragments.login.navigator.LoginNavigatorStrategy
import java.security.InvalidParameterException

class DriverLoginNavigatorStrategy(private val navController: NavController):
    LoginNavigatorStrategy {

    override fun navigateToEmailAuth() {
        throw InvalidParameterException()
    }

    override fun navigateToForgetPassword() {
        navController.navigate(R.id.action_loginFragment2_to_forgetPasswordFragment2)
    }

    override fun getMainActivityIntent(context: Context): Intent =
        Intent(context, MainActivity::class.java)

}