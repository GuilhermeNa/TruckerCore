package com.example.truckercore.layers.domain.base.contracts

interface Optional<T> {

    fun isFilled(): Boolean

    fun completeRegistration(data: T) {
        if (isFilled()) throw IllegalStateException(ALREADY_FILLED)
    }

    private companion object {
        private const val ALREADY_FILLED = "Optional fields have already been completed."
    }

}