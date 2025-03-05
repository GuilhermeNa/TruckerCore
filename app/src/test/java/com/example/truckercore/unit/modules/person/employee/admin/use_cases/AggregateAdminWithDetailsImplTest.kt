package com.example.truckercore.unit.modules.person.employee.admin.use_cases

import com.example.truckercore._test_utils.mockStaticLog
import com.example.truckercore.modules.person.employee.admin.entity.Admin
import com.example.truckercore.modules.person.employee.admin.use_cases.implementations.AggregateAdminWithDetailsImpl
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.AggregateAdminWithDetails
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.GetAdminUseCase
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
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class AggregateAdminWithDetailsImplTest : KoinTest {

    private val getAdmin: GetAdminUseCase by inject()
    private val getFile: GetFileUseCase by inject()
    private val getPersonalDataWithFile: AggregatePersonalDataWithFilesUseCase by inject()
    private val useCase: AggregateAdminWithDetails by inject()

    private val docParams: DocumentParameters = mockk(relaxed = true)
    private val admin: Admin = mockk(relaxed = true)
    private val file: File = mockk(relaxed = true)
    private val pDataWithFile: PersonalDataWithFile = mockk(relaxed = true)
    private val adminResult = Response.Success(admin)
    private val fileResult = Response.Success(listOf(file))
    private val pDataResult = Response.Success(listOf(pDataWithFile))

    companion object {

        private const val ADMIN_ID = "adminId"

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockStaticLog()
            startKoin {
                modules(
                    module {
                        single<GetAdminUseCase> { mockk() }
                        single<GetFileUseCase> { mockk() }
                        single<AggregatePersonalDataWithFilesUseCase> { mockk() }
                        single<AggregateAdminWithDetails> {
                            AggregateAdminWithDetailsImpl(get(), get(), get())
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
    fun `execute() should return AdminWithDetails when all results are successful`() =
        runTest {
            // Arrange
            every { getAdmin.execute(any() as DocumentParameters) } returns flowOf(adminResult)
            every { getFile.execute(any() as QueryParameters) } returns flowOf(fileResult)
            every { getPersonalDataWithFile.execute(any() as QueryParameters) } returns flowOf(
                pDataResult
            )

            // Call
            val result = useCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(admin, successData.admin)
            assertEquals(file, successData.photo)
            assertEquals(pDataWithFile, successData.personalDataWithFile?.firstOrNull())
        }

    @Test
    fun `execute() should return Empty when Admin result is not successful`() =
        runTest {
            // Arrange
            every { getAdmin.execute(any() as DocumentParameters) } returns flowOf(Response.Empty)
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
    fun `execute() should return AdminWithDetails with null file when File result is not successful`() =
        runTest {
            // Arrange
            every { getAdmin.execute(any() as DocumentParameters) } returns flowOf(adminResult)
            every { getFile.execute(any() as QueryParameters) } returns flowOf(Response.Empty)
            every { getPersonalDataWithFile.execute(any() as QueryParameters) } returns flowOf(
                pDataResult
            )

            // Call
            val result = useCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(admin, successData.admin)
            assertNull(successData.photo)
            assertEquals(hashSetOf(pDataWithFile), successData.personalDataWithFile)
        }

    @Test
    fun `execute() should return AdminWithDetails with null personalData when PersonalData result is not successful`() =
        runTest {
            // Arrange
            every { getAdmin.execute(any() as DocumentParameters) } returns flowOf(adminResult)
            every { getFile.execute(any() as QueryParameters) } returns flowOf(fileResult)
            every { getPersonalDataWithFile.execute(any() as QueryParameters) } returns flowOf(
                Response.Empty
            )

            // Call
            val result = useCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(admin, successData.admin)
            assertEquals(file, successData.photo)
            assertEquals(hashSetOf<PersonalDataWithFile>(), successData.personalDataWithFile)
        }

    @Test
    fun `execute() should return AdminWithDetails with null file and personalData when both results are not successful`() =
        runTest {
            // Arrange
            every { getAdmin.execute(any() as DocumentParameters) } returns flowOf(adminResult)
            every { getFile.execute(any() as QueryParameters) } returns flowOf(Response.Empty)
            every { getPersonalDataWithFile.execute(any() as QueryParameters) } returns flowOf(
                Response.Empty
            )

            // Call
            val result = useCase.execute(docParams).single()

            // Assertions
            assertTrue(result is Response.Success)
            val successData = (result as Response.Success).data
            assertEquals(admin, successData.admin)
            assertNull(successData.photo)
            assertEquals(hashSetOf<PersonalDataWithFile>(), successData.personalDataWithFile)
        }

}