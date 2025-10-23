package com.example.truckercore.layers.presentation.viewmodels._shared._base.managers

import com.example.truckercore.domain._shared._contracts.Effect
import com.example.truckercore.core.expressions.getClassName
import com.example.truckercore.core.util.AppLogger
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
