package com.example.truckercore.view.expressions

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

fun View.navigateTo(direction: NavDirections) {
    val navController = Navigation.findNavController(this)
    navController.navigate(direction)
}