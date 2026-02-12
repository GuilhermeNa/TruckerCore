package com.example.truckercore.layers.presentation.login.view.activity

import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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