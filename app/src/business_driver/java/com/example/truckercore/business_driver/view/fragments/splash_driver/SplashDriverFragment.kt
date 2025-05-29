package com.example.truckercore.business_driver.view.fragments.splash_driver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore.R
import com.example.truckercore._utils.expressions.logEffect
import com.example.truckercore._utils.expressions.navController
import com.example.truckercore._utils.expressions.navigateToActivity
import com.example.truckercore.business_driver.view.fragments.splash_driver.navigator.SplashDriverNavigator
import com.example.truckercore.business_driver.view_model.view_models.splash_driver.SplashDriverEffect
import com.example.truckercore.business_driver.view_model.view_models.splash_driver.SplashDriverViewModel
import com.example.truckercore.view.fragments._base.CloseAppFragment
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SplashDriverFragment : CloseAppFragment() {

    private val navigator: SplashDriverNavigator by lazy {
        getKoin().get<SplashDriverNavigator> { parametersOf(navController()) }
    }

    private val viewModel: SplashDriverViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initialize()
        setEffectManager()
    }

    private fun setEffectManager() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effectFlow.collect { effect ->
                    logEffect(this@SplashDriverFragment, effect)
                    handleEffect(effect)
                }
            }
        }
    }

    private fun handleEffect(effect: SplashDriverEffect) {
        when (effect) {
            SplashDriverEffect.NavigateToLogin -> {
                navigator.navigateToLogin()
            }

            SplashDriverEffect.NavigateToWelcome -> {
                navigator.navigateToWelcome()
            }

            SplashDriverEffect.NavigateToNotification -> {
                val intent = navigator.notificationActivityIntent(requireContext())
                navigateToActivity(intent, true)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_driver, container, false)
    }

}