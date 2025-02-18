package com.example.truckercore.unit.modules.user.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.employee.admin.entity.Admin
import com.example.truckercore.modules.employee.admin.use_cases.interfaces.GetAdminUseCase
import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.GetDriverUseCase
import com.example.truckercore.modules.user.aggregations.UserWithPerson
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.enums.PersonCategory
import com.example.truckercore.modules.user.use_cases.implementations.AggregateUserWithPersonUseCaseImpl
import com.example.truckercore.modules.user.use_cases.interfaces.AggregateUserWithPersonUseCase
import com.example.truckercore.modules.user.use_cases.interfaces.GetUserUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.sealeds.Response
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class AggregateUserWithPersonUseCaseImplTest : KoinTest {

    private val getUser: GetUserUseCase by inject()
    private val getDriver: GetDriverUseCase by inject()
    private val getAdminUseCase: GetAdminUseCase by inject()
    private val aggregateUserWithPersonUseCase: AggregateUserWithPersonUseCase by inject()

    private val userId: String = "userId"
    private val shouldStream: Boolean = true
    private val user: User = mockk(relaxed = true) { every { id } returns userId}
    private val driver: Driver = mockk()
    private val admin: Admin = mockk()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<GetUserUseCase> { mockk() }
                        single<GetDriverUseCase> { mockk() }
                        single<GetAdminUseCase> { mockk() }
                        single<AggregateUserWithPersonUseCase> {
                            AggregateUserWithPersonUseCaseImpl(
                                get(),
                                get(),
                                get()
                            )
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
    fun `execute should call getDriver when user is a DRIVER`() =
        runTest {
            // Arrange
            every { getUser.execute(userId, shouldStream) } returns flowOf(Response.Success(user))
            every { user.personFLag } returns PersonCategory.DRIVER
            every { getDriver.execute(any()) } returns flowOf(Response.Success(driver))

            // Call
            val result = aggregateUserWithPersonUseCase.execute(userId, shouldStream).single()

            // Assertions
            assertTrue(result is Response.Success)
            assertEquals(UserWithPerson(user, driver), (result as Response.Success).data)
            verify { getUser.execute(userId, shouldStream) }
            verify { getDriver.execute(any()) }
        }

    @Test
    fun `execute should call getAdminUseCase when user is an ADMIN`() =
        runTest {
            // Arrange
            every { getUser.execute(userId, shouldStream) } returns flowOf(Response.Success(user))
            every { user.personFLag } returns PersonCategory.ADMIN
            every { getAdminUseCase.execute(any()) } returns flowOf(Response.Success(admin))

            // Call
            val result = aggregateUserWithPersonUseCase.execute(userId, shouldStream).single()

            // Assertions
            assertTrue(result is Response.Success)
            assertEquals(UserWithPerson(user, admin), (result as Response.Success).data)
            verify { getUser.execute(userId, shouldStream) }
            verify { getAdminUseCase.execute(any()) }
        }

    @Test
    fun `execute should return Empty when getUser returns Empty`() =
        runTest {
            // Arrange
            every { getUser.execute(userId, shouldStream) } returns flowOf(Response.Empty)

            // Call
            val result = aggregateUserWithPersonUseCase.execute(userId, shouldStream).single()

            // Assertions
            assertTrue(result is Response.Empty)
            verify { getUser.execute(userId, shouldStream) }
            verify { getDriver.execute(any()) }
            verify { getAdminUseCase.execute(any()) }
        }

    @Test
    fun `execute should return Empty when getDriver returns Empty for DRIVER`() =
        runTest {
            // Arrange
            every { getUser.execute(userId, shouldStream) } returns flowOf(Response.Success(user))
            every { user.personFLag } returns PersonCategory.DRIVER
            every { getDriver.execute(any()) } returns flowOf(Response.Empty)

            // Call
            val result = aggregateUserWithPersonUseCase.execute(userId, shouldStream).single()

            // Assertions
            assertTrue(result is Response.Empty)
            verify { getUser.execute(userId, shouldStream) }
            verify { getDriver.execute(any()) }
        }

    @Test
    fun `execute should return Empty when getAdminUseCase returns Empty for ADMIN`() =
        runTest {
            // Arrange
            every { getUser.execute(userId, shouldStream) } returns flowOf(Response.Success(user))
            every { user.personFLag } returns PersonCategory.ADMIN
            every { getAdminUseCase.execute(any()) } returns flowOf(Response.Empty)

            // Call
            val result = aggregateUserWithPersonUseCase.execute(userId, shouldStream).single()

            // Assertions
            assertTrue(result is Response.Empty)
            verify { getUser.execute(userId, shouldStream) }
            verify { getAdminUseCase.execute(any()) }
        }

}