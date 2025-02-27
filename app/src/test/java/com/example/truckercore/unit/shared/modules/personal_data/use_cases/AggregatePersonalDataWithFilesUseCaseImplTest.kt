package com.example.truckercore.unit.shared.modules.personal_data.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.shared.modules.file.entity.File
import com.example.truckercore.shared.modules.file.use_cases.interfaces.GetFileUseCase
import com.example.truckercore.shared.modules.personal_data.aggregations.PersonalDataWithFile
import com.example.truckercore.shared.modules.personal_data.entity.PersonalData
import com.example.truckercore.shared.modules.personal_data.use_cases.implementations.AggregatePersonalDataWithFilesUseCaseImpl
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
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class AggregatePersonalDataWithFilesUseCaseImplTest : KoinTest {

    private val getPersonalData: GetPersonalDataUseCase by inject()
    private val getFile: GetFileUseCase by inject()
    private val aggregatePersonalDataWithFilesUseCase: AggregatePersonalDataWithFilesUseCase by inject()

    private val documentParams: DocumentParameters = mockk(relaxed = true)
    private val queryParams: QueryParameters = mockk(relaxed = true)
    private val personalData: PersonalData = mockk(relaxed = true)
    private val file: File = mockk(relaxed = true)

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<GetPersonalDataUseCase> { mockk() }
                        single<GetFileUseCase> { mockk() }
                        single<AggregatePersonalDataWithFilesUseCase> {
                            AggregatePersonalDataWithFilesUseCaseImpl(
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
    fun `execute(DocumentParameters) should return success with PersonalDataWithFile`() =
        runTest {
            // Arrange
            every { getPersonalData.execute(documentParams) } returns flowOf(
                Response.Success(personalData)
            )
            every { getFile.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(listOf(file))
            )

            // Call
            val result = aggregatePersonalDataWithFilesUseCase.execute(documentParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            assertEquals(
                PersonalDataWithFile(personalData, listOf(file)),
                (result as Response.Success).data
            )
            verify { getPersonalData.execute(documentParams) }
            verify { getFile.execute(any() as QueryParameters) }
        }

    @Test
    fun `execute(DocumentParameters) should return empty when PersonalData is empty`() =
        runTest {
            // Arrange
            every { getPersonalData.execute(documentParams) } returns flowOf(Response.Empty)
            every { getFile.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(listOf(file))
            )

            // Call
            val result = aggregatePersonalDataWithFilesUseCase.execute(documentParams).single()

            // Assertions
            assertTrue(result is Response.Empty)
            verify { getPersonalData.execute(documentParams) }
            verify { getFile.execute(any() as QueryParameters) }
        }

    @Test
    fun `execute(DocumentParameters) should return empty when Files is empty`() =
        runTest {
            // Arrange
            every { getPersonalData.execute(documentParams) } returns flowOf(
                Response.Success(personalData)
            )
            every { getFile.execute(any() as QueryParameters) } returns flowOf(Response.Empty)

            // Call
            val result = aggregatePersonalDataWithFilesUseCase.execute(documentParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            assertEquals(
                PersonalDataWithFile(personalData, emptyList()),
                (result as Response.Success).data
            )
            verify(exactly = 1) { getPersonalData.execute(documentParams) }
            verify(exactly = 1) { getFile.execute(any() as QueryParameters) }
        }

    @Test
    fun `execute(QueryParameters) should return success with a list of PersonalDataWithFile`() =
        runTest {
            // Arrange
            val personalDataList = listOf(personalData)
            val fileList = listOf(file)

            every { getPersonalData.execute(queryParams) } returns flowOf(
                Response.Success(personalDataList)
            )
            every { getFile.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(fileList)
            )

            // Call
            val result = aggregatePersonalDataWithFilesUseCase.execute(queryParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            assertEquals(
                listOf(PersonalDataWithFile(personalData, fileList)),
                (result as Response.Success).data
            )
            verify { getPersonalData.execute(queryParams) }
            verify { getFile.execute(any() as QueryParameters) }
        }

    @Test
    fun `execute(QueryParameters) should return empty when PersonalData is empty`() =
        runTest {
            // Arrange
            every { getPersonalData.execute(queryParams) } returns flowOf(Response.Empty)

            // Call
            val result = aggregatePersonalDataWithFilesUseCase.execute(queryParams).single()

            // Assertions
            assertTrue(result is Response.Empty)
            verify(exactly = 1) { getPersonalData.execute(queryParams) }
        }

    @Test
    fun `execute(QueryParameters) should return empty when Files is empty`() =
        runTest {
            // Arrange
            every { getPersonalData.execute(queryParams) } returns flowOf(
                Response.Success(listOf(personalData))
            )
            every { getFile.execute(any() as QueryParameters) } returns flowOf(Response.Empty)

            // Call
            val result = aggregatePersonalDataWithFilesUseCase.execute(queryParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            assertEquals(
                PersonalDataWithFile(personalData, emptyList()),
                (result as Response.Success).data.first()
            )
            verify { getPersonalData.execute(queryParams) }
            verify { getFile.execute(any() as QueryParameters) }
        }

}