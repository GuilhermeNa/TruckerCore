package com.example.truckercore._test_data_provider

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import io.mockk.every
import io.mockk.mockk

class TestFirebaseAuthProvider {

    val firebaseUserMock: FirebaseUser = mockk(relaxed = true)

    // Void Task Result
    fun mockSuccessfulVoidTask(): Task<Void> {
        val task: Task<Void> = mockk(relaxed = true)
        every { task.isSuccessful } returns true
        every { task.exception } returns null
        every { task.addOnCompleteListener(any()) } answers {
            val listener = arg<OnCompleteListener<Void>>(0)
            listener.onComplete(task)
            task
        }
        return task
    }

    fun mockUnsuccessfulVoidTask(): Task<Void> {
        val task: Task<Void> = mockk(relaxed = true)
        every { task.isSuccessful } returns false
        every { task.exception } returns null
        every { task.addOnCompleteListener(any()) } answers {
            val listener = arg<OnCompleteListener<Void>>(0)
            listener.onComplete(task)
            task
        }
        return task
    }

    fun mockErrorVoidTask(givenException: Exception): Task<Void> {
        val task: Task<Void> = mockk(relaxed = true)
        every { task.isSuccessful } returns true
        every { task.exception } returns givenException
        every { task.addOnCompleteListener(any()) } answers {
            val listener = arg<OnCompleteListener<Void>>(0)
            listener.onComplete(task)
            task
        }
        return task
    }

    // Auth Task Result
    fun mockSuccessfulAuthTask(): Task<AuthResult> {
        val task = mockk<Task<AuthResult>>(relaxed = true)
        every { task.isSuccessful } returns true
        every { task.exception } returns null
        every { task.addOnCompleteListener(any()) } answers {
            val listener = arg<OnCompleteListener<AuthResult>>(0)
            listener.onComplete(task)
            task
        }
        return task
    }

    fun mockUnsuccessfulAuthTask(): Task<AuthResult> {
        val task = mockk<Task<AuthResult>>(relaxed = true)
        every { task.isSuccessful } returns false
        every { task.exception } returns null
        every { task.addOnCompleteListener(any()) } answers {
            val listener = arg<OnCompleteListener<AuthResult>>(0)
            listener.onComplete(task)
            task
        }
        return task
    }

    fun mockErrorAuthTask(givenException: Exception): Task<AuthResult> {
        val task = mockk<Task<AuthResult>>(relaxed = true)
        every { task.isSuccessful } returns false
        every { task.exception } returns givenException
        every { task.addOnCompleteListener(any()) } answers {
            val listener = arg<OnCompleteListener<AuthResult>>(0)
            listener.onComplete(task)
            task
        }
        return task
    }

}