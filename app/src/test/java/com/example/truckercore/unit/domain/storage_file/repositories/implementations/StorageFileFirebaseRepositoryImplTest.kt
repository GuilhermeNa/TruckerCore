package com.example.truckercore.unit.domain.storage_file.repositories.implementations

internal class StorageFileFirebaseRepositoryImplTest {

   /* private lateinit var firebaseRepository: FirebaseRepository
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
        fun setup() {
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
    fun `create() should call create from firebaseRepository and return id`() {
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
    fun `update() should call update from firebaseRepository`() {
        // Behaviors -------------------------------------------------------------------------------
        every { firebaseRepository.update(Collection.FILE, dto) } returns Unit

        // Call ------------------------------------------------------------------------------------
        val result = repository.update(dto)

        // Assertions ------------------------------------------------------------------------------
        assertEquals(Unit, result)
        verify { firebaseRepository.update(Collection.FILE, dto) }
    }

    @Test
    fun `delete() should call delete from firebaseRepository`() {
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
    fun `fetchById() should return a response Success`() = runTest {
        // Objects ---------------------------------------------------------------------------------
        val id = "id1"
        val response = Response.Success(dto)

        // Behaviors -------------------------------------------------------------------------------
        every {
            queryBuilder.getDocumentReference(Collection.FILE, id)
        } returns documentReference
        coEvery { documentReference.get().await() } returns documentSnapShot
        every { converter.processDocumentSnapShot(documentSnapShot) } returns response

        // Call ------------------------------------------------------------------------------------
        val result = repository.fetchById(id).first()

        // Assertions ------------------------------------------------------------------------------
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.getDocumentReference(Collection.FILE, id)
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
            queryBuilder.getDocumentReference(Collection.FILE, id)
        } returns documentReference
        coEvery { documentReference.get().await() } returns documentSnapShot
        every { converter.processDocumentSnapShot(documentSnapShot) } returns response

        // Call ------------------------------------------------------------------------------------
        val result = repository.fetchById(id).first()

        // Assertions ------------------------------------------------------------------------------
        assertEquals(response, result)
        coVerifyOrder {
            queryBuilder.getDocumentReference(Collection.FILE, id)
            documentReference.get().await()
            converter.processDocumentSnapShot(documentSnapShot)
        }
    }

    @Test
    fun `fetchById() should return a ResponseError(NullDocumentSnapShotException) when Firestore fails to respond`() =
        runTest {
            // Objects ---------------------------------------------------------------------------------
            val id = "id1"
            val exception = NullDocumentSnapShotException("Simulated exception.")
            val response = Response.Error(exception)

            // Behaviors -------------------------------------------------------------------------------
            every {
                queryBuilder.getDocumentReference(Collection.FILE, id)
            } returns documentReference
            coEvery { documentReference.get().await() } returns documentSnapShot
            every { converter.processDocumentSnapShot(documentSnapShot) } throws exception

            // Call ------------------------------------------------------------------------------------
            val result = repository.fetchById(id).first()

            // Assertions ------------------------------------------------------------------------------
            assertEquals(response, result)
            coVerifyOrder {
                queryBuilder.getDocumentReference(Collection.FILE, id)
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
*/
}