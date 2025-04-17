package com.example.truckercore.unit.model.infrastructure.data_source.firebase.repository

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore._test_utils.mockStaticTask
import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.data_source.firebase.exceptions.IncompleteTaskException
import com.example.truckercore.model.infrastructure.data_source.firebase.repository.FirebaseRepository
import com.example.truckercore.model.infrastructure.data_source.firebase.repository.FirebaseRepositoryImpl
import com.example.truckercore.model.infrastructure.data_source.firebase.util.FirebaseConverter
import com.example.truckercore.model.infrastructure.data_source.firebase.util.FirebaseQueryBuilder
import com.example.truckercore.model.infrastructure.data_source.firebase.util.FirebaseRequest
import com.example.truckercore.model.modules.user.dto.UserDto
import com.example.truckercore.model.shared.modules.personal_data.dto.PersonalDataDto
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.parameters.QuerySettings
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreException.Code
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
internal class FirebaseRepositoryImplTest : KoinTest {

    // Injections
    private val queryBuilder: FirebaseQueryBuilder by inject()
    private val converter: FirebaseConverter by inject()
    private val repository: FirebaseRepository by inject()

    // Mocks
    private val docReference: DocumentReference = mockk(relaxed = true)
    private val docSnapShot: DocumentSnapshot = mockk()
    private val query: Query = mockk(relaxed = true)
    private val querySnapShot: QuerySnapshot = mockk()

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

    //----------------------------------------------------------------------------------------------
    // Testing createBlankDocument()
    //----------------------------------------------------------------------------------------------

    @Test
    fun `should call queryBuilder and create Document without path`() {
        // Arrange
        val collection = Collection.PERSONAL_DATA
        every { queryBuilder.createBlankDocument(collection) } returns docReference

        // Call
        val result = repository.createBlankDocument(collection)

        // Assertion
        assertEquals(result, docReference)
        verify { queryBuilder.createBlankDocument(collection) }
    }

    @Test
    fun `should call queryBuilder and create Document with path`() {
        // Arrange
        val collection = Collection.PERSONAL_DATA
        val path = "path"
        every { queryBuilder.createBlankDocument(collection, path) } returns docReference

        // Call
        val result = repository.createBlankDocument(collection, path)

        // Assertion
        assertEquals(result, docReference)
        verify { queryBuilder.createBlankDocument(collection, path) }
    }

    //----------------------------------------------------------------------------------------------
    // Testing createDocument()
    //----------------------------------------------------------------------------------------------

