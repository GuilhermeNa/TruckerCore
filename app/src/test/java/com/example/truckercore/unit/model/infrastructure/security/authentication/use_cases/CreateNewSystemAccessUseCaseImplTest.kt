package com.example.truckercore.unit.model.infrastructure.security.authentication.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.database.firebase.repository.FirebaseRepository
import com.example.truckercore.infrastructure.security.authentication.entity.NewAccessRequirements
import com.example.truckercore.infrastructure.security.authentication.use_cases.CreateNewSystemAccessUseCase
import com.example.truckercore.infrastructure.security.authentication.use_cases.CreateNewSystemAccessUseCaseImpl
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.person.employee.admin.mapper.AdminMapper
import com.example.truckercore.modules.person.employee.driver.mapper.DriverMapper
import com.example.truckercore.modules.user.enums.PersonCategory
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.sealeds.Response
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
    private val useCase: CreateNewSystemAccessUseCase by inject()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<FirebaseRepository> { mockk() }
                        single<ValidatorService> { mockk() }
                        single<BusinessCentralMapper> { mockk() }
                        single<UserMapper> { mockk() }
                        single<DriverMapper> { mockk() }
                        single<AdminMapper> { mockk() }
                        single<CreateNewSystemAccessUseCase> {
                            CreateNewSystemAccessUseCaseImpl(
                                get(), get(), get(),
                                get(), get(), get()
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
    fun `should execute use case successfully for creating new system access`() = runTest {
        // Arrange
        val requirements: NewAccessRequirements = mockk(relaxed = true) {
            every { personFlag } returns PersonCategory.ADMIN
        }

        every { firebaseRepository.runTransaction(any()) } returns flowOf(Response.Success(Unit))

        // Call
        val result = useCase.execute(requirements).single()

        // Assertions
        assertTrue(result is Response.Success)
        verify { firebaseRepository.runTransaction(any()) }
    }

}