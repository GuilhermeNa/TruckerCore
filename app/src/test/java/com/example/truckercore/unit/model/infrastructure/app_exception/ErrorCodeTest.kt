package com.example.truckercore.unit.model.infrastructure.app_exception

import com.example.truckercore._test_data_provider.TestAppExceptionProvider
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ErrorCodeTest {

    //----------------------------------------------------------------------------------------------
    // Setup
    //----------------------------------------------------------------------------------------------
    private val provider = TestAppExceptionProvider()

    //----------------------------------------------------------------------------------------------
    // Testing ErrorCode
    //----------------------------------------------------------------------------------------------
    @Test
    fun `errorCode should contain correct name`() {
        assertEquals("TEST_ERROR", provider.code.name)
    }

    @Test
    fun `errorCode should contain correct userMessage`() {
        assertEquals("Expected an user friendly message.", provider.code.userMessage)
    }

    @Test
    fun `errorCode should contain correct logMessage`() {
        val code = TestAppExceptionProvider.TestErrorCode()
        assertEquals("Expected a log message.", code.logMessage)
    }

    @Test
    fun `errorCode should be recoverable`() {
        assertTrue(provider.code.isRecoverable)
    }

}