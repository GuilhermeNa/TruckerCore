package com.example.truckercore.unit.model.infrastructure.app_exception

import com.example.truckercore._test_data_provider.TestAppExceptionProvider
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AppExceptionOldTest {

    //----------------------------------------------------------------------------------------------
    // Setup
    //----------------------------------------------------------------------------------------------
    private val provider = TestAppExceptionProvider()

    //----------------------------------------------------------------------------------------------
    // Testing App Exception
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should contain the expected message`() {
        assertEquals(provider.code.logMessage, provider.appException.message)
    }

    @Test
    fun `should contain the expected cause`() {
        assertEquals(provider.cause, provider.appException.cause)
    }

    @Test
    fun `should contain the expected errorCode`() {
        assertEquals(provider.code, provider.appException.errorCode)
    }

}
