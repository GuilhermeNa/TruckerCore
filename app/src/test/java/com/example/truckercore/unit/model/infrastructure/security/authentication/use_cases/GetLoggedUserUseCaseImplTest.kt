package com.example.truckercore.unit.model.infrastructure.security.authentication.use_cases

import com.example.truckercore.model.infrastructure.security.authentication.entity.LoggedUser
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.GetLoggedUserUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.GetLoggedUserUseCaseImpl
import com.example.truckercore.model.modules.person.shared.person_details.GetPersonWithDetailsUseCase
import com.example.truckercore.model.modules.person.shared.person_details.PersonWithDetails
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.modules.user.use_cases.interfaces.GetUserUseCase
import com.example.truckercore.model.modules.vip.entity.Vip
import com.example.truckercore.model.modules.vip.use_cases.interfaces.GetVipUseCase
import com.example.truckercore.model.shared.errors.ObjectNotFoundException
import com.example.truckercore.model.shared.utils.sealeds.Response
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetLoggedUserUseCaseImplTest : KoinTest {

    private val getUser: GetUserUseCase by inject()
    private val getPersonDetails: GetPersonWithDetailsUseCase by inject()
    private val getVip: GetVipUseCase by inject()
    private val getLoggedUser: GetLoggedUserUseCase by inject()

    private val fbUid = "fbUid"
    private val user: User = mockk(relaxed = true)
    private val personWD: PersonWithDetails = mockk(relaxed = true)
    private val vips: List<Vip> = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        startKoin {
            modules(
                module {
                    single<GetUserUseCase> { mockk() }
                    single<GetPersonWithDetailsUseCase> { mockk() }
                    single<GetVipUseCase> { mockk() }
                    single<GetLoggedUserUseCase> {
                        GetLoggedUserUseCaseImpl(get(), get(), get())
                    }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()

    @Test
    fun `should return a Success when all data have been found`() = runTest {
        // Arrange
        val userFLow = flowOf(Response.Success(user))
        val personWDFlow = flowOf(Response.Success(personWD))
        val vipsFLow = flowOf(Response.Success(vips))
        val loggedUser = LoggedUser(user, personWD, vips)

        every { getUser.execute(fbUid) } returns userFLow
        every { getPersonDetails.execute(user) } returns personWDFlow
        every { getVip.execute(any()) } returns vipsFLow

        // Act
        val result = getLoggedUser.execute(fbUid).first()

        // Assert
        assertTrue(result is Response.Success)
        assertEquals(result.data, loggedUser)
        verifyOrder {
            getUser.execute(fbUid)
            getVip.execute(any())
            getPersonDetails.execute(user)
        }
    }

    @Test
    fun `should return a Success when vips are not found`() = runTest {
        // Arrange
        val userFLow = flowOf(Response.Success(user))
        val personWDFlow = flowOf(Response.Success(personWD))
        val vipsFLow = flowOf(Response.Empty)
        val loggedUser = LoggedUser(user, personWD, emptyList())

        every { getUser.execute(fbUid) } returns userFLow
        every { getPersonDetails.execute(user) } returns personWDFlow
        every { getVip.execute(any()) } returns vipsFLow

        // Act
        val result = getLoggedUser.execute(fbUid).first()

        // Assert
        assertTrue(result is Response.Success)
        assertEquals(result.data, loggedUser)
        verifyOrder {
            getUser.execute(fbUid)
            getVip.execute(any())
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
        verify(exactly = 1) { getUser.execute(fbUid) }
        verify(exactly = 0) { getVip.execute(any()) }
        verify(exactly = 0) {   getPersonDetails.execute(any()) }
    }

    @Test
    fun `should throw ObjectNotFoundException when the Person Detail search returns Empty`() =
        runTest {
            // Arrange
            val userFLow = flowOf(Response.Success(user))
            val personWDFlow = flowOf(Response.Empty)
            val vipsFLow = flowOf(Response.Success(vips))

            every { getUser.execute(fbUid) } returns userFLow
            every { getPersonDetails.execute(user) } returns personWDFlow
            every { getVip.execute(any()) } returns vipsFLow

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
            val vipsFLow = flowOf(Response.Success(vips))

            every { getUser.execute(fbUid) } returns userFLow
            every { getPersonDetails.execute(user) } returns personWDFlow
            every { getVip.execute(any()) } returns vipsFLow

            // Act && Assert
            assertThrows<ObjectNotFoundException> {
                getLoggedUser.execute(fbUid).first()
            }

        }


}