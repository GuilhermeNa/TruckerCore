package com.example.truckercore.unit.model.infrastructure.security.authentication.use_cases

import com.example.truckercore.infrastructure.security.authentication.entity.LoggedUserDetails
import com.example.truckercore.infrastructure.security.authentication.use_cases.GetLoggedUserDetailsUseCase
import com.example.truckercore.infrastructure.security.authentication.use_cases.GetLoggedUserDetailsUseCaseImpl
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.use_cases.interfaces.GetUserUseCase
import com.example.truckercore.shared.errors.ObjectNotFoundException
import com.example.truckercore.modules.person.shared.person_details.GetPersonWithDetailsUseCase
import com.example.truckercore.modules.person.shared.person_details.PersonWithDetails
import com.example.truckercore.shared.utils.sealeds.Response
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
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

class GetLoggedUserDetailsUseCaseImplTest : KoinTest {

    private val getUser: GetUserUseCase by inject()
    private val getPersonDetails: GetPersonWithDetailsUseCase by inject()
    private val getLoggedUser: GetLoggedUserDetailsUseCase by inject()

    private val fbUid = "fbUid"
    private val user: User = mockk(relaxed = true)
    private val personWD: PersonWithDetails = mockk(relaxed = true)

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            startKoin {
                modules(
                    module {
                        single<GetUserUseCase> { mockk() }
                        single<GetPersonWithDetailsUseCase> { mockk() }
                        single<GetLoggedUserDetailsUseCase> {
                            GetLoggedUserDetailsUseCaseImpl(get(), get())
                        }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()

    }

    @Test
    fun `should return a Success response flow with the Logged User`() = runTest {
        // Arrange
        val userFLow = flowOf(Response.Success(user))
        val personWDFlow = flowOf(Response.Success(personWD))
        val loggedUser = LoggedUserDetails(user, personWD)

        every { getUser.execute(fbUid) } returns userFLow
        every { getPersonDetails.execute(user) } returns personWDFlow

        // Act
        val result = getLoggedUser.execute(fbUid).first()

        // Assert
        assertTrue(result is Response.Success)
        assertEquals(result.data, loggedUser)
        verifyOrder {
            getUser.execute(fbUid)
            getPersonDetails.execute(user)
        }
    }

    @Test
    fun `should return an Empty response flow when the user was not found`() = runTest {
        // Arrange
        val userFLow = flowOf(Response.Empty)

        every { getUser.execute(fbUid) } returns userFLow

        // Act
        val result = getLoggedUser.execute(fbUid).first()

        // Assert
        assertTrue(result is Response.Empty)
        verify {
            getUser.execute(fbUid)
        }
    }

    @Test
    fun `should throw ObjectNotFoundException when the Person Detail search returns Empty`() =
        runTest {
            // Arrange
            val userFLow = flowOf(Response.Success(user))
            val personWDFlow = flowOf(Response.Empty)

            every { getUser.execute(fbUid) } returns userFLow
            every { getPersonDetails.execute(user) } returns personWDFlow

            // Act && Assert
            assertThrows<ObjectNotFoundException> {
                getLoggedUser.execute(fbUid).first()
            }

        }

    @Test
    fun `should throw ObjectNotFoundException when the Person Detail search returns Error`() =
        runTest {
            // Arrange
            val userFLow = flowOf(Response.Success(user))
            val personWDFlow = flowOf(Response.Error(NullPointerException()))

            every { getUser.execute(fbUid) } returns userFLow
            every { getPersonDetails.execute(user) } returns personWDFlow

            // Act && Assert
            assertThrows<ObjectNotFoundException> {
                getLoggedUser.execute(fbUid).first()
            }

        }

}