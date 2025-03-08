package com.example.truckercore.unit.model.infrastructure.database.firebase.repository

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore._test_utils.mockStaticTask
import com.example.truckercore.model.configs.app_constants.Collection
import com.example.truckercore.model.infrastructure.database.firebase.repository.FirebaseRepository
import com.example.truckercore.model.infrastructure.database.firebase.repository.FirebaseRepositoryImpl
import com.example.truckercore.model.infrastructure.database.firebase.util.FirebaseConverter
import com.example.truckercore.model.infrastructure.database.firebase.util.FirebaseQueryBuilder
import com.example.truckercore.model.modules.user.dto.UserDto
import com.example.truckercore.model.shared.utils.sealeds.Response
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
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

    @Test
    fun `create() should create the entity and return id`() = runTest {
        // Arrange


        val mockFlow = callbackFlow<Response<String>> {
            trySend(Response.Success(id))
            awaitClose { }
        }

        every { queryBuilder.createDocument(any()) } returns docReference
        every { docReference.id } returns id
        coEvery { docReference.set(any()).addOnCompleteListener(mockk()) } returns mockk()

        // Call


        // Assertions

    }

}
