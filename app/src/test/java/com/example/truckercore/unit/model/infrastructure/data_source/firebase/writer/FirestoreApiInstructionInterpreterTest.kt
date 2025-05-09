/*
package com.example.truckercore.unit.model.infrastructure.data_source.firebase.writer

import com.example.truckercore._test_data_provider.fake_objects.FakeDto
import com.example.truckercore._test_data_provider.fake_objects.FakeInstruction
import com.example.truckercore.model.configs.collections.Collection
import com.example.truckercore.model.infrastructure.data_source.firebase.writer.FirestoreInstructionInterpreter
import com.example.truckercore.model.infrastructure.data_source.firebase.writer.api_instructions.FirebaseDelete
import com.example.truckercore.model.infrastructure.data_source.firebase.writer.api_instructions.FirebaseSet
import com.example.truckercore.model.infrastructure.data_source.firebase.writer.api_instructions.FirebaseUpdate
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.instructions.Put
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.instructions.PutLazy
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.instructions.Remove
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.instructions.Update
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.data.instructions.UpdateFields
import com.example.truckercore.model.shared.value_classes.GenericID
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.every
import io.mockk.mockk
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
import kotlin.test.assertTrue

class FirestoreApiInstructionInterpreterTest : KoinTest {

    //----------------------------------------------------------------------------------------------
    // Setup
    //----------------------------------------------------------------------------------------------
    private val firestore: FirebaseFirestore by inject()
    private val interpreter: FirestoreInstructionInterpreter by inject()

    @BeforeEach
    fun setup() {
        startKoin {
            modules(module {
                single<FirebaseFirestore> { mockk(relaxed = true) }
                single<FirestoreInstructionInterpreter> { FirestoreInstructionInterpreter(get()) }
            })
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()

    //----------------------------------------------------------------------------------------------
    // Testing
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should return a firebase set instruction`() {
        // Arrange
        val putInst = Put(
            InstructionTag("tag1"), Collection.FAKE, FakeDto()
        )
        val deque = ArrayDeque(listOf(putInst))

        val docId = "docId"
        val document = mockk<DocumentReference>(relaxed = true) { every { id } returns docId }

        every { firestore.collection(any()).document() } returns document

        // Act
        val result = interpreter.invoke(deque)

        // Assert
        val fbInstruction = result.first()
        assertTrue(fbInstruction is FirebaseSet)
        assertEquals(fbInstruction.instructionTag, InstructionTag("tag1"))
        assertEquals(fbInstruction.document, document)
        assertEquals(fbInstruction.data, FakeDto(id = docId))
    }

    @Test
    fun `should return a firebase set from PutLazy with resolved references`() {
        // Arrange
        // First put received
        val refDocId = "putDoc"
        val referencedDoc1 =
            mockk<DocumentReference>(relaxed = true) { every { id } returns refDocId }

        val putTag = InstructionTag("putTag")
        val putRef = Put(putTag, Collection.FAKE, FakeDto())

        // Put lazy received
        val refDocId2 = "lazyDoc"
        val referencedDoc2 =
            mockk<DocumentReference>(relaxed = true) { every { id } returns refDocId2 }

        val lazyTag = InstructionTag("lazyTag")
        val putLazy = PutLazy(
            lazyTag, Collection.FAKE, referenceIdFromTag = listOf(putTag)
        ) { hash -> FakeDto(companyId = hash[putTag]) }

        val deque = ArrayDeque(listOf(putRef, putLazy))

        every { firestore.collection(any()).document() } returnsMany listOf(
            referencedDoc1, referencedDoc2
        )

        // Act
        val result = interpreter.invoke(deque)

        // Assert
        val fbInstruction = result.last()
        assertTrue(fbInstruction is FirebaseSet)
        assertEquals(fbInstruction.instructionTag, lazyTag)
        assertEquals(fbInstruction.data, FakeDto(id = refDocId2, companyId = refDocId))
    }

    @Test
    fun `should return a firebase delete instruction`() {
        // Arrange
        val instruction =
            Remove(InstructionTag("tag"), Collection.FAKE, GenericID("idToDelete"))
        val deque = ArrayDeque(listOf(instruction))

        val document = mockk<DocumentReference>(relaxed = true)

        every { firestore.collection(any()).document(any()) } returns document

        // Act
        val result = interpreter.invoke(deque)

        // Assert
        val fbInstruction = result.last()
        assertTrue(fbInstruction is FirebaseDelete)
        assertEquals(fbInstruction.instructionTag, InstructionTag("tag"))
    }

    @Test
    fun `should return a firebase update instruction`() {
        // Arrange
        val id = "id"
        val instruction =
            Update(InstructionTag("tag"), Collection.FAKE, FakeDto(id))
        val deque = ArrayDeque(listOf(instruction))

        val document = mockk<DocumentReference>(relaxed = true)

        every { firestore.collection(any()).document(any()) } returns document

        // Act
        val result = interpreter.invoke(deque)

        // Assert
        val fbInstruction = result.last()
        assertTrue(fbInstruction is FirebaseUpdate)
        assertEquals(fbInstruction.instructionTag, InstructionTag("tag"))
        assertEquals(fbInstruction.document, document)
        assertEquals(fbInstruction.data, FakeDto(id))
    }

    @Test
    fun `should throw InvalidInstructionException when receive UpdateFields instruction`() {
        // Arrange
        val updateFields = mockk<UpdateFields>(relaxed = true)

        val deque = ArrayDeque(listOf(updateFields))

        // Act && Assert
        assertThrows<InvalidInstructionException> {
            interpreter.invoke(deque)
        }

    }

    @Test
    fun `should throw InstructionNotImplementedException when receive non registered instruction`() {
        // Arrange
        val updateFields = mockk<FakeInstruction>(relaxed = true)

        val deque = ArrayDeque(listOf(updateFields))

        // Act && Assert
        assertThrows<InstructionNotImplementedException> {
            interpreter.invoke(deque)
        }

    }

}*/
