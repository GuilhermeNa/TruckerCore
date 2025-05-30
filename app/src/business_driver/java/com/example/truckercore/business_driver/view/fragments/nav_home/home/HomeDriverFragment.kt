package com.example.truckercore.business_driver.view.fragments.nav_home.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.truckercore.R
import com.example.truckercore.view.fragments._base.CloseAppFragment

class HomeDriverFragment : CloseAppFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home_driver, container, false)
    }

}