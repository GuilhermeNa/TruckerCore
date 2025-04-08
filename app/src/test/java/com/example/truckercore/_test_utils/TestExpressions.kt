package com.example.truckercore._test_utils

import android.text.TextUtils
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic

fun mockStaticLog() {
    mockkStatic(Log::class)
    every { Log.w(any<String>(), any<String>()) } returns 0
    every { Log.w(any<String>(), any<Throwable>()) } returns 0
    every { Log.e(any(), any()) } returns 0
    every { Log.d(any(), any()) } returns 0
}

fun mockStaticTask() {
    mockkStatic("kotlinx.coroutines.tasks.TasksKt")
}

fun mockStaticTextUtil() {
    mockkStatic(TextUtils::class)
    every { TextUtils.isEmpty(any()) } answers {
        val input = arg<String?>(0)
        input?.isEmpty() ?: true
    }
}

fun mockTask() = mockk<Task<Void>> {
    every { exception } returns null
    every { isSuccessful } returns true
    every { isComplete } returns true
    every { isCanceled } returns false
    every { result } returns mockk()
}