package com.example.truckercore.unit.model.shared.utils.expressions

import android.util.Log
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.configs.app_constants.Tag
import com.example.truckercore.shared.utils.expressions.logError
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LogFunsTest {

    @BeforeEach
    fun setup() {
        mockStaticLog()
    }

    @Test
    fun `should log the received message in Tag ERROR`() {
        // Arrange
        val message = "Log test message."

        // Act
        logError(message)

        // Assertions
        verify { Log.e(Tag.ERROR.getName(), message) }
    }

}