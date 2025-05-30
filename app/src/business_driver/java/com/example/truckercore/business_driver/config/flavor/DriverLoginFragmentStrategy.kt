package com.example.truckercore.business_driver.config.flavor

import android.content.Context
import android.content.Intent
import androidx.navigation.NavController
import com.example.truckercore.R
import com.example.truckercore._utils.classes.ui_component.ButtonComponent
import com.example.truckercore._utils.classes.ui_component.Visibility
import com.example.truckercore.business_driver.view.activities.MainActivity
import com.example.truckercore.view.fragments.login.navigator.LoginFragmentStrategy
import java.security.InvalidParameterException

class DriverLoginFragmentStrategy(private val navController: NavController) :
    LoginFragmentStrategy {

    override fun navigateToEmailAuth() {
        throw InvalidParameterException()
    }

    override fun navigateToForgetPassword() {
        navController.navigate(R.id.action_loginFragment2_to_forgetPasswordFragment2)
    }

    override fun getMainActivityIntent(context: Context): Intent =
        Intent(context, MainActivity::class.java)

    override fun getNewAccountButtonComponent(): ButtonComponent {
        return ButtonComponent(isEnabled = false, visibility = Visibility.GONE)
    }

}