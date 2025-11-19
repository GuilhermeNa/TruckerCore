package com.example.truckercore.layers.presentation.base.abstractions.view_model

import androidx.lifecycle.ViewModel
import com.example.truckercore.core.my_lib.expressions.getClassName
import com.example.truckercore.infra.logger.AppLogger

abstract class BaseViewModel: ViewModel() {

    init {
        AppLogger.d(getClassName(), VM_INITIALIZED)
    }

    companion object {
        private const val VM_INITIALIZED = "ViewModel initialized"
    }

}