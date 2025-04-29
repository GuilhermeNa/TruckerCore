package com.example.truckercore.view.expressions

import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.truckercore.R

/**
 * Navigates to the specified destination using a NavController.
 *
 * This function leverages the Navigation component in Android to handle navigation within the app.
 * It is typically used within a Fragment to navigate to another screen.
 *
 * @param direction The [NavDirections] object containing the navigation direction to a destination.
 */
fun Fragment.navigateTo(direction: NavDirections) {
    val navController = Navigation.findNavController(this.requireView())
    navController.navigate(direction)
}

/**
 * Animates the view to create a "pump and dump" effect, where the view scales down, expands,
 * and then fades away.
 *
 * This animation sequence first shrinks the view to 80% of its size, then expands it to 150%
 * of its size, and finally makes it disappear (scale and fade to 0).
 * This effect could be used for emphasizing or removing a UI element in a visually striking way.
 */
fun View.animPumpAndDump() {
    animate().run {
        // Reduz a escala view
        duration = 250
        scaleX(0.8F)
        scaleY(0.8F)

        withEndAction {
            // A view é expandida assim que a redução finaliza
            duration = 150
            scaleX(1.5F)
            scaleY(1.5F)

            withEndAction {
                // Por fim, a view desaparece após a expansão
                duration = 100
                scaleX(0F)
                scaleY(0F)
                alpha(0F)
            }
        }

        start()
    }
}

/**
 * Extension function for `View` that animates the view sliding in from the bottom.
 * The view becomes visible and animates using a slide-in animation from the bottom.
 * This function checks if the visibility of the view is not already `VISIBLE` before applying the animation.
 *
 * @receiver View - The view on which this function is invoked.
 */
fun View.slideInBottom() {
    // Checks if the view is not already visible before applying the animation.
    if (visibility != VISIBLE) {
        // Loads the slide-in animation from the resource.
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom)

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

fun MotionLayout.transitionOnLifecycle(
    lifecycle: Lifecycle.State,
    viewResumed: (MotionLayout) -> Unit,
    viewRestoring: (MotionLayout) -> Unit
) {
    if (lifecycle == Lifecycle.State.RESUMED) viewResumed(this)
    else viewRestoring(this)
}

fun View.setBottomMargin(bottomMargin: Int) {
    val layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.bottomMargin = bottomMargin.dp
    this.layoutParams = layoutParams
}

fun View.slideInTop() {
    // Checks if the view is not already visible before applying the animation.
    if (visibility != VISIBLE) {
        // Loads the slide-in animation from the resource.
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_top)

        // Makes the view visible.
        visibility = VISIBLE

        // Starts the animation.
        this.startAnimation(animation)
    }
}

fun View.slideOutTop(vis: Int) {
    // Checks if the view's visibility is not already the desired value.
    if (visibility != vis) {
        // Loads the slide-out animation from the resource.
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_out_top)

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