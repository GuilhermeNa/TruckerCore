package com.example.truckercore._shared.expressions

import com.example.truckercore.view_model._shared._contracts.Effect
import com.example.truckercore.view_model._shared._contracts.Event
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.view_model._shared._contracts.State

fun logState(clazz: Any, uiState: State) {
    AppLogger.d(clazz.getClassName(), "State: $uiState")
}

fun logEffect(clazz: Any, effect: Effect) {
    AppLogger.d(clazz.getClassName(), "Effect: $effect")
}

fun logEvent(clazz: Any, event: Event) {
    AppLogger.d(clazz.getClassName(), "Event: $event")
}