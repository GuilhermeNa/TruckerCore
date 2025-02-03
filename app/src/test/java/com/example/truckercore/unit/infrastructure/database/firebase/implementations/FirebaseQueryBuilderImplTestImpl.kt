package com.example.truckercore.unit.infrastructure.database.firebase.implementations

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.infrastructure.database.firebase.implementations.FirebaseQueryBuilderImpl
import com.example.truckercore.shared.enums.QueryType
import com.example.truckercore.shared.utils.parameters.QuerySettings
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class FirebaseQueryBuilderImplTestImpl {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var queryBuilder: FirebaseQueryBuilderImpl
    private lateinit var documentReference: DocumentReference
    private lateinit var query: Query
    private val collectionName = Collection.PERSONAL_DATA.getName()

    @BeforeEach
    fun setup() {
        firestore = mockk()
        queryBuilder = FirebaseQueryBuilderImpl(firestore)
        documentReference = mockk()
        query = mockk()
    }

    @Test
    fun `newDocument() should call document from firestore`() {
        // Objects
        val collectionName = Collection.PERSONAL_DATA.getName()
        val documentReference = mockk<DocumentReference>()

        // Behaviors
        every {
            firestore.collection(collectionName).document()
        } returns documentReference

        // Call
        val result = queryBuilder.newDocument(collectionName)

        // Assertions
        verify { firestore.collection(collectionName).document() }
    }

    @Test
    fun `getDocumentReference() should call correct document`() {
        // Objects
        val id = "id"

        // Behaviors
        every {
            firestore.collection(collectionName).document(id)
        } returns documentReference

        // Call
        val result = queryBuilder.getDocumentReference(collectionName, id)

        // Assertions
        assertEquals(documentReference, result)
        verify { firestore.collection(collectionName).document(id) }
    }

    @Test
    fun `getQuery() should call correct query`() {
        // Object
        val field = Field.CUSTOMER_ID.getName()
        val value = "customerId"

        // Behaviors
        every {
            firestore.collection(collectionName).whereEqualTo(field, value)
        } returns query

        // Call
        val result = queryBuilder.getQuery(collectionName, field, value)

        // Assertions
        assertEquals(query, result)
        verify { firestore.collection(collectionName).whereEqualTo(eq(field), eq(value)) }
    }

    @Test
    fun `getQuery(list of strings) should call correct query`() {
        // Object
        val field = Field.CUSTOMER_ID.getName()
        val values = listOf("id1", "id2")

        // Behaviors
        every {
            firestore.collection(collectionName).whereIn(field, values)
        } returns query

        // Call
        val result = queryBuilder.getQuery(collectionName, field, values)

        // Assertions
        assertEquals(query, result)
        verify { firestore.collection(collectionName).whereIn(eq(field), eq(values)) }
    }

}