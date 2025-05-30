package com.example.truckercore._utils.classes.abstractions

import com.example.truckercore._utils.classes.contracts.Effect
import com.example.truckercore._utils.expressions.getClassName
import com.example.truckercore.model.logger.AppLogger
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

abstract class EffectManager<T: Effect> {

    private val _effectChannel = Channel<T>(Channel.BUFFERED)
    val effectFlow = _effectChannel.receiveAsFlow()

    protected fun trySend(effect: T) {
        val result = _effectChannel.trySend(effect)
        if (!result.isSuccess) {
            AppLogger.e(getClassName(), "Channel failed on emit effect: $effect.")
        }
    }

}
