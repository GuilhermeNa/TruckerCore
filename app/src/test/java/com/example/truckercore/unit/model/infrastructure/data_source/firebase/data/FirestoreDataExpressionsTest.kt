package com.example.truckercore.unit.model.infrastructure.data_source.firebase.data

import com.example.truckercore.model.infrastructure.data_source.firebase.data.safeInterpretOrEmit
import com.example.truckercore.model.infrastructure.integration.data.for_api.exceptions.UnknownException
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@OptIn(ExperimentalCoroutinesApi::class)
class FirestoreDataExpressionsTest {

    //----------------------------------------------------------------------------------------------
    // Testing safeInterpretOrEmit
    //----------------------------------------------------------------------------------------------
    @Test
    fun `safeInterpretOrEmit should return result when block succeeds`() = runTest {
        val flow = callbackFlow<String> {
            safeInterpretOrEmit(
                block = { "Success" },
                error = { UnknownException() }
            )
            awaitClose {  }
        }

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)){
            flow.collect { result ->
                assertEquals("Success", result)
            }
        }

    }

    @Test
    fun `safeInterpretOrEmit should close flow with error when block throws`() = runTest {
        val flow = callbackFlow<Unit> {
            safeInterpretOrEmit(
                block = { throw NullPointerException() },
                error = { UnknownException() }
            )
        }
        assertThrows<UnknownException> { flow.collect { } }
    }

}