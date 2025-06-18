package com.example.truckercore.view_model._shared._base.managers

import com.example.truckercore._shared.expressions.getClassName
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.view_model._shared._contracts.Effect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class EffectManagerII<T: Effect> {

    private val _effectChannel = Channel<T>(Channel.BUFFERED)
    val effectFlow = _effectChannel.receiveAsFlow()

    fun trySend(effect: T) {
        val result = _effectChannel.trySend(effect)
        if (!result.isSuccess) {
            AppLogger.e(getClassName(), "Channel failed on emit effect: $effect.")
        }
    }

}