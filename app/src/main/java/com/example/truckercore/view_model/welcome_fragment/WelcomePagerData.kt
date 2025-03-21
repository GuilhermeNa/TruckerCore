package com.example.truckercore.view_model.welcome_fragment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WelcomePagerData(
    val res: Int,
    val title: String,
    val message: String
) : Parcelable