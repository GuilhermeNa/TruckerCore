package com.example.truckercore.business_driver.config.flavor

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.truckercore.R
import com.example.truckercore._utils.classes.ui_component.ButtonComponent
import com.example.truckercore._utils.classes.ui_component.Visibility
import com.example.truckercore._utils.expressions.navigateToActivity
import com.example.truckercore.business_driver.view.activities.MainActivity
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore.view.fragments.login.navigator.LoginFragmentStrategy
import com.example.truckercore.view.ui_error.UiError
import java.lang.ref.WeakReference
import java.security.InvalidParameterException

class DriverLoginFragmentStrategy(private val navController: NavController) :
    LoginFragmentStrategy {

    override fun navigateToEmailAuth() {
        throw InvalidParameterException()
    }

    override fun navigateToForgetPassword() {
        navController.navigate(R.id.action_loginFragment2_to_forgetPasswordFragment2)
    }

    override fun navigateToMain(nActivity: WeakReference<Activity>) {
        nActivity.get()?.let { activity ->
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        } ?: throw IllegalArgumentException("Activity was not set or already collected.")
    }

    override fun getNewAccountButtonComponent(): ButtonComponent {
        return ButtonComponent(isEnabled = false, visibility = Visibility.GONE)
    }

}