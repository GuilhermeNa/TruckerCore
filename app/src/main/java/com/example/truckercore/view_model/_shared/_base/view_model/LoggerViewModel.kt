package com.example.truckercore.view_model._shared._base.view_model

import androidx.lifecycle.ViewModel
import com.example.truckercore._shared.expressions.getClassName
import com.example.truckercore.model.logger.AppLogger

abstract class LoggerViewModel: ViewModel() {

    init {
        AppLogger.d(getClassName(), VM_INITIALIZED)
    }

    companion object {
        private const val VM_INITIALIZED = "ViewModel initialized"
    }

}