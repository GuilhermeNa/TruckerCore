package com.example.truckercore.business_driver.view_model.view_models.splash_driver

import com.example.truckercore._utils.expressions.launch
import com.example.truckercore._utils.expressions.logEvent
import com.example.truckercore.model.infrastructure.integration.preferences.PreferencesRepository
import com.example.truckercore.view_model._base.LoggerViewModel

class SplashDriverViewModel(private val preferences: PreferencesRepository) : LoggerViewModel() {

    private val effectManager = SplashDriverEffectManager()
    val effectFlow = effectManager.effectFlow

    fun initialize() {
        onEvent(SplashDriverEvent.Initialize)
    }

    private fun onEvent(event: SplashDriverEvent) {
        logEvent(this@SplashDriverViewModel, event)

        when (event) {
            SplashDriverEvent.Initialize -> launch {
                val newEvent = loadUserInfo()
                onEvent(newEvent)
            }

            is SplashDriverEvent.LoadTaskConcluded.Error -> effectManager.setNavigateToNotificationEffect()
            SplashDriverEvent.LoadTaskConcluded.FirstAccess -> effectManager.setNavigateToWelcomeEffect()
            SplashDriverEvent.LoadTaskConcluded.NotFirstAccess -> effectManager.setNavigateToLoginEffect()
        }
    }

    private suspend fun loadUserInfo(): SplashDriverEvent.LoadTaskConcluded = try {
        if (preferences.isFirstAccess()) SplashDriverEvent.LoadTaskConcluded.FirstAccess
        else SplashDriverEvent.LoadTaskConcluded.NotFirstAccess
    } catch (e: Exception) {
        SplashDriverEvent.LoadTaskConcluded.Error(e)
    }

}