package com.example.truckercore.view_model._base

import androidx.lifecycle.ViewModel
import com.example.truckercore._utils.expressions.getName
import com.example.truckercore.model.logger.AppLogger

abstract class BaseViewModel: ViewModel() {

    init {
        AppLogger.d(getName(), VM_INITIALIZED)
    }

    companion object {
        private const val VM_INITIALIZED = "ViewModel initialized"
    }

}