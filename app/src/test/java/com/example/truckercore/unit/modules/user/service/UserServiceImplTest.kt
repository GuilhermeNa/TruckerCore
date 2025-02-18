package com.example.truckercore.unit.modules.user.service

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.user.service.UserService
import com.example.truckercore.modules.user.service.UserServiceImpl
import com.example.truckercore.modules.user.use_cases.interfaces.AggregateUserWithPersonUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
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

class UserServiceImplTest : KoinTest {

    private val getUserWithPerson: AggregateUserWithPersonUseCase by inject()
    private val userService: UserService by inject()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<AggregateUserWithPersonUseCase> { mockk() }
                        single<UserService> { UserServiceImpl(mockk(), get()) }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()

    }

    @Test
    fun `fetchLoggedUserWithPerson should call getUserWithPerson use case`() =
        runTest {
            // Arrange
            val userId = "userId"
            val shouldStream = true

            every { getUserWithPerson.execute(userId, shouldStream) } returns flowOf(mockk())

            // Call
            userService.fetchLoggedUserWithPerson(userId, shouldStream).single()

            // Assertions

            verify(exactly = 1) { getUserWithPerson.execute(userId, shouldStream) }
        }
}