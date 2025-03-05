package com.example.truckercore.unit.modules.person.person_details

import com.example.truckercore.modules.person.employee.admin.entity.Admin
import com.example.truckercore.modules.person.employee.admin.use_cases.interfaces.GetAdminUseCase
import com.example.truckercore.modules.person.employee.driver.entity.Driver
import com.example.truckercore.modules.person.employee.driver.use_cases.interfaces.GetDriverUseCase
import com.example.truckercore.modules.person.shared.person_details.GetPersonWithDetailsUseCaseImpl
import com.example.truckercore.modules.user.entity.User
import com.example.truckercore.modules.user.enums.PersonCategory
import com.example.truckercore.shared.modules.file.entity.File
import com.example.truckercore.shared.modules.file.use_cases.interfaces.GetFileUseCase
import com.example.truckercore.shared.modules.personal_data.aggregations.PersonalDataWithFile
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.AggregatePersonalDataWithFilesUseCase
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifyOrder
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
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetPersonWithDetailsUseCaseImplTest : KoinTest {

    private val getAdmin: GetAdminUseCase by inject()
    private val getDriver: GetDriverUseCase by inject()
    private val getFile: GetFileUseCase by inject()
    private val getPDataWF: AggregatePersonalDataWithFilesUseCase by inject()
    private val useCase: GetPersonWithDetailsUseCaseImpl by inject()

    // Search Parameters
    private val adminUser: User = mockk {
        every { id } returns "userId"
        every { personFLag } returns PersonCategory.ADMIN
    }
    private val driverUser: User = mockk {
        every { id } returns "userId"
        every { personFLag } returns PersonCategory.DRIVER
    }
    //  val queryParams: QueryParameters = mockk(relaxed = true)
    // val docParams: DocumentParameters = mockk(relaxed = true)

    // Possible Persons
    val admin: Admin = mockk(relaxed = true) {
        every { id } returns "personId"
    }
    val driver: Driver = mockk(relaxed = true) {
        every { id } returns "personId"
    }

    // Person Details
    private val photo: File = mockk(relaxed = true) {
        every { parentId } returns "personId"
    }
    private val pDataWF: PersonalDataWithFile = mockk(relaxed = true) {
        every { parentId } returns "personId"
    }

    companion object {

        @BeforeAll
        @JvmStatic
        fun setup() {
            startKoin {
                modules(
                    module {
                        single<GetAdminUseCase> { mockk() }
                        single<GetDriverUseCase> { mockk() }
                        single<GetFileUseCase> { mockk() }
                        single<AggregatePersonalDataWithFilesUseCase> { mockk() }
                        single { GetPersonWithDetailsUseCaseImpl(get(), get(), get(), get()) }
                    }
                )
            }
        }

        @AfterAll
        @JvmStatic
        fun tearDown() = stopKoin()

    }

    @Test
    fun `should return Response Success when search by admin user`() = runTest {
        // Arrange
        val spy = spyk(useCase, recordPrivateCalls = true)
        val paramsForLoggedUser: QueryParameters = mockk(relaxed = true)
        val adminList = listOf(admin)
        val personIds = adminList.map { it.id }
        val photoList = listOf(photo)
        val pDataWFList = listOf(pDataWF)

        every { spy["getQueryParamsForLoggedUser"](adminUser) } returns paramsForLoggedUser
        every { getAdmin.execute(paramsForLoggedUser) } returns flowOf(Response.Success(adminList))
        every { spy["getPhotoFlow"](adminUser, personIds) } returns flowOf(Response.Success(photoList))
        every { spy["getPersonalDataWithFilesFlow"](adminUser, personIds) } returns flowOf(
            Response.Success(pDataWFList)
        )

        // Act
        val result = spy.execute(adminUser).single()

        // Arrange
        assertTrue(result is Response.Success)
        assertEquals(result.data.person, admin)
        assertEquals(result.data.photo, photo)
        assertEquals(result.data.pDataWFSet.first(), pDataWF)
        verifyOrder {
            spy["getQueryParamsForLoggedUser"](adminUser)
            getAdmin.execute(paramsForLoggedUser)
            spy["getPhotoFlow"](adminUser, personIds)
            spy["getPersonalDataWithFilesFlow"](adminUser, personIds)
        }
    }

    @Test
    fun `should return Response Success when search by driver user`() = runTest {
        // Arrange
        val spy = spyk(useCase, recordPrivateCalls = true)
        val paramsForLoggedUser: QueryParameters = mockk(relaxed = true)
        val driverList = listOf(driver)
        val personIds = driverList.map { it.id }
        val photoList = listOf(photo)
        val pDataWFList = listOf(pDataWF)

        every { spy["getQueryParamsForLoggedUser"](driverUser) } returns paramsForLoggedUser
        every { getDriver.execute(paramsForLoggedUser) } returns flowOf(Response.Success(driverList))
        every { spy["getPhotoFlow"](driverUser, personIds) } returns flowOf(Response.Success(photoList))
        every { spy["getPersonalDataWithFilesFlow"](driverUser, personIds) } returns flowOf(
            Response.Success(pDataWFList)
        )

        // Act
        val result = spy.execute(driverUser).single()

        // Arrange
        assertTrue(result is Response.Success)
        assertEquals(result.data.person, driver)
        assertEquals(result.data.photo, photo)
        assertEquals(result.data.pDataWFSet.first(), pDataWF)
        verifyOrder {
            spy["getQueryParamsForLoggedUser"](driverUser)
            getDriver.execute(paramsForLoggedUser)
            spy["getPhotoFlow"](driverUser, personIds)
            spy["getPersonalDataWithFilesFlow"](driverUser, personIds)
        }
    }


}