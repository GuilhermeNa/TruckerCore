package com.example.truckercore.unit.modules.fleet.shared.module.licensing.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.configs.di.travelModule
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.implementations.AggregateLicensingWithFilesUseCaseImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.AggregateLicensingWithFilesUseCase
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingUseCase
import com.example.truckercore.shared.modules.file.entity.File
import com.example.truckercore.shared.modules.file.use_cases.interfaces.GetFileUseCase
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
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class AggregateLicensingWithFilesUseCaseImplTest : KoinTest {

    private val getLicensing: GetLicensingUseCase by inject()
    private val getFile: GetFileUseCase by inject()
    private val aggregateLicensingWithFilesUseCase: AggregateLicensingWithFilesUseCase by inject()

    private val docParams: DocumentParameters = mockk(relaxed = true)
    private val queryParams: QueryParameters = mockk(relaxed = true)

    private val licensing: Licensing = mockk(relaxed = true)
    private val file: File = mockk(relaxed = true)

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<GetLicensingUseCase> { mockk() }
                        single<GetFileUseCase> { mockk() }
                        single<AggregateLicensingWithFilesUseCase> {
                            AggregateLicensingWithFilesUseCaseImpl(get(), get())
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
    fun `execute(DocumentParameters) should return LicensingWithFile when both results are successful`() =
        runTest {
            // Arrange
            every { getLicensing.execute(any() as DocumentParameters) } returns flowOf(
                Response.Success(licensing)
            )
            every { getFile.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(listOf(file))
            )

            // Call
            val result = aggregateLicensingWithFilesUseCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(licensing, successData.licensing)
            assertEquals(file, successData.files.firstOrNull())
        }

    @Test
    fun `execute(DocumentParameters) should return Empty when Licensing result is not successful`() =
        runTest {
            // Arrange
            every { getLicensing.execute(any() as DocumentParameters) } returns flowOf(Response.Empty)
            every { getFile.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(listOf(file))
            )

            // Call
            val result = aggregateLicensingWithFilesUseCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Empty)
        }

    @Test
    fun `execute(DocumentParameters) should return LicensingWithFile with empty files when File result is not successful`() =
        runTest {
            // Arrange
            every { getLicensing.execute(any() as DocumentParameters) } returns flowOf(
                Response.Success(licensing)
            )
            every { getFile.execute(any() as QueryParameters) } returns flowOf(Response.Empty)

            // Call
            val result = aggregateLicensingWithFilesUseCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(licensing, successData.licensing)
            assertTrue(successData.files.isEmpty())
        }

    @Test
    fun `execute(DocumentParameters) should return LicensingWithFile with empty files when both results are not successful`() =
        runTest {
            // Arrange
            every { getLicensing.execute(any() as DocumentParameters) } returns flowOf(Response.Empty)
            every { getFile.execute(any() as QueryParameters) } returns flowOf(Response.Empty)

            // Call
            val result = aggregateLicensingWithFilesUseCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Empty)
        }

    @Test
    fun `execute(QueryParameters) should return a list of LicensingWithFile for multiple licensing results`() =
        runTest {
            // Arrange
            every { getLicensing.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(listOf(licensing))
            )
            every { getFile.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(listOf(file))
            )

            // Call
            val result = aggregateLicensingWithFilesUseCase.execute(queryParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(1, successData.size)
            assertEquals(file, successData.first().files.firstOrNull())
        }

    @Test
    fun `execute(QueryParameters) should return Empty when Licensing result is not successful`() =
        runTest {
            // Arrange
            every { getLicensing.execute(any() as QueryParameters) } returns flowOf(Response.Empty)

            // Call
            val result = aggregateLicensingWithFilesUseCase.execute(queryParams).single()

            // Assertions
            assertTrue(result is Response.Empty)
        }

    @Test
    fun `execute(QueryParameters) should return LicensingWithFile with empty files when File result is not successful`() =
        runTest {
            // Arrange
            every { getLicensing.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(listOf(licensing))
            )
            every { getFile.execute(any() as QueryParameters) } returns flowOf(Response.Empty)

            // Call
            val result = aggregateLicensingWithFilesUseCase.execute(queryParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(licensing, successData.first().licensing)
            assertTrue(successData.first().files.isEmpty())
        }

}