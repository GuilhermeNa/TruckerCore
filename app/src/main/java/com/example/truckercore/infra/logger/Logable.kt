package com.example.truckercore.infra.logger

import android.util.Log
import com.example.truckercore.layers.data.base.outcome.DataOutcome
import com.example.truckercore.layers.data.base.outcome.OperationOutcome
import com.example.truckercore.layers.data.base.outcome.Outcome

interface Logable {

    val tag: String
        get() = "AppLogger"

    fun <T> Outcome<T>.logFailure(message: String) {
        if (this is DataOutcome.Failure) {
            Log.e(tag, "$message\n${exception.stackTraceToString()}")
        }

        if (this is OperationOutcome.Failure) {
            Log.e(tag, "$message\n${exception.stackTraceToString()}")
        }

    }

    fun Throwable.logFailure(message: String) {
        Log.e(tag, "$message\n${stackTraceToString()}")
    }

}