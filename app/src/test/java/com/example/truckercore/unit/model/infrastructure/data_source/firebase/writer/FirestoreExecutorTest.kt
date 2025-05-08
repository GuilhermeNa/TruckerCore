package com.example.truckercore.unit.model.infrastructure.data_source.firebase.writer

import com.example.truckercore._test_data_provider.fake_objects.FakeDto
import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.data_source.firebase.writer.FirestoreExecutor
import com.example.truckercore.model.infrastructure.data_source.firebase.writer.FirestoreInstInterpreter
import com.example.truckercore.model.infrastructure.data_source.firebase.writer.api_instructions.FirebaseSet
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_api.exceptions.InvalidInstructionException
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.Instruction
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.InstructionTag
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.types.Put
import com.example.truckercore.model.infrastructure.integration.instruction_executor.for_app.instruction.types.PutLazy
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Transaction
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class FirestoreExecutorTest : KoinTest {

    //----------------------------------------------------------------------------------------------
    // Setup
    //----------------------------------------------------------------------------------------------
    private val firebase: FirebaseFirestore by inject()
    private val interpreter: FirestoreInstInterpreter by inject()
    private val executor: FirestoreExecutor by inject()

    private val transaction = mockk<Transaction>(relaxed = true)

    @BeforeEach
    fun setup() {

        startKoin {
            modules(
                module {
                    single<FirebaseFirestore> { mockk(relaxed = true) }
                    single<FirestoreInstInterpreter> { mockk(relaxed = true) }
                    single { FirestoreExecutor(get(), get()) }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()

    //----------------------------------------------------------------------------------------------
    // Testing
    //----------------------------------------------------------------------------------------------
    @Test
    fun `should execute one instructions in Firestore transaction`() = runTest {
        // Arrange
        //---
        val tag = InstructionTag("tag1")
        val collection = Collection.FAKE
        val data = FakeDto()
        val instr1 = Put(tag, collection, data)

        val deque = ArrayDeque<Instruction>()
        deque.add(instr1)

        val document = mockk<DocumentReference>(relaxed = true)
        val fbInstr1 = FirebaseSet(tag, document, data)

        every { interpreter(deque) } returns ArrayDeque(listOf(fbInstr1))

        every { firebase.runTransaction(any<Transaction.Function<Unit>>()) } answers {
            val function = arg<Transaction.Function<Unit>>(0)
            function.apply(transaction)
            Tasks.forResult(Unit)
        }

        // Act
        executor.invoke(deque)

        // Assert
        verify(exactly = 1) { fbInstr1.execute(transaction) }
    }

    @Test
    fun `should execute multiple instructions in Firestore transaction`() = runTest {
        // Arrange
        val tag = InstructionTag("tag1")
        val collection = Collection.FAKE
        val data = FakeDto()
        val instr1 = Put(tag, collection, data)

        val tag2 = InstructionTag("tag2")
        val data2 = FakeDto()
        val instr2 = Put(tag2, collection, data2)

        val deque = ArrayDeque<Instruction>()
        deque.add(instr1)
        deque.add(instr2)

        val document = mockk<DocumentReference>(relaxed = true)
        val document2 = mockk<DocumentReference>(relaxed = true)
        val fbInstr1 = FirebaseSet(tag, document, data)
        val fbInstr2 = FirebaseSet(tag2, document2, data2)

        every { interpreter(deque) } returns ArrayDeque(listOf(fbInstr1, fbInstr2))

        every { firebase.runTransaction(any<Transaction.Function<Unit>>()) } answers {
            val function = arg<Transaction.Function<Unit>>(0)
            function.apply(transaction)
            Tasks.forResult(Unit)
        }

        // Act
        executor.invoke(deque)

        // Assert
        verifyOrder {
            fbInstr1.execute(transaction)
            fbInstr2.execute(transaction)
        }
        verify(exactly = 1) {
            fbInstr1.execute(transaction)
            fbInstr2.execute(transaction)
        }
    }

    @Test
    fun `should throw InvalidInstructionException when instruction deque is empty`() = runTest {
        // Arrange
        val deque = ArrayDeque<Instruction>()

        // Act && Assert
        val exception = assertThrows<InvalidInstructionException> {
            executor.invoke(deque)
        }

        assertEquals(
            "Instruction deque must not be empty. " +
                    "At least one instruction is required for execution.",
            exception.message
        )
    }

    @Test
    fun `should throw InvalidInstructionException when instruction deque has duplicate tags`() =
        runTest {
            // Arrange
            val instr1 = mockk<Instruction> {
                every { instructionTag } returns InstructionTag("tag1")
            }
            val instr2 = mockk<Instruction> {
                every { instructionTag } returns InstructionTag("tag1")
            }

            val deque = ArrayDeque(listOf(instr1, instr2))

            val exception = assertThrows<InvalidInstructionException> {
                executor.invoke(deque)
            }

            assertEquals(
                "Each instruction must have a unique instructionTag. Duplicate tags found in the deque.",
                exception.message
            )
        }

    @Test
    fun `should throw InvalidInstructionException when PutLazy has missing references`() = runTest {
        // Arrange
        val instr1 = mockk<Put> {
            every { instructionTag } returns InstructionTag("tag")
        }
        val lazyInstr = mockk<PutLazy> {
            every { instructionTag } returns InstructionTag("tag1")
            every { referenceIdFromTag } returns listOf(InstructionTag("non_tag"))
        }

        val deque = ArrayDeque<Instruction>()
        deque.add(instr1)
        deque.add(lazyInstr)

        // Act && Assert
        val exception = assertThrows<InvalidInstructionException> {
            executor.invoke(deque)
        }

        assertEquals(
            "Missing instructionTag(s) required by one or more PutLazy instructions. Ensure all referenced instructions are present in the deque.",
            exception.message
        )
    }

}