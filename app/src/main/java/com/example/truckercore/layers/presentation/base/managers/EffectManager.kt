package com.example.truckercore.layers.presentation.base.managers

import com.example.truckercore.core.my_lib.expressions.getTag
import com.example.truckercore.infra.logger.AppLogger
import com.example.truckercore.layers.presentation.base.contracts.Effect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Manages one-time UI effects using a [Channel].
 *
 * An [EffectManager] is designed for events that should be handled
 * only once, such as:
 * - Navigation events
 * - Toast or Snackbar messages
 * - Dialog triggers
 *
 * @param T The type of [Effect] being emitted.
 */
class EffectManager<T : Effect> {

    /**
     * Buffered channel used to emit one-time effects.
     */
    private val _effectChannel = Channel<T>(Channel.BUFFERED)

    /**
     * Flow representation of the effect channel.
     * Allows effects to be collected in a lifecycle-aware way.
     */
    val effectFlow = _effectChannel.receiveAsFlow()

    /**
     * Attempts to emit an effect without suspending.
     *
     * If the channel fails to accept the effect, an error
     * is logged for debugging purposes.
     *
     * @param effect The effect to be emitted.
     */
    fun trySend(effect: T) {
        val result = _effectChannel.trySend(effect)

        if (!result.isSuccess) {
            AppLogger.e(getTag, "Channel failed to emit effect: $effect.")
        }
    }

}