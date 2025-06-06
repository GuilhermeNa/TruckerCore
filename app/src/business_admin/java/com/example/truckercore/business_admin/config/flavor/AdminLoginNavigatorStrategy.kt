package com.example.truckercore.business_admin.config.flavor

import android.app.Activity
import android.content.Intent
import androidx.navigation.NavController
import com.example.truckercore.view_model._shared.components.ButtonComponent
import com.example.truckercore.view_model._shared.components.Visibility
import com.example.truckercore.business_admin.view.activities.MainActivity
import com.example.truckercore.view.nav_login.fragments.login.LoginFragmentDirections
import com.example.truckercore.view.nav_login.fragments.login.navigator.LoginFragmentStrategy
import java.lang.ref.WeakReference

class AdminLoginNavigatorStrategy(private val navController: NavController) :
    LoginFragmentStrategy {

    override fun navigateToEmailAuth() {
        val direction = LoginFragmentDirections.actionGlobalEmailAuthFragment()
        navController.navigate(direction)
    }

    override fun navigateToForgetPassword() {
        val direction = LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment()
        navController.navigate(direction)
    }

    override fun navigateToMain(nActivity: WeakReference<Activity>) {
        nActivity.get()?.let { activity ->
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        } ?: throw throw IllegalArgumentException("Activity was not set or already collected.")
    }

    override fun getNewAccountButtonComponent(): ButtonComponent {
        return ButtonComponent(isEnabled = true, visibility = Visibility.VISIBLE)
    }

}