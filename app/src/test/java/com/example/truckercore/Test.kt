package com.example.truckercore

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.shared.errors.InvalidResponseException
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class Test {

    private lateinit var docParams: DocumentParameters

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
        }
    }

    @Test
    fun testar() {
        CoroutineScope(Dispatchers.Unconfined).launch {
            try {
                failedConcurrentSum()
            } catch (e: Exception) {
                println("Computation failed with ArithmeticException")
            }
        }
    }

    private suspend fun failedConcurrentSum(): Int = coroutineScope {
        val one = async<Int> {
                delay(Long.MAX_VALUE) // Emulates very long computation
                42
        }
        val two = async<Int> {
            println("Second child throws an exception")
            throw ArithmeticException()
        }
        one.await() + two.await()
    }
}
