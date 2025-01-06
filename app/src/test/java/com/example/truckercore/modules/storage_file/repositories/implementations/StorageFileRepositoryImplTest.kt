package com.example.truckercore.modules.storage_file.repositories.implementations

import com.example.truckercore._utils.mockStaticLog
import com.example.truckercore._utils.mockStaticTask
import com.example.truckercore.configs.database.Collection
import com.example.truckercore.infrastructure.database.firebase.exceptions.NullDocumentSnapShotException
import com.example.truckercore.infrastructure.database.firebase.exceptions.NullQuerySnapShotException
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseConverter
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseQueryBuilder
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.modules.storage_file.dtos.StorageFileDto
import com.example.truckercore.shared.utils.Response
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class StorageFileRepositoryImplTest {

    private lateinit var firebaseRepository: FirebaseRepository
    private lateinit var queryBuilder: FirebaseQueryBuilder
    private lateinit var converter: FirebaseConverter<StorageFileDto>
    private lateinit var repository: StorageFileRepositoryImpl
    private lateinit var dto: StorageFileDto
    private lateinit var documentReference: DocumentReference
    private lateinit var documentSnapShot: DocumentSnapshot
    private lateinit var query: Query
    private lateinit var querySnapshot: QuerySnapshot

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup(): Unit {
            mockStaticLog()
            mockStaticTask()
        }
    }

    @BeforeEach
    fun repeat() {
        dto = mockk<StorageFileDto>()
        documentReference = mockk<DocumentReference>()
        documentSnapShot = mockk<DocumentSnapshot>()
        query = mockk<Query>()
        querySnapshot = mockk<QuerySnapshot>()
        firebaseRepository = mockk()
        queryBuilder = mockk()
        converter = mockk()
        repository = StorageFileRepositoryImpl(firebaseRepository, queryBuilder, converter)
    }

    @Test
    fun `collection name should be files`() {
        assertEquals(repository.collectionName, Collection.FILE)
    }

    @Test
    fun `createFile() should call create from firebaseRepository and return id`() {
        // Object
        val newId = "newId"

        // Behaviors -------------------------------------------------------------------------------
        every { firebaseRepository.create(Collection.FILE, dto) } returns newId

        // Call ------------------------------------------------------------------------------------
        val result = repository.create(dto)

        // Assertions ------------------------------------------------------------------------------
        assertEquals(newId, result)
        verify { firebaseRepository.create(Collection.FILE, dto) }
    }

    @Test
    fun `updateFile() should call update from firebaseRepository`() {
        // Behaviors -------------------------------------------------------------------------------
        every { firebaseRepository.update(Collection.FILE, dto) } returns Unit

        // Call ------------------------------------------------------------------------------------
        val result = repository.update(dto)

        // Assertions ------------------------------------------------------------------------------
        assertEquals(Unit, result)
        verify { firebaseRepository.update(Collection.FILE, dto) }
    }

    @Test
    fun `deleteFile() should call delete from firebaseRepository`() {
        // Objects ---------------------------------------------------------------------------------
        val id = "id"

        // Behaviors -------------------------------------------------------------------------------
        every { firebaseRepository.delete(Collection.FILE, id) } returns Unit

        // Call ------------------------------------------------------------------------------------
        val result = repository.delete(id)

        // Assertions ------------------------------------------------------------------------------
        assertEquals(Unit, result)
        verify { firebaseRepository.delete(Collection.FILE, id) }
    }

    @Test
    fun `fetchFileById() should return a response Success`() = runTest {
        // Objects ---------------------------------------------------------------------------------
        val id = "id1"
        val response = Response.Success(dto)

        // Behaviors -------------------------------------------------------------------------------
        every {
            queryBuilder.buildDocumentReferenceQuery(Collection.FILE, id)
        } returns documentReference
        coEvery { documentReference.get().await() } returns documentSnapShot
        every { converter.processDocumentSnapShot(documentSnapShot) } returns response

        // Call ------------------------------------------------------------------------------------
        val result = repository.fetchById(id).first()

        // Assertions ------------------------------------------------------------------------------
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.buildDocumentReferenceQuery(Collection.FILE, id)
            documentReference.get().await()
            converter.processDocumentSnapShot(documentSnapShot)
        }
    }

    @Test
    fun `fetchFileById() should return a response Empty`() = runTest {
        // Objects ---------------------------------------------------------------------------------
        val id = "id1"
        val response = Response.Empty

        // Behaviors -------------------------------------------------------------------------------
        every {
            queryBuilder.buildDocumentReferenceQuery(Collection.FILE, id)
        } returns documentReference
        coEvery { documentReference.get().await() } returns documentSnapShot
        every { converter.processDocumentSnapShot(documentSnapShot) } returns response

        // Call ------------------------------------------------------------------------------------
        val result = repository.fetchById(id).first()

        // Assertions ------------------------------------------------------------------------------
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.buildDocumentReferenceQuery(Collection.FILE, id)
            documentReference.get().await()
            converter.processDocumentSnapShot(documentSnapShot)
        }
    }

    @Test
    fun `fetchFileById() should return a ResponseError(NullDocumentSnapShotException) when Firestore fails to respond`() =
        runTest {
            // Objects ---------------------------------------------------------------------------------
            val id = "id1"
            val exception = NullDocumentSnapShotException("Simulated exception.")
            val response = Response.Error(exception)

            // Behaviors -------------------------------------------------------------------------------
            every {
                queryBuilder.buildDocumentReferenceQuery(Collection.FILE, id)
            } returns documentReference
            coEvery { documentReference.get().await() } returns documentSnapShot
            every { converter.processDocumentSnapShot(documentSnapShot) } throws exception

            // Call ------------------------------------------------------------------------------------
            val result = repository.fetchById(id).first()

            // Assertions ------------------------------------------------------------------------------
            assertEquals(response, result)
            coVerifyOrder {
                queryBuilder.buildDocumentReferenceQuery(Collection.FILE, id)
                documentReference.get().await()
                converter.processDocumentSnapShot(documentSnapShot)
            }
        }

    @Test
    fun `fetchFilesByParentUid() should return a response Success`() = runTest {
        // Objects ---------------------------------------------------------------------------------
        val parentId = "parentId"
        val response = Response.Success(mockk<List<StorageFileDto>>())

        // Behaviors -------------------------------------------------------------------------------
        every {
            queryBuilder.buildParentIdQuery(Collection.FILE, parentId)
        } returns query
        coEvery { query.get().await() } returns querySnapshot
        every { converter.processQuerySnapShot(querySnapshot) } returns response

        // Call ------------------------------------------------------------------------------------
        val result = repository.fetchFilesByParentUid(parentId).first()

        // Assertions ------------------------------------------------------------------------------
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.buildParentIdQuery(Collection.FILE, parentId)
            query.get().await()
            converter.processQuerySnapShot(querySnapshot)
        }
    }

    @Test
    fun `fetchFilesByParentUid() should return a response Empty`() = runTest {
        // Objects ---------------------------------------------------------------------------------
        val parentId = "parentId"
        val response = Response.Empty

        // Behaviors -------------------------------------------------------------------------------
        every {
            queryBuilder.buildParentIdQuery(Collection.FILE, parentId)
        } returns query
        coEvery { query.get().await() } returns querySnapshot
        every { converter.processQuerySnapShot(querySnapshot) } returns response

        // Call ------------------------------------------------------------------------------------
        val result = repository.fetchFilesByParentUid(parentId).first()

        // Assertions ------------------------------------------------------------------------------
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.buildParentIdQuery(Collection.FILE, parentId)
            query.get().await()
            converter.processQuerySnapShot(querySnapshot)
        }
    }

    @Test
    fun `fetchFilesByParentUid() should return a ResponseError(NullQuerySnapShotException) when Firestore fails to respond`() =
        runTest {
            // Objects ---------------------------------------------------------------------------------
            val parentId = "parentId"
            val exception = NullQuerySnapShotException("Simulated exception")
            val response = Response.Error(exception)

            // Behaviors -------------------------------------------------------------------------------
            every {
                queryBuilder.buildParentIdQuery(Collection.FILE, parentId)
            } returns query
            coEvery { query.get().await() } returns querySnapshot
            every { converter.processQuerySnapShot(querySnapshot) } throws exception

            // Call ------------------------------------------------------------------------------------
            val result = repository.fetchFilesByParentUid(parentId).first()

            // Assertions ------------------------------------------------------------------------------
            assertEquals(response, result)
            coVerifyOrder {
                queryBuilder.buildParentIdQuery(Collection.FILE, parentId)
                query.get().await()
                converter.processQuerySnapShot(querySnapshot)
            }
        }

}