    @Test
    fun `should create the entity and return id`() = runTest {
        // Arrange
        val docRefId = "refId"
        val newDto: UserDto = mockk(relaxed = true)
        val dto: UserDto = mockk(relaxed = true) {
            every { initializeId(any()) } returns newDto
        }
        val task = mockk<Task<Void>> {
            every { exception } returns null
            every { isSuccessful } returns true
            every { isComplete } returns true
            every { isCanceled } returns false
            every { result } returns mockk(relaxed = true)
        }

        every { docReference.id } returns docRefId
        every { queryBuilder.createBlankDocument(any()) } returns docReference
        every { docReference.set(any()) } returns task
        every { task.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<Void>
            listener.onComplete(task)
            task
        }

        // Call && Assert
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            repository.create(Collection.USER, dto).collect { response ->
                assertTrue(response is Response.Success)
                assertEquals(response.data, docRefId)
                verify {
                    queryBuilder.createBlankDocument(Collection.USER)
                    docReference.set(newDto)
                }
            }
        }

    }

    @Test
    fun `should throw NullPointerException when an error occurs on firebase`() = runTest {
        // Arrange
        val docRefId = "refId"
        val newDto: UserDto = mockk(relaxed = true)
        val dto: UserDto = mockk(relaxed = true) {
            every { initializeId(any()) } returns newDto
        }
        val task = mockk<Task<Void>> {
            every { exception } returns NullPointerException("Simulated Error.")
            every { isSuccessful } returns true
            every { isComplete } returns true
            every { isCanceled } returns false
            every { result } returns null
        }

        every { docReference.id } returns docRefId
        every { queryBuilder.createBlankDocument(any()) } returns docReference
        every { docReference.set(any()) } returns task
        every { task.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<Void>
            listener.onComplete(task)
            task
        }

        // Call && Assert
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            assertThrows<NullPointerException> {
                repository.create(Collection.USER, dto).single()
                verify {
                    queryBuilder.createBlankDocument(Collection.USER)
                    docReference.set(newDto)
                }
            }

        }

    }

    @Test
    fun `should throw IncompleteTaskException when the task does not finish correctly`() = runTest {
        // Arrange
        val docRefId = "refId"
        val newDto: UserDto = mockk(relaxed = true)
        val dto: UserDto = mockk(relaxed = true) {
            every { initializeId(any()) } returns newDto
        }
        val task = mockk<Task<Void>> {
            every { exception } returns null
            every { isSuccessful } returns false
            every { isComplete } returns true
            every { isCanceled } returns false
            every { result } returns null
        }

        every { docReference.id } returns docRefId
        every { queryBuilder.createBlankDocument(any()) } returns docReference
        every { docReference.set(any()) } returns task
        every { task.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<Void>
            listener.onComplete(task)
            task
        }

        // Call && Assert
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            assertThrows<IncompleteTaskException> {
                repository.create(Collection.USER, dto).single()
                verify {
                    queryBuilder.createBlankDocument(Collection.USER)
                    docReference.set(newDto)
                }
            }

        }

    }

    //----------------------------------------------------------------------------------------------
    // Testing update()
    //----------------------------------------------------------------------------------------------

    @Test
    fun `should update the entity`() = runTest {
        // Arrange
        val docRefId = "refId"
        val dto: UserDto = mockk(relaxed = true) {
            every { id } returns docRefId
        }
        val task = mockk<Task<Void>> {
            every { exception } returns null
            every { isSuccessful } returns true
            every { isComplete } returns true
            every { isCanceled } returns false
            every { result } returns null
        }

        every { queryBuilder.getDocument(any(), any()) } returns docReference
        every { docReference.set(any()) } returns task
        every { task.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<Void>
            listener.onComplete(task)
            task
        }

        // Call && Assert
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            repository.update(Collection.USER, dto).collect { response ->
                assertTrue(response is Response.Success)
                assertEquals(response.data, Unit)
                verify {
                    queryBuilder.getDocument(Collection.USER, docRefId)
                    docReference.set(dto)
                }
            }
        }

    }

    @Test
    fun `should throw NullPointerException when an error occurs while updating on firebase`() =
        runTest {
            // Arrange
            val docRefId = "refId"
            val dto: UserDto = mockk(relaxed = true) {
                every { id } returns docRefId
            }
            val task = mockk<Task<Void>> {
                every { exception } returns NullPointerException("Simulated Exception.")
                every { isSuccessful } returns false
                every { isComplete } returns false
                every { isCanceled } returns false
                every { result } returns null
            }

            every { queryBuilder.getDocument(any(), any()) } returns docReference
            every { docReference.set(any()) } returns task
            every { task.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<Void>
                listener.onComplete(task)
                task
            }

            // Call && Assert
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                assertThrows<NullPointerException> {
                    repository.update(Collection.USER, dto).single()
                }
                verify {
                    queryBuilder.getDocument(Collection.USER, docRefId)
                    docReference.set(dto)
                    task.exception
                }
            }

        }

    @Test
    fun `should throw IncompleteTaskException when the task does not finish while updating`() =
        runTest {
            // Arrange
            val docRefId = "refId"
            val newDto: UserDto = mockk(relaxed = true)
            val dto: UserDto = mockk(relaxed = true) {
                every { initializeId(any()) } returns newDto
            }
            val task = mockk<Task<Void>> {
                every { exception } returns null
                every { isSuccessful } returns false
                every { isComplete } returns false
                every { isCanceled } returns false
                every { result } returns null
            }

            every { docReference.id } returns docRefId
            every { queryBuilder.createBlankDocument(any()) } returns docReference
            every { docReference.set(any()) } returns task
            every { task.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<Void>
                listener.onComplete(task)
                task
            }

            // Call && Assert
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                assertThrows<IncompleteTaskException> {
                    repository.create(Collection.USER, dto).single()
                    verify {
                        queryBuilder.getDocument(Collection.USER, docRefId)
                        docReference.set(newDto)
                        task.isSuccessful
                    }
                }

            }

        }

    //----------------------------------------------------------------------------------------------
    // Testing delete()
    //----------------------------------------------------------------------------------------------

    @Test
    fun `should delete the entity`() = runTest {
        // Arrange
        val docRefId = "refId"
        val task = mockk<Task<Void>> {
            every { exception } returns null
            every { isSuccessful } returns true
            every { isComplete } returns true
            every { isCanceled } returns false
            every { result } returns null
        }

        every { queryBuilder.getDocument(any(), any()) } returns docReference
        every { docReference.delete() } returns task
        every { task.addOnCompleteListener(any()) } answers {
            val listener = it.invocation.args[0] as OnCompleteListener<Void>
            listener.onComplete(task)
            task
        }

        // Call && Assert
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            repository.delete(Collection.USER, docRefId).collect { response ->
                assertTrue(response is Response.Success)
                assertEquals(response.data, Unit)
                verify {
                    queryBuilder.getDocument(Collection.USER, docRefId)
                    docReference.delete()
                    task.isSuccessful
                }
            }
        }

    }

    @Test
    fun `should throw NullPointerException when an error occurs while deleting on firebase`() =
        runTest {
            // Arrange
            val docRefId = "refId"
            val task = mockk<Task<Void>> {
                every { exception } returns NullPointerException("Simulated Exception.")
                every { isSuccessful } returns false
                every { isComplete } returns false
                every { isCanceled } returns false
                every { result } returns null
            }

            every { queryBuilder.getDocument(any(), any()) } returns docReference
            every { docReference.delete() } returns task
            every { task.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<Void>
                listener.onComplete(task)
                task
            }

            // Call && Assert
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                assertThrows<NullPointerException> {
                    repository.delete(Collection.USER, docRefId).single()
                    verify {
                        queryBuilder.getDocument(Collection.USER, docRefId)
                        docReference.delete()
                        task.exception
                    }
                }
            }

        }

    @Test
    fun `should throw IncompleteTaskException when the task does not finish while deleting`() =
        runTest {
            // Arrange
            val docRefId = "refId"
            val task = mockk<Task<Void>> {
                every { exception } returns null
                every { isSuccessful } returns false
                every { isComplete } returns false
                every { isCanceled } returns false
                every { result } returns null
            }

            every { queryBuilder.getDocument(any(), any()) } returns docReference
            every { docReference.delete() } returns task
            every { task.addOnCompleteListener(any()) } answers {
                val listener = it.invocation.args[0] as OnCompleteListener<Void>
                listener.onComplete(task)
                task
            }

            // Call && Assert
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                assertThrows<IncompleteTaskException> {
                    repository.delete(Collection.USER, docRefId).single()
                    verify {
                        queryBuilder.getDocument(Collection.USER, docRefId)
                        docReference.delete()
                        task.isSuccessful
                    }
                }
            }

        }

    //----------------------------------------------------------------------------------------------
    // Testing entityExists()
    //----------------------------------------------------------------------------------------------

    @Test
    fun `should check existence of the entity`() = runTest {
        // Arrange
        val docRefId = "refId"
        val task = mockk<Task<DocumentSnapshot>> {
            every { exception } returns null
            every { isSuccessful } returns true
            every { isComplete } returns true
            every { isCanceled } returns false
            every { result } returns docSnapShot
        }

        every { queryBuilder.getDocument(any(), any()) } returns docReference
        every { docReference.get() } returns task
        every { docSnapShot.exists() } returns true

        // Call && Assert
        repository.entityExists(Collection.USER, docRefId).collect { response ->
            assertTrue(response is Response.Success)
            assertEquals(response.data, Unit)
            verify {
                queryBuilder.getDocument(Collection.USER, docRefId)
                docReference.get()
                docSnapShot.exists()
            }
        }

    }

    @Test
    fun `should return Empty when check existence of the entity does not found result`() =
        runTest {
            // Arrange
            val docRefId = "refId"
            val task = mockk<Task<DocumentSnapshot>> {
                every { exception } returns null
                every { isSuccessful } returns true
                every { isComplete } returns true
                every { isCanceled } returns false
                every { result } returns docSnapShot
            }

            every { queryBuilder.getDocument(any(), any()) } returns docReference
            every { docReference.get() } returns task
            every { docSnapShot.exists() } returns false

            // Call && Assert
            repository.entityExists(Collection.USER, docRefId).collect { response ->
                assertTrue(response is Response.Empty)
                verify {
                    queryBuilder.getDocument(Collection.USER, docRefId)
                    docReference.get()
                    docSnapShot.exists()
                }
            }

        }

    //----------------------------------------------------------------------------------------------
    // Testing fetchLoggedUser()
    //----------------------------------------------------------------------------------------------

    @Test
    fun `should return the loggedUser when search a single result`() = runTest {
        // Arrange
        val userId = "refId"
        val stream = false
        val userDto: UserDto = mockk(relaxed = true)
        val task = mockk<Task<DocumentSnapshot>> {
            every { exception } returns null
            every { isSuccessful } returns true
            every { isComplete } returns true
            every { isCanceled } returns false
            every { result } returns docSnapShot
        }

        every { queryBuilder.getDocument(any(), any()) } returns docReference
        every { docReference.get() } returns task
        every { docSnapShot.exists() } returns true
        every {
            converter.processDocumentSnapShot(docSnapShot, UserDto::class.java)
        } returns Response.Success(userDto)

        // Call && Assert
        repository.fetchLoggedUser(userId, stream).collect { response ->
            assertTrue(response is Response.Success)
            assertEquals(response.data, userDto)
            verifyOrder {
                queryBuilder.getDocument(Collection.USER, userId)
                docReference.get()
                converter.processDocumentSnapShot(docSnapShot, UserDto::class.java)
            }
        }

    }

    @Test
    fun `should return the loggedUser when search a stream result`() = runTest {
        // Arrange
        val userId = "refId"
        val stream = true
        val userDto: UserDto = mockk(relaxed = true)
        val listenerRegistration: ListenerRegistration = mockk()

        every { queryBuilder.getDocument(any(), any()) } returns docReference
        every { docReference.addSnapshotListener(any()) } answers {
            val listener = it.invocation.args[0] as EventListener<DocumentSnapshot>
            listener.onEvent(docSnapShot, null)
            listenerRegistration
        }
        every {
            converter.processDocumentSnapShot(docSnapShot, UserDto::class.java)
        } returns Response.Success(userDto)

        // Call && Assert
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            repository.fetchLoggedUser(userId, stream).collect { response ->
                assertTrue(response is Response.Success)
                assertEquals(response.data, userDto)
                verifyOrder {
                    queryBuilder.getDocument(Collection.USER, userId)
                    docReference.addSnapshotListener(any())
                    converter.processDocumentSnapShot(docSnapShot, UserDto::class.java)
                }
            }
        }
    }

    @Test
    fun `should throw ExceptionInInitializerError when an error occurred on firebase`() = runTest {
        // Arrange
        val userId = "refId"
        val stream = true
        val listenerRegistration: ListenerRegistration = mockk()

        every { queryBuilder.getDocument(any(), any()) } returns docReference
        every { docReference.addSnapshotListener(any()) } answers {
            val listener = it.invocation.args[0] as EventListener<DocumentSnapshot>
            listener.onEvent(null, FirebaseFirestoreException("Simulated Error.", Code.CANCELLED))
            listenerRegistration
        }

        // Call && Assert
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            assertThrows<ExceptionInInitializerError> {
                repository.fetchLoggedUser(userId, stream).single()
                verifyOrder {
                    queryBuilder.getDocument(Collection.USER, userId)
                    docReference.addSnapshotListener(any())
                }
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // Testing documentFetch()
    //----------------------------------------------------------------------------------------------

    @Test
    fun `should return the correct document data when search a single result`() = runTest {
        // Arrange
        val stream = false
        val docId = "id"
        val docParams: DocumentParameters = mockk(relaxed = true) {
            every { id } returns docId
            every { shouldStream } returns stream
        }
        val fbRequest: FirebaseRequest<PersonalDataDto> = mockk(relaxed = true) {
            every { getDocumentParams() } returns docParams
            every { shouldObserve() } returns stream
        }
        val pDataDto: PersonalDataDto = mockk(relaxed = true)
        val task = mockk<Task<DocumentSnapshot>> {
            every { exception } returns null
            every { isSuccessful } returns true
            every { isComplete } returns true
            every { isCanceled } returns false
            every { result } returns docSnapShot
        }

        every { queryBuilder.getDocument(any(), any()) } returns docReference
        every { docReference.get() } returns task
        every {
            converter.processDocumentSnapShot(any(), fbRequest.clazz)
        } returns Response.Success(pDataDto)

        // Act
        val result = repository.documentFetch(fbRequest).single()

        // Assertions
        assertTrue(result is Response.Success)
        assertEquals(result.data, pDataDto)
        verifyOrder {
            fbRequest.shouldObserve()
            fbRequest.getDocumentParams()
            queryBuilder.getDocument(fbRequest.collection, docId)
            docReference.get()
            converter.processDocumentSnapShot(docSnapShot, fbRequest.clazz)
        }
    }

    @Test
    fun `should return the correct document data when search a stream result`() = runTest {
        // Arrange
        val stream = true
        val docId = "id"
        val docParams: DocumentParameters = mockk(relaxed = true) {
            every { id } returns docId
            every { shouldStream } returns stream
        }
        val fbRequest: FirebaseRequest<PersonalDataDto> = mockk(relaxed = true) {
            every { getDocumentParams() } returns docParams
            every { shouldObserve() } returns stream
        }
        val pDataDto: PersonalDataDto = mockk(relaxed = true)
        val listenerRegistration: ListenerRegistration = mockk()

        every { queryBuilder.getDocument(any(), any()) } returns docReference
        every { docReference.addSnapshotListener(any()) } answers {
            val listener = it.invocation.args[0] as EventListener<DocumentSnapshot>
            listener.onEvent(docSnapShot, null)
            listenerRegistration
        }
        every {
            converter.processDocumentSnapShot(any(), fbRequest.clazz)
        } returns Response.Success(pDataDto)

        // Act && Assert
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            repository.documentFetch(fbRequest).collect() { result ->
                assertTrue(result is Response.Success)
                assertEquals(result.data, pDataDto)
                verifyOrder {
                    fbRequest.shouldObserve()
                    fbRequest.getDocumentParams()
                    queryBuilder.getDocument(fbRequest.collection, docId)
                    docReference.addSnapshotListener(any())
                    converter.processDocumentSnapShot(docSnapShot, fbRequest.clazz)
                }
            }
        }
    }

    @Test
    fun `should throw ExceptionInInitializerError when an error occurred while streaming document`() =
        runTest {
            // Arrange
            val stream = true
            val docId = "id"
            val docParams: DocumentParameters = mockk(relaxed = true) {
                every { id } returns docId
                every { shouldStream } returns stream
            }
            val fbRequest: FirebaseRequest<PersonalDataDto> = mockk(relaxed = true) {
                every { getDocumentParams() } returns docParams
                every { shouldObserve() } returns stream
            }
            val listenerRegistration: ListenerRegistration = mockk()

            every { queryBuilder.getDocument(any(), any()) } returns docReference
            every { docReference.addSnapshotListener(any()) } answers {
                val listener = it.invocation.args[0] as EventListener<DocumentSnapshot>
                listener.onEvent(null, mockk())
                listenerRegistration
            }

            // Act && Assert
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                assertThrows<Exception> {
                    repository.documentFetch(fbRequest).single()
                }
                verifyOrder {
                    fbRequest.shouldObserve()
                    fbRequest.getDocumentParams()
                    queryBuilder.getDocument(fbRequest.collection, docId)
                    docReference.addSnapshotListener(any())
                }
            }
        }

    //----------------------------------------------------------------------------------------------
    // Testing queryFetch()
    //----------------------------------------------------------------------------------------------

    @Test
    fun `should return the correct query data when search a single result`() = runTest {
        // Arrange
        val stream = false
        val querySettings: QuerySettings = mockk(relaxed = true)
        val queryParams: QueryParameters = mockk(relaxed = true) {
            every { queries } returns arrayOf(querySettings)
            every { shouldStream } returns stream
        }
        val fbRequest: FirebaseRequest<PersonalDataDto> = mockk(relaxed = true) {
            every { getQueryParams() } returns queryParams
            every { shouldObserve() } returns stream
        }
        val pDataDto: PersonalDataDto = mockk(relaxed = true)
        val task = mockk<Task<QuerySnapshot>> {
            every { exception } returns null
            every { isSuccessful } returns true
            every { isComplete } returns true
            every { isCanceled } returns false
            every { result } returns querySnapShot
        }

        every { queryBuilder.getQuery(any(), any()) } returns query
        every { query.get() } returns task
        every {
            converter.processQuerySnapShot(any(), fbRequest.clazz)
        } returns Response.Success(listOf(pDataDto))

        // Act
        val result = repository.queryFetch(fbRequest).single()

        // Assertions
        assertTrue(result is Response.Success)
        assertEquals(result.data.first(), pDataDto)
        verifyOrder {
            fbRequest.shouldObserve()
            fbRequest.getQueryParams()
            queryBuilder.getQuery(fbRequest.collection, querySettings)
            query.get()
            converter.processQuerySnapShot(querySnapShot, fbRequest.clazz)
        }
    }

    @Test
    fun `should return the correct query data when search a stream result`() = runTest {
        val stream = true
        val querySettings: QuerySettings = mockk(relaxed = true)
        val queryParams: QueryParameters = mockk(relaxed = true) {
            every { queries } returns arrayOf(querySettings)
            every { shouldStream } returns stream
        }
        val fbRequest: FirebaseRequest<PersonalDataDto> = mockk(relaxed = true) {
            every { getQueryParams() } returns queryParams
            every { shouldObserve() } returns stream
        }
        val pDataDto: PersonalDataDto = mockk(relaxed = true)
        val listenerRegistration: ListenerRegistration = mockk()

        every { queryBuilder.getQuery(any(), any()) } returns query
        every { query.addSnapshotListener(any()) } answers {
            val listener = it.invocation.args[0] as EventListener<QuerySnapshot>
            listener.onEvent(querySnapShot, null)
            listenerRegistration
        }
        every {
            converter.processQuerySnapShot(any(), fbRequest.clazz)
        } returns Response.Success(listOf(pDataDto))

        // Act && Assert
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            repository.queryFetch(fbRequest).collect { response ->
                assertTrue(response is Response.Success)
                assertEquals(response.data.first(), pDataDto)
                verifyOrder {
                    fbRequest.shouldObserve()
                    fbRequest.getQueryParams()
                    queryBuilder.getQuery(fbRequest.collection, querySettings)
                    query.addSnapshotListener(any())
                    converter.processQuerySnapShot(querySnapShot, fbRequest.clazz)
                }
            }
        }
    }

    @Test
    fun `should throw ExceptionInInitializerError when an error occurred while streaming query`() =
        runTest {
            // Arrange
            val stream = true
            val querySettings: QuerySettings = mockk(relaxed = true)
            val queryParams: QueryParameters = mockk(relaxed = true) {
                every { queries } returns arrayOf(querySettings)
                every { shouldStream } returns stream
            }
            val fbRequest: FirebaseRequest<PersonalDataDto> = mockk(relaxed = true) {
                every { getQueryParams() } returns queryParams
                every { shouldObserve() } returns stream
            }
            val listenerRegistration: ListenerRegistration = mockk()

            every { queryBuilder.getQuery(any(), any()) } returns query
            every { query.addSnapshotListener(any()) } answers {
                val listener = it.invocation.args[0] as EventListener<QuerySnapshot>
                listener.onEvent(null, mockk())
                listenerRegistration
            }

            // Act && Assert
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                assertThrows<Exception> {
                    repository.queryFetch(fbRequest).single()
                }
                verifyOrder {
                    fbRequest.shouldObserve()
                    fbRequest.getQueryParams()
                    queryBuilder.getQuery(fbRequest.collection, querySettings)
                    query.addSnapshotListener(any())
                }
            }
        }

}
