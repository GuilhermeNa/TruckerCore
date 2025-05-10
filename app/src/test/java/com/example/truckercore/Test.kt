package com.example.truckercore

import com.example.truckercore._test_utils.mockStaticLog
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Test {

    @BeforeEach
    fun setup() {
        mockStaticLog()
    }

    @Test
    fun test() = runTest {
       val valor = 0.getOrElse(
           onSuccess = { it + 1 },
           orElse = { return@runTest }
       )

        assertEquals(valor, 2)
    }


}


inline fun Int.getOrElse(
    onSuccess: (data: Int) -> Int,
    orElse: (Exception?) -> Nothing
): Int = when {
    this == 1 -> onSuccess(1)
    else -> orElse(NullPointerException())
}