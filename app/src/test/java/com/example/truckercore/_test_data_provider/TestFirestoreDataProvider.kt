package com.example.truckercore._test_data_provider

import com.example.truckercore._test_data_provider.fake_objects.FakeDto
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import io.mockk.every
import io.mockk.mockk

class TestFirestoreDataProvider {

    fun docReference(
        docId: String = "id",
        get: Task<DocumentSnapshot>? = null
    ) = mockk<DocumentReference>(relaxed = true) {
        every { id } returns docId
        get?.let { every { get() } returns it }
    }

    fun query(
        get: Task<QuerySnapshot>? = null
    ) = mockk<Query>(relaxed = true) {
        get?.let { every { get() } returns it }
    }

    fun docSnapMock(
        exists: Boolean = true,
        converter: Class<FakeDto> = FakeDto::class.java,
        fakeDto: FakeDto? = null
    ): DocumentSnapshot =
        mockk<DocumentSnapshot>(relaxed = true) {
            every { exists() } returns exists
            every { toObject(converter) } returns fakeDto
        }

    fun queryDocSnapMock(
        converter: Class<FakeDto> = FakeDto::class.java,
        fakeDto: FakeDto
    ): QueryDocumentSnapshot {
        val queryDocSnapMock = mockk<QueryDocumentSnapshot>(relaxed = true)
        every { queryDocSnapMock.exists() } returns true
        every { queryDocSnapMock.toObject(converter) } returns fakeDto
        return queryDocSnapMock
    }

    fun querySnapMock(
        isEmpty: Boolean = false,
        docs: List<QueryDocumentSnapshot>? = null
    ): QuerySnapshot {
        val querySnap = mockk<QuerySnapshot>(relaxed = true)
        every { querySnap.isEmpty } returns isEmpty
        if (docs != null) {
            every { querySnap.iterator() } returns docs.toMutableList().iterator()
        }
        return querySnap
    }

    fun <T> taskMock(
        exc: Exception? = null,
        success: Boolean = true,
        complete: Boolean = true,
        canceled: Boolean = false,
        res: T? = null
    ): Task<T> {
        val task = mockk<Task<T>> {
            every { exception } returns exc
            every { isSuccessful } returns success
            every { isComplete } returns complete
            every { isCanceled } returns canceled
            every { result } returns res
        }
        return task
    }

}