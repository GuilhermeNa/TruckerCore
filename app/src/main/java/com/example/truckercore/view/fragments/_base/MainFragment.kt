package com.example.truckercore.view.fragments._base

import android.os.Bundle
import com.example.truckercore._utils.expressions.navigateToActivity
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore.view_model._base.MainViewModel
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