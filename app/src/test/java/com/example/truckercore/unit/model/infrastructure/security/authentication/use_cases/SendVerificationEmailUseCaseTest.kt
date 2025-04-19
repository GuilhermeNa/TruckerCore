package com.example.truckercore.unit.model.infrastructure.security.authentication.use_cases

import com.example.truckercore.model.infrastructure.integration._auth.repository.AuthenticationRepository
import com.example.truckercore.model.infrastructure.security.authentication.use_cases.interfaces.SendVerificationEmailUseCase
import com.example.truckercore.model.infrastructure.integration._auth.use_cases.implementations.SendVerificationEmailUseCaseImpl
import com.example.truckercore.model.shared.utils.sealeds.AppResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

class SendVerificationEmailUseCaseTest : KoinTest {

    // Injections
    private val authRepository: com.example.truckercore.model.infrastructure.integration._auth.repository.AuthenticationRepository by inject()
    private val useCase: SendVerificationEmailUseCase by inject()

    @BeforeEach
    fun setup() {
        startKoin {
            modules(
                module {
                    single<com.example.truckercore.model.infrastructure.integration._auth.repository.AuthenticationRepository> { mockk() }
                    single<SendVerificationEmailUseCase> {
                        com.example.truckercore.model.infrastructure.integration._auth.use_cases.implementations.SendVerificationEmailUseCaseImpl(
                            get()
                        )
                    }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() = stopKoin()


    @Test
    fun `should return the sendEMailVerification from auth repository`() = runTest {
        // Arrange
        val authRepoResult = AppResult.Success(Unit)

        coEvery { authRepository.sendEmailVerification() } returns authRepoResult

        // Act
        val result = useCase.invoke()

        // Assert
        assertEquals(result, authRepoResult)
        coVerify(exactly = 1) { authRepository.sendEmailVerification() }
    }

}