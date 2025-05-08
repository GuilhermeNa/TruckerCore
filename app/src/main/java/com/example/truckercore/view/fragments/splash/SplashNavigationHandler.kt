package com.example.truckercore.view.fragments.splash

import android.content.Context
import android.content.Intent
import androidx.navigation.NavDirections
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore.view_model.view_models.splash.SplashEffect

/**
 * Handles navigation logic for the Splash screen, based on emitted [SplashEffect]s.
 * This class centralizes navigation decisions, making the Fragment cleaner and more testable.
 */
class SplashNavigationHandler {

    /**
     * Returns the appropriate [NavDirections] based on the given [SplashEffect].
     *
     * @param effect The splash effect that represents a navigation decision.
     * @return A [NavDirections] instance for use with the Navigation Component.
     * @throws UnsupportedSplashEffectException If the effect does not map to a fragment navigation.
     */
    fun getDirection(effect: SplashEffect): NavDirections = when (effect) {
        is SplashEffect.FirstTimeAccess ->
            SplashFragmentDirections.actionSplashFragmentToWelcomeFragment()

        SplashEffect.AlreadyAccessed.RequireLogin ->
            SplashFragmentDirections.actionSplashFragmentToLoginFragment()

        SplashEffect.AlreadyAccessed.AuthenticatedUser.AwaitingRegistration ->
            SplashFragmentDirections.actionSplashFragmentToContinueRegisterFragment()

        else -> throw UnsupportedSplashEffectException(effect)
    }

    /**
     * Returns an [Intent] based on the given [SplashEffect], for use when navigating to an Activity.
     *
     * @param effect The splash effect that indicates an Activity-level navigation.
     * @param context The context used to create the intent.
     * @return An [Intent] pointing to the destination activity.
     * @throws UnsupportedSplashEffectException If the effect does not map to an activity intent.
     */
    fun getIntent(effect: SplashEffect, context: Context): Intent = when (effect) {
        is SplashEffect.Error -> {
            NotificationActivity.newInstance(
                context = context,
                errorHeader = effect.error.name,
                errorBody = effect.error.userMessage
            )
        }

        else -> throw UnsupportedSplashEffectException(effect)
    }
}


/**
 * Exception thrown when a [SplashEffect] does not correspond to any known navigation target.
 *
 * This improves error reporting compared to generic Java exceptions and provides
 * detailed information about the unsupported effect.
 *
 * @param effect The effect that was not handled by the navigation handler.
 */
private class UnsupportedSplashEffectException(effect: SplashEffect) :
    IllegalArgumentException("Unsupported navigation effect: ${effect::class.qualifiedName}")