package com.example.truckercore.business_admin.layers.presentation.check_in

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.truckercore.R
import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.CheckInViewModel
import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.helpers.CheckInEffect
import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.helpers.CheckInState
import com.example.truckercore.business_admin.layers.presentation.main.activity.MainActivity
import com.example.truckercore.infra.logger.AppLogger
import com.example.truckercore.layers.presentation.base.contracts.BaseNavigator
import com.example.truckercore.layers.presentation.common.LoadingDialog
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckInActivity : AppCompatActivity(), BaseNavigator {

    private val viewModel: CheckInViewModel by viewModel()

    private var dialog: LoadingDialog? = null

    //----------------------------------------------------------------------------------------------
    // On Create
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_in)
        enableEdgeToEdge()
        initializeDialog()
        setActivityManagers()
        initializeViewModel(savedInstanceState)
    }

    private fun initializeViewModel(sis: Bundle?) {
        if (sis == null) {
            viewModel.initialize()
        }
    }

    private fun initializeDialog() {
        dialog = LoadingDialog(this)
    }

    private fun setActivityManagers() {
        lifecycleScope.launch {
            launch { setStateManager() }
            setEffectManager()
        }
    }

    private suspend fun setStateManager() {
        viewModel.stateFlow.collect { state ->
            AppLogger.d("Activity(State): ", "$state")
            when (state) {
                CheckInState.Idle -> dialog?.dismiss()
                CheckInState.Loading -> dialog?.show()
            }
        }
    }

    private suspend fun setEffectManager() {
        viewModel.effectFlow.collect { effect ->
            AppLogger.d("Activity(State): ", "$effect")
            when (effect) {
                CheckInEffect.Navigation.ToMain -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                CheckInEffect.Navigation.ToNoConnection -> {
                    navigateToNoConnection(this, viewModel::retry)
                }

                CheckInEffect.Navigation.ToError -> {
                    navigateToErrorActivity(this)
                }

                else -> {
                    throw NotImplementedError("CheckInActivity not implemented effect: $effect")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog?.dismiss()
        dialog = null
    }

}