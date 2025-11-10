package com.example.truckercore.core.my_lib.expressions

import com.example.truckercore.infra.logger.AppLogger
import com.example.truckercore.layers.presentation.viewmodels.base._contracts.Effect
import com.example.truckercore.layers.presentation.viewmodels.base._contracts.Event
import com.example.truckercore.layers.presentation.viewmodels.base._contracts.State

fun logState(clazz: Any, uiState: State) {
    AppLogger.d(clazz.getClassName(), "State: $uiState")
}

fun logEffect(clazz: Any, effect: Effect) {
    AppLogger.d(clazz.getClassName(), "Effect: $effect")
}

fun logEvent(clazz: Any, event: Event) {
    AppLogger.d(clazz.getClassName(), "Event: $event")
}