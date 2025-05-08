package com.example.truckercore

import com.example.truckercore._test_utils.mockStaticLog
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Test {

    @BeforeEach
    fun setup() {
        mockStaticLog()
    }

    @Test
    fun test() = runTest {

    }


}
