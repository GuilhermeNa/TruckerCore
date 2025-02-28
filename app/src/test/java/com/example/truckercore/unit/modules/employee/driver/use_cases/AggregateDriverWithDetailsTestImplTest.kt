package com.example.truckercore.unit.modules.employee.driver.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.modules.employee.driver.use_cases.implementations.AggregateDriverWithDetailsImpl
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.AggregateDriverWithDetails
import com.example.truckercore.modules.employee.driver.use_cases.interfaces.GetDriverUseCase
import com.example.truckercore.shared.modules.file.entity.File
import com.example.truckercore.shared.modules.file.use_cases.interfaces.GetFileUseCase
import com.example.truckercore.shared.modules.personal_data.aggregations.PersonalDataWithFile
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.AggregatePersonalDataWithFilesUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class AggregateDriverWithDetailsTestImplTest : KoinTest {

    private val getDriver: GetDriverUseCase by inject()
    private val getFile: GetFileUseCase by inject()
    private val getPersonalDataWithFile: AggregatePersonalDataWithFilesUseCase by inject()
    private val useCase: AggregateDriverWithDetails by inject()

    private val docParams: DocumentParameters = mockk(relaxed = true)
    private val driver: Driver = mockk(relaxed = true)
    private val file: File = mockk(relaxed = true)
    private val pDataWithFile: PersonalDataWithFile = mockk(relaxed = true)
    private val driverResult = Response.Success(driver)
    private val fileResult = Response.Success(listOf(file))
    private val pDataResult = Response.Success(listOf(pDataWithFile))

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<GetDriverUseCase> { mockk() }
                        single<GetFileUseCase> { mockk() }
                        single<AggregatePersonalDataWithFilesUseCase> { mockk() }
                        single<AggregateDriverWithDetails> {
                            AggregateDriverWithDetailsImpl(get(), get(), get())
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
    fun `execute() should return DriverWithDetails when all results are successful`() =
        runTest {
            // Arrange
            every { getDriver.execute(any() as DocumentParameters) } returns flowOf(driverResult)
            every { getFile.execute(any() as QueryParameters) } returns flowOf(fileResult)
            every { getPersonalDataWithFile.execute(any() as QueryParameters) } returns flowOf(
                pDataResult
            )

            // Call
            val result = useCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(driver, successData.driver)
            assertEquals(file, successData.photo)
            assertEquals(pDataWithFile, successData.personalDataWithFile?.firstOrNull())
        }

    @Test
    fun `execute() should return Empty when Driver result is not successful`() =
        runTest {
            // Arrange
            every { getDriver.execute(any() as DocumentParameters) } returns flowOf(Response.Empty)
            every { getFile.execute(any() as QueryParameters) } returns flowOf(fileResult)
            every { getPersonalDataWithFile.execute(any() as QueryParameters) } returns flowOf(
                pDataResult
            )

            // Call
            val result = useCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Empty)
        }

    @Test
    fun `execute() should return DriverWithDetails with null file when File result is not successful`() =
        runTest {
            // Arrange
            every { getDriver.execute(any() as DocumentParameters) } returns flowOf(driverResult)
            every { getFile.execute(any() as QueryParameters) } returns flowOf(Response.Empty)
            every { getPersonalDataWithFile.execute(any() as QueryParameters) } returns flowOf(
                pDataResult
            )

            // Call
            val result = useCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(driver, successData.driver)
            assertNull(successData.photo)
            assertEquals(hashSetOf(pDataWithFile), successData.personalDataWithFile)
        }

    @Test
    fun `execute() should return DriverWithDetails with null personalData when PersonalData result is not successful`() =
        runTest {
            // Arrange
            every { getDriver.execute(any() as DocumentParameters) } returns flowOf(driverResult)
            every { getFile.execute(any() as QueryParameters) } returns flowOf(fileResult)
            every { getPersonalDataWithFile.execute(any() as QueryParameters) } returns flowOf(
                Response.Empty
            )

            // Call
            val result = useCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(driver, successData.driver)
            assertEquals(file, successData.photo)
            assertEquals(hashSetOf<PersonalDataWithFile>(), successData.personalDataWithFile)
        }

    @Test
    fun `execute() should return DriverWithDetails with null file and personalData when both results are not successful`() =
        runTest {
            // Arrange
            every { getDriver.execute(any() as DocumentParameters) } returns flowOf(driverResult)
            every { getFile.execute(any() as QueryParameters) } returns flowOf(Response.Empty)
            every { getPersonalDataWithFile.execute(any() as QueryParameters) } returns flowOf(
                Response.Empty
            )

            // Call
            val result = useCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(driver, successData.driver)
            assertNull(successData.photo)
            assertEquals(hashSetOf<PersonalDataWithFile>(), successData.personalDataWithFile)
        }

}