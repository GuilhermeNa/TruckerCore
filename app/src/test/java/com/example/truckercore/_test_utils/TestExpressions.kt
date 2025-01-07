package com.example.truckercore._test_utils

import android.util.Log
import io.mockk.every
import io.mockk.mockkStatic

fun mockStaticLog() {
    mockkStatic(Log::class)
    every { Log.e(any(), any()) } returns 0
    every { Log.d(any(), any()) } returns 0
}

fun mockStaticTask() {
    mockkStatic("kotlinx.coroutines.tasks.TasksKt")
}

