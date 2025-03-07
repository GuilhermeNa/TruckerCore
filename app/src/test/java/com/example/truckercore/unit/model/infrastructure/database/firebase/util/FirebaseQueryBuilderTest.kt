package com.example.truckercore.unit.model.infrastructure.database.firebase.util

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.database.firebase.util.FirebaseQueryBuilder
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

internal class FirebaseQueryBuilderTest : KoinTest {

    private val firestore: FirebaseFirestore by inject()
    private val queryBuilder: FirebaseQueryBuilder by inject()

    private val docReference: DocumentReference = mockk(relaxed = true)
    private val query: Query = mockk(relaxed = true)
    private val collectionName = Collection.USER

    @Test
    fun `newDocument() should get a document from firestore`() {
        // Arrange
        every {
            firestore.collection(collectionName.getName()).document()
        } returns docReference

        // Call
        val result = queryBuilder.createDocument(collectionName)

        // Assertions
        assertEquals(docReference, result)
        verify {
            firestore.collection(collectionName.getName()).document()
        }
    }

    @Test
    fun `getDocumentReference() should get a specific document from firestore`() {
        // Arrange
        val id = "id"

        // Behaviors
        every {
            firestore.collection(collectionName.getName()).document(id)
        } returns docReference

        // Call
        val result = queryBuilder.getDocument(collectionName, id)

        // Assertions
        assertEquals(docReference, result)
        verify { firestore.collection(collectionName.getName()).document(id) }
    }

    @Test
    fun `getQuery() should call correct query`() {
        // Arrange
        val field = Field.CUSTOMER_ID
        val value = "id"
        val querySettings = QuerySettings(field, QueryType.WHERE_EQUALS, value)

        every {
            firestore.collection(collectionName.getName()).whereEqualTo(field.getName(), value)
        } returns query

        // Call
        val result = queryBuilder.getQuery(collectionName, querySettings)

        // Assertions
        assertEquals(query, result)
        verify {
            firestore.collection(collectionName.getName()).whereEqualTo(field.getName(), value)
        }
    }

    @Test
    fun `getQuery(list of strings) should call correct query`() {
        // Arrange
        val field = Field.CUSTOMER_ID
        val value = listOf("id1", "id2")
        val querySettings = QuerySettings(field, QueryType.WHERE_IN, value)

        every {
            firestore.collection(collectionName.getName()).whereIn(field.getName(), value)
        } returns query

        // Call
        val result = queryBuilder.getQuery(collectionName, querySettings)

        // Assertions
        assertEquals(query, result)
        verify {
            firestore.collection(collectionName.getName()).whereIn(field.getName(), value)
        }
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            startKoin {
                modules(
                    module {
                        single<FirebaseFirestore> { mockk(relaxed = true) }
                        single { FirebaseQueryBuilder(get()) }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()

    }


}