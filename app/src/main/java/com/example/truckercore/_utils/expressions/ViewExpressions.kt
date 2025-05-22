package com.example.truckercore._utils.expressions

import android.content.Context
import android.os.Build
import android.view.View
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import com.example.truckercore.R
import com.google.android.material.textfield.TextInputEditText

/**
 * Extension function for `View` that animates the view sliding in from the bottom.
 * The view becomes visible and animates using a slide-in animation from the bottom.
 * This function checks if the visibility of the view is not already `VISIBLE` before applying the animation.
 *
 * @receiver View - The view on which this function is invoked.
 */
fun View.slideInBottom(duration: Long? = null) {
    // Checks if the view is not already visible before applying the animation.
    if (visibility != VISIBLE) {
        // Loads the slide-in animation from the resource.
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom)
        duration?.let { animation.duration = duration }

        // Makes the view visible.
        visibility = VISIBLE

        // Starts the animation.
        this.startAnimation(animation)
    }
}

/**
 * Extension function for `View` that animates the view sliding out to the bottom.
 * The view becomes either `INVISIBLE` or `GONE` depending on the `vis` parameter and animates with a slide-out effect.
 * This function checks if the view's visibility is not already the provided visibility value before applying the animation.
 *
 * @receiver View - The view on which this function is invoked.
 * @param vis Int - The desired visibility state after the animation (either `INVISIBLE` or `GONE`).
 */
fun View.slideOutBottom(vis: Int, duration: Long? = null) {
    // Checks if the view's visibility is not already the desired value.
    if (visibility != vis) {
        // Loads the slide-out animation from the resource.
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_out_bottom)
        duration?.let { animation.duration = duration }

        // Sets the view's visibility to the provided value (INVISIBLE or GONE).
        visibility = vis

        // Starts the animation.
        this.startAnimation(animation)
    }
}

fun List<View>.clearFocusIfNeeded() {
    this.forEach { v ->
        if (v.hasFocus()) v.clearFocus()
    }
}

fun ImageView.loadGif(url: Any? = null, context: Context) {
    fun getGifLoader(context: Context): ImageLoader {
        val imageLoaderGifSupport = ImageLoader.Builder(context)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }.build()
        return imageLoaderGifSupport
    }

    val imageLoaderGifSupport = getGifLoader(context)
    load(url, imageLoaderGifSupport)
}