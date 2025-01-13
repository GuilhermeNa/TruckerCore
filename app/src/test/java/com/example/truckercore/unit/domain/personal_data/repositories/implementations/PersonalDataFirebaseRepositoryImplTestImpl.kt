package com.example.truckercore.unit.domain.personal_data.repositories.implementations

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore._test_utils.mockStaticTask
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseConverter
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseQueryBuilder
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.shared.modules.personal_data.dtos.PersonalDataDto
import com.example.truckercore.shared.modules.personal_data.repositories.implementations.PersonalDataRepositoryImpl
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import io.mockk.mockk
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach

internal class PersonalDataFirebaseRepositoryImplTestImpl {

    private lateinit var firebaseRepository: FirebaseRepository<PersonalDataDto>
    private lateinit var queryBuilder: FirebaseQueryBuilder
    private lateinit var converter: FirebaseConverter<PersonalDataDto>
    private lateinit var repository: PersonalDataRepositoryImpl
    private lateinit var dto: PersonalDataDto
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
        dto = mockk<PersonalDataDto>()
        documentReference = mockk<DocumentReference>()
        documentSnapShot = mockk<DocumentSnapshot>()
        query = mockk<Query>()
        querySnapshot = mockk<QuerySnapshot>()
        firebaseRepository = mockk()
        queryBuilder = mockk()
        converter = mockk()
        //repository = PersonalDataRepositoryImpl(firebaseRepository, queryBuilder, converter)
    }

 /*   @Test
    fun `collection name should be personal_data`() {
        assertEquals(repository.collectionName, Collection.PERSONAL_DATA)
    }

    @Test
    fun `create() should call create from firebaseRepository and return id`() {
        // Objects ---------------------------------------------------------------------------------
        val newId = "newId"

        // Behaviors -------------------------------------------------------------------------------
        every { firebaseRepository.create(Collection.PERSONAL_DATA, dto) } returns newId

        // Call ------------------------------------------------------------------------------------
        val result = repository.create(dto)

        // Assertions ------------------------------------------------------------------------------
        assertEquals(newId, result)
        verify { firebaseRepository.create(Collection.PERSONAL_DATA, dto) }
    }

    @Test
    fun `update() should call update from firebaseRepository`() {
        // Behaviors -------------------------------------------------------------------------------
        every { firebaseRepository.update(Collection.PERSONAL_DATA, dto) } returns Unit

        // Call ------------------------------------------------------------------------------------
        val result = repository.update(dto)

        // Assertions ------------------------------------------------------------------------------
        assertEquals(Unit, result)
        verify { firebaseRepository.update(Collection.PERSONAL_DATA, dto) }
    }

    @Test
    fun `delete() should call delete from firebaseRepository`() {
        // Objects ---------------------------------------------------------------------------------
        val id = "id"

        // Behaviors -------------------------------------------------------------------------------
        every { firebaseRepository.delete(Collection.PERSONAL_DATA, id) } returns Unit

        // Call ------------------------------------------------------------------------------------
        val result = repository.delete(id)

        // Assertions ------------------------------------------------------------------------------
        assertEquals(Unit, result)
        verify { firebaseRepository.delete(Collection.PERSONAL_DATA, id) }
    }

    @Test
    fun `fetchById() should return a response Success`() = runTest {
        // Objects ---------------------------------------------------------------------------------
        val id = "id1"
        val response = Response.Success(dto)

        // Behaviors -------------------------------------------------------------------------------
        every {
            queryBuilder.getDocumentReference(Collection.PERSONAL_DATA, id)
        } returns documentReference
        coEvery { documentReference.get().await() } returns documentSnapShot
        every { converter.processDocumentSnapShot(documentSnapShot) } returns response

        // Call ------------------------------------------------------------------------------------
        val result = repository.fetchById(id).first()

        // Assertions ------------------------------------------------------------------------------
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.getDocumentReference(Collection.PERSONAL_DATA, id)
            documentReference.get().await()
            converter.processDocumentSnapShot(documentSnapShot)
        }
    }

    @Test
    fun `fetchById() should return a response Empty`() = runTest {
        // Objects ---------------------------------------------------------------------------------
        val id = "id1"
        val response = Response.Empty

        // Behaviors -------------------------------------------------------------------------------
        every {
            queryBuilder.getDocumentReference(Collection.PERSONAL_DATA, id)
        } returns documentReference
        coEvery { documentReference.get().await() } returns documentSnapShot
        every { converter.processDocumentSnapShot(documentSnapShot) } returns response

        // Call ------------------------------------------------------------------------------------
        val result = repository.fetchById(id).first()

        // Assertions ------------------------------------------------------------------------------
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.getDocumentReference(Collection.PERSONAL_DATA, id)
            documentReference.get().await()
            converter.processDocumentSnapShot(documentSnapShot)
        }
    }

    @Test
    fun `fetchById() should return a ResponseError(NullDocumentSnapShotException) when firestore fails to respond`() =
        runTest {
            // Objects ---------------------------------------------------------------------------------
            val id = "id1"
            val exception = NullDocumentSnapShotException("Simulated exception")
            val response = Response.Error(exception)

            // Behaviors -------------------------------------------------------------------------------
            every {
                queryBuilder.getDocumentReference(Collection.PERSONAL_DATA, id)
            } returns documentReference
            coEvery { documentReference.get().await() } returns mockk()
            every { converter.processDocumentSnapShot(any()) } throws exception

            // Call ------------------------------------------------------------------------------------
            val result = repository.fetchById(id).first()

            // Assertions ------------------------------------------------------------------------------
            assertEquals(response, result)
            coVerifyOrder {
                queryBuilder.getDocumentReference(Collection.PERSONAL_DATA, id)
                documentReference.get().await()
                converter.processDocumentSnapShot(any())
            }
        }

    @Test
    fun `fetchPersonalDataByParentId() should return a response Success`() = runTest {
        // Objects ---------------------------------------------------------------------------------
        val parentId = "parentId1"
        val response = Response.Success<List<PersonalDataDto>>(mockk())

        // Behaviors -------------------------------------------------------------------------------
        every { queryBuilder.buildParentIdQuery(Collection.PERSONAL_DATA, parentId) } returns query
        coEvery { query.get().await() } returns querySnapshot
        every { converter.processQuerySnapShot(querySnapshot) } returns response

        // Call ------------------------------------------------------------------------------------
        val result = repository.fetchPersonalDataByParentId(parentId).first()

        // Assertions ------------------------------------------------------------------------------
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.buildParentIdQuery(Collection.PERSONAL_DATA, parentId)
            query.get().await()
            converter.processQuerySnapShot(querySnapshot)
        }
    }

    @Test
    fun `fetchPersonalDataByParentId() should return a response Empty`() = runTest {
        // Objects ---------------------------------------------------------------------------------
        val parentId = "parentId1"
        val response = Response.Empty

        // Behaviors -------------------------------------------------------------------------------
        every { queryBuilder.buildParentIdQuery(Collection.PERSONAL_DATA, parentId) } returns query
        coEvery { query.get().await() } returns querySnapshot
        every { converter.processQuerySnapShot(querySnapshot) } returns response

        // Call ------------------------------------------------------------------------------------
        val result = repository.fetchPersonalDataByParentId(parentId).first()

        // Assertions ------------------------------------------------------------------------------
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.buildParentIdQuery(Collection.PERSONAL_DATA, parentId)
            query.get().await()
            converter.processQuerySnapShot(querySnapshot)
        }
    }

    @Test
    fun `fetchPersonalDataByParentId() should return a ResponseError when firestore fails to respond`() =
        runTest {
            // Objects ---------------------------------------------------------------------------------
            val parentId = "parentId1"
            val exception = NullQuerySnapShotException("Simulated exception")
            val response = Response.Error(exception)

            // Behaviors -------------------------------------------------------------------------------
            every {
                queryBuilder.buildParentIdQuery(Collection.PERSONAL_DATA, parentId)
            } returns query
            coEvery { query.get().await() } returns querySnapshot
            every { converter.processQuerySnapShot(querySnapshot) } throws exception

            // Call ------------------------------------------------------------------------------------
            val result = repository.fetchPersonalDataByParentId(parentId).first()

            // Assertions ------------------------------------------------------------------------------
            assertEquals(response, result)
            coVerifyOrder {
                queryBuilder.buildParentIdQuery(Collection.PERSONAL_DATA, parentId)
                query.get().await()
                converter.processQuerySnapShot(querySnapshot)
            }
        }*/

}