package com.example.truckercore.layers.presentation.base.abstractions.view_model

import androidx.lifecycle.ViewModel
import com.example.truckercore.core.my_lib.expressions.getTag
import com.example.truckercore.infra.logger.AppLogger

abstract class BaseViewModel : ViewModel() {

    init {
        AppLogger.d(getTag, "ViewModel initialized")
    }

}