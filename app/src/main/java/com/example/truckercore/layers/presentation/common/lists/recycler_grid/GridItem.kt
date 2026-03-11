package com.example.truckercore.layers.presentation.common.lists.recycler_grid

import androidx.navigation.NavDestination

data class GridItem(
    val iconResource: Int,
    val text: String,
    val destination: NavDestination? = null
)
