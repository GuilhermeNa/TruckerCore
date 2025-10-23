package com.example.truckercore.layers.presentation._shared._base.fragments

import android.os.Bundle
import com.example.truckercore.core.expressions.navigateToActivity
import com.example.truckercore.presentation._shared.views.activities.NotificationActivity
import com.example.truckercore.domain._shared._base.view_model.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class MainFragment : BaseFragment() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkUserStillLogged()
    }

    private fun checkUserStillLogged() {
        if (mainViewModel.hasLoggedUser()) return

        val intent = NotificationActivity.newInstance(
            context = requireContext(),
            title = ERROR_TITLE,
            message = ERROR_MESSAGE
        )
        navigateToActivity(intent, true)
    }

    companion object {
        private const val ERROR_TITLE = "Falha de autenticação"
        private const val ERROR_MESSAGE = "Reinicie o aplicativo e faça o login novamente"
    }

}