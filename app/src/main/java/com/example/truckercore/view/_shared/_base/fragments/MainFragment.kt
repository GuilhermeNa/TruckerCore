package com.example.truckercore.view._shared._base.fragments

import android.os.Bundle
import com.example.truckercore._shared.expressions.navigateToActivity
import com.example.truckercore.view._shared.views.activities.NotificationActivity
import com.example.truckercore.view_model._shared._base.view_model.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class MainFragment : LoggerFragment() {

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