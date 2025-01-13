package com.example.truckercore.unit.infrastructure.database.firebase.implementations

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore._test_utils.mockStaticTask
import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.database.firebase.errors.FirebaseConversionException
import com.example.truckercore.infrastructure.database.firebase.implementations.FirebaseRepositoryImpl
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseConverter
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseQueryBuilder
import com.example.truckercore.shared.modules.personal_data.dtos.PersonalDataDto
import com.example.truckercore.shared.utils.Response
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FirebaseRepositoryImplTestImpl {

    private lateinit var repository: FirebaseRepositoryImpl<PersonalDataDto>
    private lateinit var queryBuilder: FirebaseQueryBuilder
    private lateinit var converter: FirebaseConverter<PersonalDataDto>
    private lateinit var collection: Collection
    private lateinit var dto: PersonalDataDto
    private lateinit var documentReference: DocumentReference
    private lateinit var documentSnapShot: DocumentSnapshot
    private lateinit var query: Query
    private lateinit var querySnapshot: QuerySnapshot

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            mockStaticTask()
        }
    }

    @BeforeEach
    fun repeat() {
        queryBuilder = mockk()
        converter = mockk()
        collection = Collection.PERSONAL_DATA
        repository = FirebaseRepositoryImpl(queryBuilder, converter, collection)

        documentReference = mockk()
        dto = mockk()
        documentSnapShot = mockk()
        query = mockk()
        querySnapshot = mockk()
    }

    @Test
    fun `create() should create the entity and return id`() = runTest {
        // Objects
        val newId = "newId"
        val newDto = mockk<PersonalDataDto> { every { id } returns newId }
        val task = mockk<Task<Void>> {
            every { exception } returns null
            every { isSuccessful } returns true
            every { isComplete } returns true
            every { isCanceled } returns false
            every { result } returns mockk()
        }
        val response = Response.Success(newId)

        // Behaviors
        every { queryBuilder.newDocument(collection.getName()) } returns documentReference
        every { documentReference.id } returns newId
        every { documentReference.set(newDto) } returns task
        every { dto.initializeId(newId) } returns newDto
        every { converter.processTask(task, documentReference) } returns response
        every { task.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<Void>
            listener.onComplete(task)
            task
        }

        // Call
        val result = repository.create(dto).single()

        // Assertions
        assertEquals(response, result)

        coVerifyOrder {
            queryBuilder.newDocument(collection.getName())
            dto.initializeId(newId)
            documentReference.set(newDto)
        }

    }

    @Test
    fun `update() should update a document reference`() = runTest {
        // Object
        val dtoId = "dtoId"
        val task = mockk<Task<Void>> {
            every { exception } returns null
            every { isSuccessful } returns true
            every { isComplete } returns true
            every { isCanceled } returns false
            every { result } returns mockk()
        }
        val response = Response.Success(Unit)

        // Behaviors
        every { dto.id } returns dtoId
        every {
            queryBuilder.getDocumentReference(eq(collection.getName()), eq(dtoId))
        } returns documentReference
        every { documentReference.set(dto) } returns task
        every { converter.processTask(task = task) } returns response
        every { task.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<Void>
            listener.onComplete(task)
            task
        }

        // Call
        val result = repository.update(dto).single()

        // Assertions
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.getDocumentReference(eq(collection.getName()), eq(dtoId))
            documentReference.set(dto)
        }
    }

    @Test
    fun `delete() should update a document reference`() = runTest {
        // Object
        val id = "id"
        val task = mockk<Task<Void>> {
            every { exception } returns null
            every { isSuccessful } returns true
            every { isComplete } returns true
            every { isCanceled } returns false
            every { result } returns mockk()
        }
        val response = Response.Success(Unit)

        // Behaviors
        every {
            queryBuilder.getDocumentReference(eq(collection.getName()), eq(id))
        } returns documentReference
        every { documentReference.delete() } returns task
        every { converter.processTask(task = task) } returns response
        every { task.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<Void>
            listener.onComplete(task)
            task
        }

        // Call
        val result = repository.delete(id).single()

        // Assertions
        assertEquals(response , result)
        coVerifyOrder {
            queryBuilder.getDocumentReference(eq(collection.getName()), eq(id))
            documentReference.delete()
        }
    }

    @Test
    fun `entityExists() should return Response with true when found the entity`() =
        runTest {
        // Object
        val id = "id"
        val response = Response.Success(true)

        // Behaviors
        every {
            queryBuilder.getDocumentReference(eq(collection.getName()), eq(id))
        } returns documentReference
        coEvery { documentReference.get().await() } returns documentSnapShot
        every { converter.processEntityExistence(documentSnapShot) } returns response

        // Call
        val result = repository.entityExists(id).first()

        // Assertions
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.getDocumentReference(eq(collection.getName()), eq(id))
            documentReference.get().await()
            converter.processEntityExistence(eq(documentSnapShot))
        }
    }

    @Test
    fun `entityExists() should return Response Empty when the entity don't exists`() = runTest {
        // Object
        val id = "id"
        val response = Response.Empty

        // Behaviors
        every {
            queryBuilder.getDocumentReference(eq(collection.getName()), eq(id))
        } returns documentReference
        coEvery { documentReference.get().await() } returns documentSnapShot
        every { converter.processEntityExistence(documentSnapShot) } returns response

        // Call
        val result = repository.entityExists(id).first()

        // Assertions
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.getDocumentReference(eq(collection.getName()), eq(id))
            documentReference.get().await()
            converter.processEntityExistence(eq(documentSnapShot))
        }
    }

    @Test
    fun `entityExists() should return Response Empty when the document is null`() = runTest {
        // Object
        val id = "id"
        val response = Response.Empty

        // Behaviors
        every {
            queryBuilder.getDocumentReference(eq(collection.getName()), eq(id))
        } returns documentReference
        coEvery { documentReference.get().await() } returns null

        // Call
        val result = repository.entityExists(id).first()

        // Assertions
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.getDocumentReference(eq(collection.getName()), eq(id))
            documentReference.get().await()
        }
    }

    @Test
    fun `entityExists() should return Response Error when get some exception`() = runTest {
        // Object
        val id = "id"
        val exception = FirebaseConversionException("Simulated exception.")
        val response = Response.Error(exception = exception)

        // Behaviors
        every {
            queryBuilder.getDocumentReference(eq(collection.getName()), eq(id))
        } returns documentReference
        coEvery { documentReference.get().await() } returns documentSnapShot
        every { converter.processEntityExistence(eq(documentSnapShot)) } throws exception

        // Call
        val result = repository.entityExists(id).first()

        // Assertions
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.getDocumentReference(eq(collection.getName()), eq(id))
            documentReference.get().await()
            converter.processEntityExistence(eq(documentSnapShot))
        }
    }

    @Test
    fun `simpleDocumentFetch() should return Response with the fetched entity when found`() = runTest {
        // Object
        val id = "id"
        val response = Response.Success(dto)

        // Behaviors
        every {
            queryBuilder.getDocumentReference(eq(collection.getName()), eq(id))
        } returns documentReference
        coEvery { documentReference.get().await() } returns documentSnapShot
        every { converter.processDocumentSnapShot(documentSnapShot) } returns response

        // Call
        val result = repository.simpleDocumentFetch(id).first()

        // Assertions
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.getDocumentReference(eq(collection.getName()), eq(id))
            documentReference.get().await()
            converter.processDocumentSnapShot(eq(documentSnapShot))
        }
    }

    @Test
    fun `simpleDocumentFetch() should return Response Empty when the entity doesn't exist`() = runTest {
        // Object
        val id = "id"
        val response = Response.Empty

        // Behaviors
        every {
            queryBuilder.getDocumentReference(eq(collection.getName()), eq(id))
        } returns documentReference
        coEvery { documentReference.get().await() } returns documentSnapShot
        every { converter.processDocumentSnapShot(documentSnapShot) } returns response

        // Call
        val result = repository.simpleDocumentFetch(id).first()

        // Assertions
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.getDocumentReference(eq(collection.getName()), eq(id))
            documentReference.get().await()
            converter.processDocumentSnapShot(eq(documentSnapShot))
        }
    }

    @Test
    fun `simpleDocumentFetch() should return Response Empty when the document is null`() = runTest {
        // Object
        val id = "id"
        val response = Response.Empty

        // Behaviors
        every {
            queryBuilder.getDocumentReference(eq(collection.getName()), eq(id))
        } returns documentReference
        coEvery { documentReference.get().await() } returns null

        // Call
        val result = repository.simpleDocumentFetch(id).first()

        // Assertions
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.getDocumentReference(eq(collection.getName()), eq(id))
            documentReference.get().await()
        }
    }

    @Test
    fun `simpleDocumentFetch() should return Response Error when get some exception`() = runTest {
        // Object
        val id = "id"
        val exception = FirebaseConversionException("Simulated exception.")
        val response = Response.Error(exception = exception)

        // Behaviors
        every {
            queryBuilder.getDocumentReference(eq(collection.getName()), eq(id))
        } returns documentReference
        coEvery { documentReference.get().await() } returns documentSnapShot
        every { converter.processDocumentSnapShot(eq(documentSnapShot)) } throws exception

        // Call
        val result = repository.simpleDocumentFetch(id).first()

        // Assertions
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.getDocumentReference(eq(collection.getName()), eq(id))
            documentReference.get().await()
            converter.processDocumentSnapShot(eq(documentSnapShot))
        }
    }

    @Test
    fun `simpleQueryFetch() should return Response with the fetched entities when found`() = runTest {
        // Object
        val field = mockk<Field>(relaxed = true)
        val value = "value"
        val response = Response.Success(listOf(dto))

        // Behaviors
        every {
            queryBuilder.getQuery(eq(collection.getName()), eq(field.name), eq(value))
        } returns query
        coEvery { query.get().await() } returns querySnapshot
        every { converter.processQuerySnapShot(querySnapshot) } returns response

        // Call
        val result = repository.simpleQueryFetch(field, value).first()

        // Assertions
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.getQuery(eq(collection.getName()), eq(field.name), eq(value))
            query.get().await()
            converter.processQuerySnapShot(eq(querySnapshot))
        }
    }

    @Test
    fun `simpleQueryFetch() should return Response Empty when no entities are found`() = runTest {
        // Object
        val field = mockk<Field>(relaxed = true)
        val value = "value"
        val response = Response.Empty

        // Behaviors
        every {
            queryBuilder.getQuery(eq(collection.getName()), eq(field.name), eq(value))
        } returns query
        coEvery { query.get().await() } returns querySnapshot
        every { converter.processQuerySnapShot(querySnapshot) } returns response

        // Call
        val result = repository.simpleQueryFetch(field, value).first()

        // Assertions
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.getQuery(eq(collection.getName()), eq(field.name), eq(value))
            query.get().await()
            converter.processQuerySnapShot(eq(querySnapshot))
        }
    }

    @Test
    fun `simpleQueryFetch() should return Response Empty when the query result is null`() = runTest {
        // Object
        val field = mockk<Field>(relaxed = true)
        val value = "value"
        val response = Response.Empty

        // Behaviors
        every {
            queryBuilder.getQuery(eq(collection.getName()), eq(field.name), eq(value))
        } returns query
        coEvery { query.get().await() } returns null

        // Call
        val result = repository.simpleQueryFetch(field, value).first()

        // Assertions
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.getQuery(eq(collection.getName()), eq(field.name), eq(value))
            query.get().await()
        }
    }

    @Test
    fun `simpleQueryFetch() should return Response Error when get some exception`() = runTest {
        // Object
        val field = mockk<Field>(relaxed = true)
        val value = "value"
        val exception = FirebaseConversionException("Simulated exception.")
        val response = Response.Error(exception = exception)

        // Behaviors
        every {
            queryBuilder.getQuery(eq(collection.getName()), eq(field.name), eq(value))
        } returns query
        coEvery { query.get().await() } returns querySnapshot
        every { converter.processQuerySnapShot(eq(querySnapshot)) } throws exception

        // Call
        val result = repository.simpleQueryFetch(field, value).first()

        // Assertions
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.getQuery(eq(collection.getName()), eq(field.name), eq(value))
            query.get().await()
            converter.processQuerySnapShot(eq(querySnapshot))
        }
    }

}