package com.example.truckercore.business_admin.layers.presentation.check_in.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.truckercore.R
import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.CheckInViewModel
import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.helpers.CheckInEffect
import com.example.truckercore.business_admin.layers.presentation.check_in.view_model.helpers.CheckInState
import com.example.truckercore.layers.presentation.base.contracts.BaseNavigator
import com.example.truckercore.layers.presentation.common.LoadingDialog
import kotlinx.coroutines.launch

class CheckInActivity : AppCompatActivity(), BaseNavigator {

    private val viewModel: CheckInViewModel by viewModels()

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
            when(state) {
                CheckInState.Idle -> dialog?.dismiss()
                CheckInState.Loading -> dialog?.show()
            }
        }
    }

    private suspend fun setEffectManager() {
        viewModel.effectFlow.collect { effect ->
            when(effect) {
                CheckInEffect.Navigation.ToMain -> {
                    TODO()
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