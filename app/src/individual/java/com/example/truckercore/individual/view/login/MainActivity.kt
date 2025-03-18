package com.example.truckercore.individual.view.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.truckercore.R
import com.example.truckercore.model.shared.utils.expressions.logError

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logError("Activity individual")
    }

}