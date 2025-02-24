package com.example.truckercore.unit.infrastructure.security.authentication.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.database.firebase.repository.FirebaseRepository
import com.example.truckercore.infrastructure.security.authentication.entity.NewAccessRequirements
import com.example.truckercore.infrastructure.security.authentication.use_cases.CreateNewSystemAccessUseCase
import com.example.truckercore.infrastructure.security.authentication.use_cases.CreateNewSystemAccessUseCaseImpl
import com.example.truckercore.modules.business_central.dto.BusinessCentralDto
import com.example.truckercore.modules.business_central.mapper.BusinessCentralMapper
import com.example.truckercore.modules.employee.admin.mapper.AdminMapper
import com.example.truckercore.modules.employee.driver.mapper.DriverMapper
import com.example.truckercore.modules.user.dto.UserDto
import com.example.truckercore.modules.user.mapper.UserMapper
import com.example.truckercore.shared.services.ValidatorService
import com.example.truckercore.shared.utils.sealeds.Response
import com.google.firebase.firestore.DocumentReference
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.junit.jupiter.api.Test
import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.modules.employee.driver.dto.DriverDto
import com.example.truckercore.modules.user.enums.PersonCategory
import com.google.common.base.Verify.verify
import io.mockk.verify

class CreateNewSystemAccessUseCaseImplTest : KoinTest {

    private val firebaseRepository: FirebaseRepository by inject()
    private val validatorService: ValidatorService by inject()
    private val centralMapper: BusinessCentralMapper by inject()
    private val userMapper: UserMapper by inject()
    private val driverMapper: DriverMapper by inject()
    private val adminMapper: AdminMapper by inject()

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
                                get(),
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
    fun `should execute use case successfully for creating new system access`() = runTest {
        // Arrange
        val requirements: NewAccessRequirements = mockk(relaxed = true) {
            every { personFlag } returns PersonCategory.DRIVER
        }
        val centralRef: DocumentReference = mockk(relaxed = true)
        val userRef: DocumentReference = mockk(relaxed = true)
        val personRef: DocumentReference = mockk(relaxed = true)

        every { firebaseRepository.createDocument(Collection.CENTRAL) } returns centralRef
        every { firebaseRepository.createDocument(Collection.USER) } returns userRef
        every { firebaseRepository.createDocument(Collection.DRIVER) } returns personRef
        every { firebaseRepository.runTransaction(any()) } returns flowOf(Response.Success(Unit))

        // Mocking the mapper methods
        val centralDto: BusinessCentralDto = mockk()
        val userDto: UserDto = mockk()
        val driverDto: DriverDto = mockk()

        every { centralMapper.toDto(any()) } returns centralDto
        every { userMapper.toDto(any()) } returns userDto
        every { driverMapper.toDto(any()) } returns driverDto

        // Call
        val result = useCase.execute(requirements).single()

        // Assertions
        assertTrue(result is Response.Success)

        // Verify that the repository and mapper methods were called
        verify {
            firebaseRepository.createDocument(Collection.CENTRAL)
            firebaseRepository.createDocument(Collection.USER)
            firebaseRepository.createDocument(Collection.DRIVER)
            centralMapper.toDto(any())
            userMapper.toDto(any())
            driverMapper.toDto(any())
        }
    }

}