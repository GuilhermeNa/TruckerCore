package com.example.truckercore.unit.model.infrastructure.security.authentication.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.infrastructure.data_source.firebase.repository.FirebaseRepository
import com.example.truckercore.model.infrastructure.security.authentication.entity.NewAccessRequirements
import com.example.truckercore.model.infrastructure.integration._auth.use_cases.implementations.CreateNewSystemAccessUseCaseImpl
import com.example.truckercore.model.modules.business_central.factory.BusinessCentralFactory
import com.example.truckercore.model.modules.person.employee.admin.factory.AdminFactory
import com.example.truckercore.model.modules.person.employee.driver.factory.DriverFactory
import com.example.truckercore.model.modules.user.enums.PersonCategory
import com.example.truckercore.model.modules.user.factory.UserFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class CreateNewSystemAccessUseCaseImplTest : KoinTest {

    private val firebaseRepository: FirebaseRepository by inject()
    private val useCase: com.example.truckercore.model.infrastructure.integration._auth.use_cases.implementations.CreateNewSystemAccessUseCaseImpl by inject()

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<FirebaseRepository> { mockk() }
                        single<BusinessCentralFactory> { mockk() }
                        single<UserFactory> { mockk() }
                        single<AdminFactory> { mockk() }
                        single<DriverFactory> { mockk() }
                        single {
                            com.example.truckercore.model.infrastructure.integration._auth.use_cases.implementations.CreateNewSystemAccessUseCaseImpl(
                                get(),
                                get(),
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
    fun `should execute use case successfully for creating new admin system access`() = runTest {
        // Arrange
        val requirements: NewAccessRequirements = mockk(relaxed = true) {
            every { category } returns PersonCategory.ADMIN
        }

        every { firebaseRepository.runTransaction(any()) } returns flowOf(Response.Success(Unit))

        // Call
        val result = useCase.execute(requirements).single()

        // Assertions
        assertTrue(result is Response.Success)
        verify { firebaseRepository.runTransaction(any()) }
    }

    @Test
    fun `should execute use case successfully for creating new driver system access`() = runTest {
        // Arrange
        val requirements: NewAccessRequirements = mockk(relaxed = true) {
            every { category } returns PersonCategory.DRIVER
        }

        every { firebaseRepository.runTransaction(any()) } returns flowOf(Response.Success(Unit))

        // Call
        val result = useCase.execute(requirements).single()

        // Assertions
        assertTrue(result is Response.Success)
        verify { firebaseRepository.runTransaction(any()) }
    }

}