package com.example.truckercore.unit.infrastructure.database.firebase.repository

import com.example.truckercore._test_data_provider.TestPersonalDataDataProvider
import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore._test_utils.mockStaticTask
import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.database.firebase.repository.FirebaseRepository
import com.example.truckercore.infrastructure.database.firebase.repository.FirebaseRepositoryImpl
import com.example.truckercore.infrastructure.database.firebase.util.FirebaseConverter
import com.example.truckercore.infrastructure.database.firebase.util.FirebaseQueryBuilder
import com.example.truckercore.shared.modules.personal_data.dto.PersonalDataDto
import com.example.truckercore.shared.utils.sealeds.Response
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.spyk
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertNotNull

internal class FirebaseRepositoryImplTest : KoinTest {

    // Injections
    private val queryBuilder: FirebaseQueryBuilder by inject()
    private val converter: FirebaseConverter by inject()
    private val repository: FirebaseRepository by inject()

    //Data provider
    private val pDataProvider = TestPersonalDataDataProvider

    // Mocks
    private val repositorySpy = spyk(repository, recordPrivateCalls = true)
    private val collectionRef = Collection.PERSONAL_DATA
    private val docReference: DocumentReference = mockk(relaxed = true)
    private val docSnapShot: DocumentSnapshot = mockk()
    private val query: Query = mockk()
    private val id = "refId"

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            mockStaticTask()

            startKoin {
                modules(module {
                    single<FirebaseQueryBuilder> { mockk(relaxed = true) }
                    single<FirebaseConverter> { mockk(relaxed = true) }
                    single<FirebaseRepository> { FirebaseRepositoryImpl(get(), get()) }
                })
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()

    }

/*
    @Test
    fun `create() should create the entity and return id`() = runTest {
        // Arrange
        val dtoReadyToSave = pDataProvider.getBaseDto().copy(id = id)
        val dtoSent = mockk<PersonalDataDto>(relaxed = true) {
            every { initializeId(any()) } returns dtoReadyToSave
        }
        val mockFlow = callbackFlow<Response<String>> {
            trySend(Response.Success(id))
            awaitClose {  }
        }

        every { queryBuilder.createDocument(any()) } returns docReference
        every { docReference.id } returns id
        coEvery { docReference.set(any()).addOnCompleteListener(mockk()) } returns mockk()

        // Call
        val result = repository.create(collectionRef, dtoSent).single()

        // Assertions
        assertNotNull(result)
    }


*/

