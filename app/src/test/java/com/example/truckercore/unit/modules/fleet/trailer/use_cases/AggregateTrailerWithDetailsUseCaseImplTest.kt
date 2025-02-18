package com.example.truckercore.unit.modules.fleet.trailer.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.fleet.shared.module.licensing.aggregations.LicensingWithFile
import com.example.truckercore.modules.fleet.shared.module.licensing.use_cases.interfaces.AggregateLicensingWithFilesUseCase
import com.example.truckercore.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.modules.fleet.trailer.use_cases.implementations.AggregateTrailerWithDetailsUseCaseImpl
import com.example.truckercore.modules.fleet.trailer.use_cases.interfaces.AggregateTrailerWithDetailsUseCase
import com.example.truckercore.modules.fleet.trailer.use_cases.interfaces.GetTrailerUseCase
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

class AggregateTrailerWithDetailsUseCaseImplTest : KoinTest {

    private val getTrailer: GetTrailerUseCase by inject()
    private val getLicensingWithFiles: AggregateLicensingWithFilesUseCase by inject()
    private val aggregateTrailerWithDetailsUseCase: AggregateTrailerWithDetailsUseCase by inject()

    private val docParams: DocumentParameters = mockk(relaxed = true)
    private val queryParams: QueryParameters = mockk(relaxed = true)

    private val trailer: Trailer = mockk(relaxed = true)
    private val licensingWithFile: LicensingWithFile = mockk(relaxed = true)

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<GetTrailerUseCase> { mockk() }
                        single<AggregateLicensingWithFilesUseCase> { mockk() }
                        single<AggregateTrailerWithDetailsUseCase> {
                            AggregateTrailerWithDetailsUseCaseImpl(get(), get())
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
    fun `execute(DocumentParameters) should return TrailerWithDetails when both results are successful`() =
        runTest {
            // Arrange
            every { getTrailer.execute(docParams) } returns flowOf(Response.Success(trailer))
            every { getLicensingWithFiles.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(listOf(licensingWithFile))
            )

            // Call
            val result = aggregateTrailerWithDetailsUseCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(trailer, successData.trailer)
            assertEquals(licensingWithFile, successData.licensingWithFiles.firstOrNull())
        }

    @Test
    fun `execute(DocumentParameters) should return Empty when Trailer result is not successful`() =
        runTest {
            // Arrange
            every { getTrailer.execute(any() as DocumentParameters) } returns flowOf(Response.Empty)
            every { getLicensingWithFiles.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(listOf(licensingWithFile))
            )

            // Call
            val result = aggregateTrailerWithDetailsUseCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Empty)
        }

    @Test
    fun `execute(DocumentParameters) should return TrailerWithDetails with empty licensing when Licensing result is not successful`() =
        runTest {
            // Arrange
            every { getTrailer.execute(any() as DocumentParameters) } returns flowOf(
                Response.Success(trailer)
            )
            every { getLicensingWithFiles.execute(any() as QueryParameters) } returns flowOf(
                Response.Empty
            )

            // Call
            val result = aggregateTrailerWithDetailsUseCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(trailer, successData.trailer)
            assertTrue(successData.licensingWithFiles.isEmpty())
        }

    @Test
    fun `execute(QueryParameters) should return a list of TrailerWithDetails for multiple trailer results`() =
        runTest {
            // Arrange
            every { getTrailer.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(listOf(trailer))
            )
            every { getLicensingWithFiles.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(listOf(licensingWithFile))
            )

            // Call
            val result = aggregateTrailerWithDetailsUseCase.execute(queryParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(1, successData.size)
            assertEquals(trailer, successData.first().trailer)
            assertEquals(licensingWithFile, successData.first().licensingWithFiles.firstOrNull())
        }

    @Test
    fun `execute(QueryParameters) should return Empty when Trailer result is not successful for queryParam`() =
        runTest {
            // Arrange
            every { getTrailer.execute(any() as QueryParameters) } returns flowOf(Response.Empty)

            // Call
            val result = aggregateTrailerWithDetailsUseCase.execute(queryParams).single()

            // Assertions
            assertTrue(result is Response.Empty)
        }

    @Test
    fun `execute(QueryParameters) should return TrailerWithDetails with empty licensing when Licensing result is not successful for queryParam`() =
        runTest {
            // Arrange
            every { getTrailer.execute(any() as QueryParameters) } returns flowOf(
                Response.Success(listOf(trailer))
            )
            every { getLicensingWithFiles.execute(any() as QueryParameters) } returns flowOf(
                Response.Empty
            )

            // Call
            val result = aggregateTrailerWithDetailsUseCase.execute(queryParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(trailer, successData.first().trailer)
            assertTrue(successData.first().licensingWithFiles.isEmpty())
        }

}