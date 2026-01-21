package com.example.truckercore.layers.presentation.login.view.activity

import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.truckercore.R
import com.example.truckercore.core.my_lib.expressions.isKeyboardOpen

/**
 * LoginActivity is responsible for hosting the login flow of the application.
 *
 * This activity uses a NavHostFragment to manage navigation between login-related
 * fragments (such as login and forgot password).
 *
 * Behavior highlights:
 * - Listens for navigation destination changes to customize the system UI
 *   (status bar appearance) when the "forgot password" screen is displayed.
 * - Automatically hides the soft keyboard when the user touches outside
 *   of an input field.
 */
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        enableEdgeToEdge()

        // Retrieves the NavHostFragment responsible for handling navigation
   /*     val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_login) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, dest, _ ->
            if (dest.label == "fragment_forget_password") {

                // Allows the layout to extend into the system window areas
                WindowCompat.setDecorFitsSystemWindows(window, false)

                // Makes the status bar transparent
                window.statusBarColor = Color.TRANSPARENT

                // Sets the status bar icons to a dark appearance (light background)
                WindowInsetsControllerCompat(
                    window,
                    window.decorView
                ).isAppearanceLightStatusBars = true
            }
        }*/



    }

    /**
     * Dispatches touch events to the activity.
     *
     * If the soft keyboard is open and the user touches anywhere outside
     * the currently focused view, the keyboard will be dismissed.
     *
     * @param ev The touch event being dispatched.
     * @return True if the event was handled, otherwise the result of the
     * superclass implementation.
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val view = currentFocus

        if (view != null && isKeyboardOpen()) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        return super.dispatchTouchEvent(ev)
    }

}