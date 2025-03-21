package com.example.truckercore.view.expressions

import android.view.View
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.truckercore.R

fun View.navigateTo(direction: NavDirections) {
    val navController = Navigation.findNavController(this)
    navController.navigate(direction)
}

fun Fragment.navigateTo(direction: NavDirections) {
    val navController = Navigation.findNavController(this.requireView())
    navController.navigate(direction)
}

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

fun View.slideInBottom() {
    val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom)
    if (visibility != VISIBLE) visibility = VISIBLE
    this.startAnimation(animation)
}

fun View.slideOutBottom(vis: Int) {
    val animation = AnimationUtils.loadAnimation(context, R.anim.slide_out_bottom)
    if (visibility != vis) visibility = vis
    this.startAnimation(animation)
}