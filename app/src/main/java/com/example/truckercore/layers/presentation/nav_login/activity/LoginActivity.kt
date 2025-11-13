package com.example.truckercore.layers.presentation.nav_login.activity

import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.truckercore.R
import com.example.truckercore.core.my_lib.expressions.isKeyboardOpen

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
       // enableEdgeToEdge()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_login) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, dest, _ ->
            if(dest.label == "fragment_forget_password") {
                WindowCompat.setDecorFitsSystemWindows(window, false)
                window.statusBarColor = Color.TRANSPARENT
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val view = currentFocus

        if (view != null && isKeyboardOpen()) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        return super.dispatchTouchEvent(ev)
    }



}