package com.example.truckercore.unit.shared.modules.personal_data.service

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.infrastructure.exceptions.ExceptionHandler
import com.example.truckercore.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.shared.modules.personal_data.service.PersonalDataService
import com.example.truckercore.shared.modules.personal_data.service.PersonalDataServiceImpl
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.AggregatePersonalDataWithFilesUseCase
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.GetPersonalDataUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
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
import kotlin.test.assertTrue

class PersonalDataServiceImplTest : KoinTest {

    private val getPersonalData: GetPersonalDataUseCase by inject()
    private val getPersonalDataWithFile: AggregatePersonalDataWithFilesUseCase by inject()
    private val personalDataService: PersonalDataService by inject()

    private val documentParams: DocumentParameters = mockk(relaxed = true)
    private val queryParams: QueryParameters = mockk(relaxed = true)

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<ExceptionHandler> { mockk() }
                        single<GetPersonalDataUseCase> { mockk() }
                        single<AggregatePersonalDataWithFilesUseCase> { mockk() }
                        single<PersonalDataService> { PersonalDataServiceImpl(get(), get(), get()) }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()
    }

    @Test
    fun `fetchPersonalData(DocumentParameters) should call getPersonalData useCase`() =
        runTest {
            // Arrange
            every { getPersonalData.execute(documentParams) } returns flowOf(mockk())

            // Act
            personalDataService.fetchPersonalData(documentParams).single()

            // Assert
            verify { getPersonalData.execute(documentParams) }
        }

    @Test
    fun `fetchPersonalData(QueryParameters) should call getPersonalData useCase`() =
        runTest {
            // Arrange
            every { getPersonalData.execute(queryParams) } returns flowOf(mockk())

            // Act
          personalDataService.fetchPersonalData(queryParams).single()


            // Assert
            verify { getPersonalData.execute(queryParams) }
        }

    @Test
    fun `fetchPersonalDataWithDetails(DocumentParameters) should call getPersonalDataWithFile useCase`() =
        runTest {
            // Arrange
            every { getPersonalDataWithFile.execute(documentParams) } returns flowOf(mockk())

            // Act
           personalDataService.fetchPersonalDataWithDetails(documentParams).single()

            // Assert

            verify { getPersonalDataWithFile.execute(documentParams) }
        }

    @Test
    fun `fetchPersonalDataWithDetails(QueryParameters) should call getPersonalDataWithFile useCase`() =
        runTest {
            // Arrange
            every { getPersonalDataWithFile.execute(documentParams) } returns flowOf(mockk())

            // Act
            personalDataService.fetchPersonalDataWithDetails(documentParams).single()

            // Assert
            verify { getPersonalDataWithFile.execute(documentParams) }
        }

}