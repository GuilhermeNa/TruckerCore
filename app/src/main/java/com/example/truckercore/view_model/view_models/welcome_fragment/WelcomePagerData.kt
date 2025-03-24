package com.example.truckercore.view_model.view_models.welcome_fragment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data class representing the information for each page in the WelcomePager.
 * The class is annotated with @Parcelize to make it Parcelable, which allows instances of this class to be passed
 * between Android components via Intents or Bundles.
 *
 * @property res An integer representing the resource ID of an image or animation that will be displayed on the page.
 * @property title The title text displayed on the page.
 * @property message The message text displayed on the page.
 */
@Parcelize
data class WelcomePagerData(
    /**
     * The resource ID of the image or animation associated with this page.
     * It can refer to drawable resources (e.g., `R.drawable.some_image`).
     */
    val res: Int,

    /**
     * The title text to be displayed at the top or in a prominent position on the page.
     */
    val title: String,

    /**
     * The message text to be displayed on the page, providing additional information or context.
     */
    val message: String
) : Parcelable