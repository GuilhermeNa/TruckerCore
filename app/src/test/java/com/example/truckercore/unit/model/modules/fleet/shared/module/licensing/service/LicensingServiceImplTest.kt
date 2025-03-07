package com.example.truckercore.unit.model.modules.fleet.shared.module.licensing.service

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.modules.fleet.shared.module.licensing.entity.Licensing
import com.example.truckercore.modules.fleet.shared.module.licensing.service.LicensingService
import com.example.truckercore.modules.fleet.shared.module.licensing.service.LicensingServiceImpl
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.AggregateLicensingWithFilesUseCase
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.GetLicensingUseCase
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
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.Test

class LicensingServiceImplTest : KoinTest {

    private val getLicensing: GetLicensingUseCase by inject()
    private val getLicensingWithFile: AggregateLicensingWithFilesUseCase by inject()
    private val licensingService: LicensingService by inject()

    private val docParams: DocumentParameters = mockk(relaxed = true)
    private val queryParams: QueryParameters = mockk(relaxed = true)

    private val licensing: Licensing = mockk()
    private val licensingWithFile: LicensingWithFile = mockk()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<GetLicensingUseCase> { mockk() }
                        single<AggregateLicensingWithFilesUseCase> { mockk() }
                        single<LicensingService> { LicensingServiceImpl(mockk(), get(), get()) }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()

    }

    @Test
    fun `fetchLicensing(DocumentParameters) should call getLicensing useCase`() =
        runTest {
            // Arrange
            every { getLicensing.execute(docParams) } returns flowOf(Response.Success(licensing))

            // Call
            val result = licensingService.fetchLicensing(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            assertEquals(licensing, (result as Response.Success).data)
            verify { getLicensing.execute(docParams) }
        }

    @Test
    fun `fetchLicensing(QueryParameters) should call getLicensing useCase`() =
        runTest {
            // Arrange
            every { getLicensing.execute(queryParams) } returns flowOf(
                Response.Success(listOf(licensing))
            )

            // Call
            val result = licensingService.fetchLicensing(queryParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            assertEquals(licensing, (result as Response.Success).data.first())
            verify { getLicensing.execute(queryParams) }
        }

    @Test
    fun `fetchLicensingWithFiles(DocumentParameters) should call getLicensingWithFile useCase`() =
        runTest {
            // Arrange
            every { getLicensingWithFile.execute(docParams) } returns flowOf(
                Response.Success(licensingWithFile)
            )

            // Call
            val result = licensingService.fetchLicensingWithFiles(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            assertEquals(licensingWithFile, (result as Response.Success).data)
        }

    @Test
    fun `fetchLicensingWithFiles(QueryParameters) should call getLicensingWithFile useCase`() =
        runTest {
            // Arrange
            every { getLicensingWithFile.execute(queryParams) } returns flowOf(
                Response.Success(listOf(licensingWithFile))
            )

            // Call
            val result = licensingService.fetchLicensingWithFiles(queryParams).single()

            // Assertions
            assertTrue(result is Response.Empty)
            assertEquals(licensingWithFile, (result as Response.Success).data.first())
            verify { getLicensingWithFile.execute(queryParams) }
        }

}