    /*

        @Test
        fun `update() should update a document reference`() = runTest {
            // Arrange
      val dtoId = "dtoId"
            val task = mockk<Task<Void>> {
                every { exception } returns null
                every { isSuccessful } returns true
                every { isComplete } returns true
                every { isCanceled } returns false
                every { result } returns mockk()
            }
            val expectedResult = Response.Success(Unit)

            every { dto.id } returns dtoId
            every { queryBuilder.getDocument(collectionRef, dtoId) } returns docReference
            every { docReference.set(dto) } returns task
            every { task.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<Void>
                listener.onComplete(task)
                task
            }

            // Call
            val result = repositorySpy.update(collectionRef, dto).single()

            // Assertions
            assertEquals(expectedResult, result)
            coVerifyOrder {
                queryBuilder.getDocument(eq(collectionRef), eq(dtoId))
                docReference.set(dto)
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
            val expectedResult = Response.Success(Unit)

            // Behaviors
            every { queryBuilder.getDocument(collectionRef, id) } returns docReference
            every { docReference.delete() } returns task
            every { task.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<Void>
                listener.onComplete(task)
                task
            }

            // Call
            val result = repositorySpy.delete(collectionRef, id).single()

            // Assertions
            assertEquals(expectedResult, result)
            coVerifyOrder {
                queryBuilder.getDocument(eq(collectionRef), eq(id))
                docReference.delete()
            }
        }

        @Test
        fun `entityExists() should return Success with true when found the entity`() = runTest {
            // Arrange
            val id = "id"

            every { queryBuilder.getDocument(collectionRef, id) } returns docReference
            coEvery { docReference.get().await() } returns docSnapShot
            every { docSnapShot.exists() } returns true

            // Call
            val result = repositorySpy.entityExists(collectionRef, id).single()

            // Assertions
            assertTrue(result is Response.Success)
            coVerifyOrder {
                queryBuilder.getDocument(eq(collectionRef), eq(id))
                docReference.get().await()
            }
        }

        @Test
        fun `entityExists() should return Empty when the entity don't exists`() = runTest {
            // Arrange
            val id = "id"

            every { queryBuilder.getDocument(collectionRef, eq(id)) } returns docReference
            coEvery { docReference.get().await() } returns docSnapShot
            every { docSnapShot.exists() } returns false

            // Call
            val result = repositorySpy.entityExists(collectionRef, id).single()

            // Assertions
            assertTrue(result is Response.Empty)
            coVerifyOrder {
                queryBuilder.getDocument(eq(collectionRef), eq(id))
                docReference.get().await()
            }
        }

        @Test
        fun `fetchLoggedUser() should retrieve a single flow with the logged user when its found`() {
            // Arrange
            val id = "id"
            val shouldStream = false
            val expectedData = mockk<UserDto>()
            val expectedResult = Response.Success(expectedData)

            every { repositorySpy["getLoggedUser"](id) } returns flowOf(expectedResult)

            // Call
            val result = repositorySpy.fetchLoggedUser(id, shouldStream)

            // Arrange
            assertEquals(expectedResult, result)
            verify { repositorySpy["getLoggedUser"](id) }

        }

        @Test
        fun `fetchLoggedUser() should retrieve a stream flow with the logged user when its found`() {
            // Arrange
            val id = "id"
            val shouldStream = true
            val expectedData = mockk<UserDto>()
            val expectedResult = Response.Success(expectedData)

            every { repositorySpy["streamLoggedUser"](id) } returns flowOf(expectedResult)

            // Call
            val result = repositorySpy.fetchLoggedUser(id, shouldStream)

            // Arrange
            assertEquals(expectedResult, result)
            verify { repositorySpy["streamLoggedUser"](id) }
        }

        @Test
        fun `documentFetch() should retrieve a single flow with the data when its found`() {
            // Arrange
            val id = "id"
            val shouldStream = false
            val params =
                DocumentParameters.create(mockk(relaxed = true)).setStream(shouldStream).setId(id)
                    .build()
            val firebaseRequest =
                FirebaseRequest.create(PersonalDataDto::class.java).setCollection(collectionRef)
                    .setParams(params).build()
            val expectedResult = flowOf(Response.Success(mockk<PersonalDataDto>()))

            every { repositorySpy["getDocument"](firebaseRequest) } returns expectedResult

            // Call
            val result = repositorySpy.documentFetch(firebaseRequest)

            // Assertions
            assertEquals(expectedResult, result)
            verify { repositorySpy["getDocument"](firebaseRequest) }

        }

        @Test
        fun `documentFetch() should retrieve a stream flow with the data when its found`() {
            // Arrange
            val id = "id"
            val shouldStream = true
            val params =
                DocumentParameters.create(mockk(relaxed = true)).setStream(shouldStream).setId(id)
                    .build()
            val firebaseRequest =
                FirebaseRequest.create(PersonalDataDto::class.java).setCollection(collectionRef)
                    .setParams(params).build()
            val expectedResult = flowOf(Response.Success(mockk<PersonalDataDto>()))

            every { repositorySpy["streamDocument"](firebaseRequest) } returns expectedResult

            // Call
            val result = repositorySpy.documentFetch(firebaseRequest)

            // Assertions
            assertEquals(expectedResult, result)
            verify { repositorySpy["streamDocument"](firebaseRequest) }

        }

        @Test
        fun `queryFetch() should retrieve a single flow with the data when its found`() {
            // Arrange
            val params = QueryParameters.create(mockk(relaxed = true)).setStream(false).setQueries(
                QuerySettings(Field.BUSINESS_CENTRAL_ID, QueryType.WHERE_EQUALS, "id")
            ).build()

            val firebaseRequest =
                FirebaseRequest.create(PersonalDataDto::class.java).setCollection(collectionRef)
                    .setParams(params).build()

            val expectedResult = flowOf(Response.Success(mockk<List<PersonalDataDto>>()))

            every { repositorySpy["getQuery"](firebaseRequest) } returns expectedResult

            // Call
            val result = repositorySpy.queryFetch(firebaseRequest)

            // Assertions
            assertEquals(expectedResult, result)
            verify { repositorySpy["getQuery"](firebaseRequest) }
        }

        @Test
        fun `queryFetch() should retrieve a stream flow with the data when its found`() {
            // Arrange
            val params = QueryParameters.create(mockk(relaxed = true)).setStream(true).setQueries(
                QuerySettings(Field.BUSINESS_CENTRAL_ID, QueryType.WHERE_EQUALS, "id")
            ).build()

            val firebaseRequest =
                FirebaseRequest.create(PersonalDataDto::class.java).setCollection(collectionRef)
                    .setParams(params).build()

            val expectedResult = Response.Success(mockk<List<PersonalDataDto>>())

            every { repositorySpy["streamQuery"](firebaseRequest) } returns flowOf(expectedResult)

            // Call
            val result = repositorySpy.documentFetch(firebaseRequest)

            // Assertions
            assertEquals(expectedResult, result)
            verify { repositorySpy["streamDocument"](firebaseRequest) }

        }
    */

}
