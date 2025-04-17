package com.example.truckercore.unit.model.infrastructure.security.authentication.use_cases

import com.example.truckercore.model.infrastructure.security.authentication.entity.SessionInfo
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.GetSessionInfoUseCase
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.implementations.GetSessionInfoUseCaseImpl
import com.example.truckercore.model.modules.business_central.entity.BusinessCentral
import com.example.truckercore.model.modules.business_central.use_cases.interfaces.GetBusinessCentralUseCase
import com.example.truckercore.model.modules.person.shared.person_details.GetPersonWithDetailsUseCase
import com.example.truckercore.model.modules.person.shared.person_details.PersonWithDetails
import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.modules.user.use_cases.interfaces.GetUserUseCase
import com.example.truckercore.model.modules.vip.data.Vip
import com.example.truckercore.model.modules.vip.use_cases.interfaces.GetVipUseCase
import com.example.truckercore.model.shared.errors.ObjectNotFoundException
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
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

class GetSessionInfoUseCaseImplTest : KoinTest {

    private val getUser: GetUserUseCase by inject()
    private val getPersonDetails: GetPersonWithDetailsUseCase by inject()
    private val getVip: GetVipUseCase by inject()
    private val getCentral: GetBusinessCentralUseCase by inject()
    private val useCase: GetSessionInfoUseCase by inject()

    private val fbUid = "fbUid"
    private val centralId = "centralId"
    private val user: User = mockk(relaxed = true) { every { businessCentralId } returns centralId }
    private val central: BusinessCentral = mockk(relaxed = true) { every { id } returns centralId }
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
                    single<GetBusinessCentralUseCase> { mockk() }
                    single<GetSessionInfoUseCase> {
                        GetSessionInfoUseCaseImpl(get(), get(), get(), get())
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
        val centralFlow = flowOf(Response.Success(central))
        val personWDFlow = flowOf(Response.Success(personWD))
        val vipsFLow = flowOf(Response.Success(vips))
        val sessionInfo = SessionInfo(user, central, personWD, vips)

        every { getUser.execute(fbUid) } returns userFLow
        every { getCentral.execute(any() as DocumentParameters) } returns centralFlow
        every { getPersonDetails.execute(user) } returns personWDFlow
        every { getVip.execute(any()) } returns vipsFLow

        // Act
        val result = useCase.execute(fbUid).first()

        // Assert
        assertTrue(result is Response.Success)
        assertEquals(result.data, sessionInfo)
        verifyOrder {
            getUser.execute(fbUid)
            getVip.execute(any())
            getPersonDetails.execute(user)
            getCentral.execute(any() as DocumentParameters)
        }
    }

    @Test
    fun `should return a Success when vips are not found`() = runTest {
        // Arrange
        val userFLow = flowOf(Response.Success(user))
        val centralFlow = flowOf(Response.Success(central))
        val personWDFlow = flowOf(Response.Success(personWD))
        val vipsFLow = flowOf(Response.Empty)
        val sessionInfo = SessionInfo(user, central, personWD, emptyList())

        every { getUser.execute(fbUid) } returns userFLow
        every { getCentral.execute(any() as DocumentParameters) } returns centralFlow
        every { getPersonDetails.execute(user) } returns personWDFlow
        every { getVip.execute(any()) } returns vipsFLow

        // Act
        val result = useCase.execute(fbUid).first()

        // Assert
        assertTrue(result is Response.Success)
        assertEquals(result.data, sessionInfo)
        verifyOrder {
            getUser.execute(fbUid)
            getVip.execute(any())
            getPersonDetails.execute(user)
            getCentral.execute(any() as DocumentParameters)
        }
    }

    @Test
    fun `should return an Empty response flow when the user was not found`() = runTest {
        // Arrange
        val userFLow = flowOf(Response.Empty)

        every { getUser.execute(fbUid) } returns userFLow

        // Act
        val result = useCase.execute(fbUid).first()

        // Assert
        assertTrue(result is Response.Empty)
        verify(exactly = 1) { getUser.execute(fbUid) }
        verify(exactly = 0) { getVip.execute(any()) }
        verify(exactly = 0) { getPersonDetails.execute(any()) }
    }

    @Test
    fun `should throw ObjectNotFoundException when the Person Detail search returns Empty`() =
        runTest {
            // Arrange
            val userFLow = flowOf(Response.Success(user))
            val personWDFlow = flowOf(Response.Empty)
            val centralFlow = flowOf(Response.Success(central))
            val vipsFLow = flowOf(Response.Success(vips))

            every { getUser.execute(fbUid) } returns userFLow
            every { getPersonDetails.execute(user) } returns personWDFlow
            every { getVip.execute(any()) } returns vipsFLow
            every { getCentral.execute(any() as DocumentParameters) } returns centralFlow

            // Act && Assert
            assertThrows<ObjectNotFoundException> {
                useCase.execute(fbUid).first()
            }

        }

    @Test
    fun `should throw ObjectNotFoundException when the Person Detail search returns Error`() =
        runTest {
            // Arrange
            val userFLow = flowOf(Response.Success(user))
            val personWDFlow = flowOf(Response.Error(NullPointerException()))
            val vipsFLow = flowOf(Response.Success(vips))
            val centralFlow = flowOf(Response.Success(central))

            every { getUser.execute(fbUid) } returns userFLow
            every { getPersonDetails.execute(user) } returns personWDFlow
            every { getVip.execute(any()) } returns vipsFLow
            every { getCentral.execute(any() as DocumentParameters) } returns centralFlow

            // Act && Assert
            assertThrows<ObjectNotFoundException> {
                useCase.execute(fbUid).first()
            }

        }

    @Test
    fun `should throw ObjectNotFoundException when the Business Central search returns Error`() =
        runTest {
            // Arrange
            val userFLow = flowOf(Response.Success(user))
            val centralFlow = flowOf(Response.Empty)
            val personWDFlow = flowOf(Response.Success(personWD))
            val vipsFLow = flowOf(Response.Success(vips))

            every { getUser.execute(fbUid) } returns userFLow
            every { getCentral.execute(any() as DocumentParameters) } returns centralFlow
            every { getPersonDetails.execute(user) } returns personWDFlow
            every { getVip.execute(any()) } returns vipsFLow

            // Act && Assert
            assertThrows<ObjectNotFoundException> {
                useCase.execute(fbUid).first()
            }

        }

}