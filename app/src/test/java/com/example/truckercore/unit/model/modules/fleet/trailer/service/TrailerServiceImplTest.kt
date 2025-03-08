package com.example.truckercore.unit.model.modules.fleet.trailer.service

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.model.modules.fleet.trailer.aggregations.TrailerWithDetails
import com.example.truckercore.model.modules.fleet.trailer.entity.Trailer
import com.example.truckercore.model.modules.fleet.trailer.service.TrailerService
import com.example.truckercore.model.modules.fleet.trailer.service.TrailerServiceImpl
import com.example.truckercore.model.modules.fleet.trailer.use_cases.interfaces.AggregateTrailerWithDetailsUseCase
import com.example.truckercore.model.modules.fleet.trailer.use_cases.interfaces.GetTrailerUseCase
import com.example.truckercore.model.shared.utils.parameters.DocumentParameters
import com.example.truckercore.model.shared.utils.parameters.QueryParameters
import com.example.truckercore.model.shared.utils.sealeds.Response
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

class TrailerServiceImplTest : KoinTest {

    private val getTrailer: GetTrailerUseCase by inject()
    private val getTrailerWithDetails: AggregateTrailerWithDetailsUseCase by inject()
    private val trailerService: TrailerService by inject()

    private val docParams: DocumentParameters = mockk(relaxed = true)
    private val queryParams: QueryParameters = mockk(relaxed = true)

    private val trailer: Trailer = mockk()
    private val trailerWithDetails: TrailerWithDetails = mockk()

    companion object {

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<GetTrailerUseCase> { mockk() }
                        single<AggregateTrailerWithDetailsUseCase> { mockk() }
                        single<TrailerService> { TrailerServiceImpl(mockk(), get(), get()) }
                    }
                )
            }
        }

        @JvmStatic
        @AfterAll
        fun tearDown() = stopKoin()

    }

    @Test
    fun `fetchTrailer(DocumentParameters) should call getTrailer useCase`() =
        runTest {
            // Arrange
            every { getTrailer.execute(docParams) } returns flowOf(Response.Success(trailer))

            // Call
            val result = trailerService.fetchTrailer(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            assertEquals(trailer, (result as Response.Success).data)
            verify { getTrailer.execute(docParams) }
        }

    @Test
    fun `fetchTrailerList(QueryParameters) should call getTrailer useCase`() =
        runTest {
            // Arrange
            every { getTrailer.execute(queryParams) } returns flowOf(
                Response.Success(listOf(trailer))
            )

            // Call
            val result = trailerService.fetchTrailerList(queryParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            assertEquals(trailer, (result as Response.Success).data.first())
            verify { getTrailer.execute(queryParams) }
        }

    @Test
    fun `fetchTrailerWithDetails(DocumentParameters) should call getTrailerWithDetails useCase`() =
        runTest {
            // Arrange
            every { getTrailerWithDetails.execute(docParams) } returns flowOf(
                Response.Success(trailerWithDetails)
            )

            // Call
            val result = trailerService.fetchTrailerWithDetails(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            assertEquals(trailerWithDetails, (result as Response.Success).data)
            verify { getTrailerWithDetails.execute(docParams) }
        }

    @Test
    fun `fetchTrailerWithDetailsList(QueryParameters) should call getTrailerWithDetails useCase`() =
        runTest {
            // Arrange
            every { getTrailerWithDetails.execute(queryParams) } returns flowOf(
                Response.Success(listOf(trailerWithDetails))
            )

            // Call
            val result = trailerService.fetchTrailerWithDetailsList(queryParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            assertEquals(trailerWithDetails, (result as Response.Success).data.first())
            verify { getTrailerWithDetails.execute(queryParams) }
        }

}