package com.example.truckercore._utils.expressions

import com.example.truckercore._utils.classes.contracts.Effect
import com.example.truckercore._utils.classes.contracts.Event
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore._utils.classes.contracts.UiState

fun logState(clazz: Any, uiState: UiState) {
    AppLogger.d(clazz.getClassName(), "State: $uiState")
}

fun logEffect(clazz: Any, effect: Effect) {
    AppLogger.d(clazz.getClassName(), "Effect: $effect")
}

fun logEvent(clazz: Any, event: Event) {
    AppLogger.d(clazz.getClassName(), "Event: $event")
}