package com.example.truckercore.layers.presentation.viewmodels.base._base.managers


import com.example.truckercore.core.my_lib.expressions.getTag
import com.example.truckercore.infra.logger.AppLogger
import com.example.truckercore.layers.presentation.viewmodels.base._contracts.Effect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class EffectManager<T: Effect> {

    private val _effectChannel = Channel<T>(Channel.BUFFERED)
    val effectFlow = _effectChannel.receiveAsFlow()

    fun trySend(effect: T) {
        val result = _effectChannel.trySend(effect)
        if (!result.isSuccess) {
            AppLogger.e(getTag, "Channel failed on emit effect: $effect.")
        }
    }

}