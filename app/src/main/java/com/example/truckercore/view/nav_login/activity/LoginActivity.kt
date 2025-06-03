package com.example.truckercore.view.nav_login.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.truckercore.R

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

}