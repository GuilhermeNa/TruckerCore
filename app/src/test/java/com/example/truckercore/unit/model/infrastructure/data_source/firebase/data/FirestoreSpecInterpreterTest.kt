package com.example.truckercore.unit.model.infrastructure.data_source.firebase.data

import com.example.truckercore._test_data_provider.TestFirestoreDataProvider
import com.example.truckercore._test_data_provider.TestSpecificationProvider
import com.example.truckercore.model.infrastructure.data_source.firebase.data.FirestoreSpecInterpreter
import com.example.truckercore.model.infrastructure.integration.data.for_app.data.exceptions.SpecificationException
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

class FirestoreSpecInterpreterTest : KoinTest {

    //----------------------------------------------------------------------------------------------
    // Setup
    //----------------------------------------------------------------------------------------------
    private val firestore: FirebaseFirestore by inject()
    private val interpreter: FirestoreSpecInterpreter by inject()

    private val firestoreProvider = TestFirestoreDataProvider()
    private val specProvider = TestSpecificationProvider()

    @BeforeEach
    fun setup() {
        startKoin {
            modules(
                module {
                    single<FirebaseFirestore> { mockk(relaxed = true) }
                    single { FirestoreSpecInterpreter(get()) }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()

    //----------------------------------------------------------------------------------------------
    // Testing interpretIdSearch
    //----------------------------------------------------------------------------------------------
    @Test
    fun `interpretIdSearch should return correct DocumentReference for valid specification`() {
        // Arrange
        val docRef = firestoreProvider.docReference()
        val spec = specProvider.specIDSearch()

        every { firestore.collection(any()).document(any()) } returns docRef

        // Act
        val result = interpreter.interpretIdSearch(spec)

        // Assert
        assertEquals(docRef, result)
        verify(exactly = 1) { firestore.collection(any()).document(any()) }
    }

    //----------------------------------------------------------------------------------------------
    // Testing interpretFilterSearch
    //----------------------------------------------------------------------------------------------
    @Test
    fun `interpretFilterSearch should throw SpecificationException when filter is unsupported`() {
        // Arrange
        val unsupportedSpec = specProvider.unsupportedSpecMock()

        // Act & Assert
        assertThrows<SpecificationException> {
            interpreter.interpretFilterSearch(unsupportedSpec)
        }
    }

}