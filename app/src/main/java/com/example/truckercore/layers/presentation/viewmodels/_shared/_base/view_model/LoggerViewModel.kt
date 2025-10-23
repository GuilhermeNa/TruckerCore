package com.example.truckercore.layers.presentation.viewmodels._shared._base.view_model

import androidx.lifecycle.ViewModel
import com.example.truckercore.core.expressions.getClassName
import com.example.truckercore.core.util.AppLogger

abstract class LoggerViewModel: ViewModel() {

    init {
        AppLogger.d(getClassName(),
            com.example.truckercore.presentation.viewmodels._shared._base.view_model.LoggerViewModel.Companion.VM_INITIALIZED
        )
    }

    companion object {
        private const val VM_INITIALIZED = "ViewModel initialized"
    }

}