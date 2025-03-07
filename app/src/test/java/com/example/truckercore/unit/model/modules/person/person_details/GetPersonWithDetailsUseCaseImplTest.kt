package com.example.truckercore.unit.model.modules.person.person_details

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
import com.example.truckercore.shared.modules.personal_data.use_cases.interfaces.GetPersonalDataWithFilesUseCase
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.sealeds.Response
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class GetPersonWithDetailsUseCaseImplTest : KoinTest {

    private val getAdmin: GetAdminUseCase by inject()
    private val getDriver: GetDriverUseCase by inject()
    private val getFile: GetFileUseCase by inject()
    private val getPDataWF: GetPersonalDataWithFilesUseCase by inject()
    private val useCase: GetPersonWithDetailsUseCaseImpl by inject()
    private val spy = spyk(useCase, recordPrivateCalls = true)

    // Search Parameters
    private val adminUser: User = mockk {
        every { id } returns "userId"
        every { personFLag } returns PersonCategory.ADMIN
    }
    private val driverUser: User = mockk {
        every { id } returns "userId"
        every { personFLag } returns PersonCategory.DRIVER
    }
    private val docParams: DocumentParameters = mockk(relaxed = true)
    private val queryParams: QueryParameters = mockk(relaxed = true)

    // Possible Persons
    private val admin: Admin = mockk(relaxed = true) {
        every { id } returns "personId"
    }
    private val driver: Driver = mockk(relaxed = true) {
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
                        single<GetPersonalDataWithFilesUseCase> { mockk() }
                        single { GetPersonWithDetailsUseCaseImpl(get(), get(), get(), get()) }
                    }
                )
            }
        }

        @AfterAll
        @JvmStatic
        fun tearDown() = stopKoin()

    }

    //----------------------------------------------------------------------------------------------
    // Testing "execute(user: User)"
    //----------------------------------------------------------------------------------------------

    @Test
    fun `should return Response Success when search by admin user`() = runTest {
        // Arrange
        val paramsForLoggedUser: QueryParameters = mockk(relaxed = true)
        val adminList = listOf(admin)
        val personIds = adminList.map { it.id }
        val photoList = listOf(photo)
        val pDataWFList = listOf(pDataWF)

        every { spy["getQueryParamsForLoggedUser"](adminUser) } returns paramsForLoggedUser
        every { getAdmin.execute(paramsForLoggedUser) } returns flowOf(Response.Success(adminList))
        every { spy["getPhotoFlow"](adminUser, personIds) } returns flowOf(
            Response.Success(
                photoList
            )
        )
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
        val paramsForLoggedUser: QueryParameters = mockk(relaxed = true)
        val driverList = listOf(driver)
        val personIds = driverList.map { it.id }
        val photoList = listOf(photo)
        val pDataWFList = listOf(pDataWF)

        every { spy["getQueryParamsForLoggedUser"](driverUser) } returns paramsForLoggedUser
        every { getDriver.execute(paramsForLoggedUser) } returns flowOf(Response.Success(driverList))
        every { spy["getPhotoFlow"](driverUser, personIds) } returns flowOf(
            Response.Success(
                photoList
            )
        )
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

    @Test
    fun `should throw NullPointerException when user has null id and is searching by user`() =
        runTest {
            // Arrange
            val userWithNullId: User = mockk {
                every { id } returns null
            }

            // Act && Assert
            assertThrows<NullPointerException> {
                spy.execute(userWithNullId).single()
            }

        }

    @Test
    fun `should return Response Empty when person is empty and is searching by user`() = runTest {
        // Arrange
        val paramsForLoggedUser: QueryParameters = mockk(relaxed = true)
        val driverList = emptyList<Driver>()

        every { spy["getQueryParamsForLoggedUser"](driverUser) } returns paramsForLoggedUser
        every { getDriver.execute(paramsForLoggedUser) } returns flowOf(Response.Success(driverList))

        // Act
        val result = spy.execute(driverUser).single()

        // Arrange
        assertTrue(result is Response.Empty)
        verifyOrder {
            spy["getQueryParamsForLoggedUser"](driverUser)
            getDriver.execute(paramsForLoggedUser)
        }
    }

    @Test
    fun `should return Response Empty when person Response is Empty and is searching by user`() =
        runTest {
            // Arrange
            val paramsForLoggedUser: QueryParameters = mockk(relaxed = true)

            every { spy["getQueryParamsForLoggedUser"](driverUser) } returns paramsForLoggedUser
            every { getDriver.execute(paramsForLoggedUser) } returns flowOf(Response.Empty)

            // Act
            val result = spy.execute(driverUser).single()

            // Arrange
            assertTrue(result is Response.Empty)
            verifyOrder {
                spy["getQueryParamsForLoggedUser"](driverUser)
                getDriver.execute(paramsForLoggedUser)
            }
        }

    @Test
    fun `should return Response Success when File Response is Empty and searching by user`() =
        runTest {
            // Arrange
            val paramsForLoggedUser: QueryParameters = mockk(relaxed = true)
            val adminList = listOf(admin)
            val personIds = adminList.map { it.id }
            val pDataWFList = listOf(pDataWF)

            every { spy["getQueryParamsForLoggedUser"](adminUser) } returns paramsForLoggedUser
            every { getAdmin.execute(paramsForLoggedUser) } returns flowOf(
                Response.Success(
                    adminList
                )
            )
            every { spy["getPhotoFlow"](adminUser, personIds) } returns flowOf(Response.Empty)
            every { spy["getPersonalDataWithFilesFlow"](adminUser, personIds) } returns flowOf(
                Response.Success(pDataWFList)
            )

            // Act
            val result = spy.execute(adminUser).single()

            // Arrange
            assertTrue(result is Response.Success)
            assertEquals(result.data.person, admin)
            assertNull(result.data.photo)
            assertEquals(result.data.pDataWFSet.first(), pDataWF)
            verifyOrder {
                spy["getQueryParamsForLoggedUser"](adminUser)
                getAdmin.execute(paramsForLoggedUser)
                spy["getPhotoFlow"](adminUser, personIds)
                spy["getPersonalDataWithFilesFlow"](adminUser, personIds)
            }
        }

    @Test
    fun `should return Response Success when PDataWF Response is Empty and searching by  user`() =
        runTest {
            // Arrange
            val paramsForLoggedUser: QueryParameters = mockk(relaxed = true)
            val adminList = listOf(admin)
            val personIds = adminList.map { it.id }
            val photoList = listOf(photo)

            every {
                spy["getQueryParamsForLoggedUser"](adminUser)
            } returns paramsForLoggedUser
            every {
                getAdmin.execute(paramsForLoggedUser)
            } returns flowOf(Response.Success(adminList))
            every {
                spy["getPhotoFlow"](adminUser, personIds)
            } returns flowOf(Response.Success(photoList))
            every {
                spy["getPersonalDataWithFilesFlow"](adminUser, personIds)
            } returns flowOf(Response.Empty)

            // Act
            val result = spy.execute(adminUser).single()

            // Arrange
            assertTrue(result is Response.Success)
            assertEquals(result.data.person, admin)
            assertEquals(result.data.photo, photo)
            assertTrue(result.data.pDataWFSet.isEmpty())
            verifyOrder {
                spy["getQueryParamsForLoggedUser"](adminUser)
                getAdmin.execute(paramsForLoggedUser)
                spy["getPhotoFlow"](adminUser, personIds)
                spy["getPersonalDataWithFilesFlow"](adminUser, personIds)
            }
        }

    //----------------------------------------------------------------------------------------------
    // Testing "execute(params: DocumentParameters, category: PersonCategory)"
    //----------------------------------------------------------------------------------------------

    @Test
    fun `should return Response Success when search by Document Parameters and Admin`() = runTest {
        // Arrange
        val photoList = listOf(photo)
        val pDataWFList = listOf(pDataWF)

        every {
            getAdmin.execute(docParams)
        } returns flowOf(Response.Success(admin))

        every {
            getFile.execute(any() as QueryParameters)
        } returns flowOf(Response.Success(photoList))

        every {
            getPDataWF.execute(any() as QueryParameters)
        } returns flowOf(Response.Success(pDataWFList))

        // Act
        val result = useCase.execute(docParams, PersonCategory.ADMIN).single()

        // Arrange
        assertTrue(result is Response.Success)
        assertEquals(result.data.person, admin)
        assertEquals(result.data.photo, photo)
        assertEquals(result.data.pDataWFSet.first(), pDataWF)
        verifyOrder {
            getAdmin.execute(docParams)
            getFile.execute(any() as QueryParameters)
            getPDataWF.execute(any() as QueryParameters)
        }
    }

    @Test
    fun `should return Response Success when search by Document Parameters and Driver`() = runTest {
        // Arrange
        val photoList = listOf(photo)
        val pDataWFList = listOf(pDataWF)

        every {
            getDriver.execute(docParams)
        } returns flowOf(Response.Success(driver))

        every {
            getFile.execute(any() as QueryParameters)
        } returns flowOf(Response.Success(photoList))

        every {
            getPDataWF.execute(any() as QueryParameters)
        } returns flowOf(Response.Success(pDataWFList))

        // Act
        val result = useCase.execute(docParams, PersonCategory.DRIVER).single()

        // Arrange
        assertTrue(result is Response.Success)
        assertEquals(result.data.person, driver)
        assertEquals(result.data.photo, photo)
        assertEquals(result.data.pDataWFSet.first(), pDataWF)
        verifyOrder {
            getDriver.execute(docParams)
            getFile.execute(any() as QueryParameters)
            getPDataWF.execute(any() as QueryParameters)
        }
    }

    @Test
    fun `should return Response Empty when Person Response is Empty and searching by Document Parameters`() =
        runTest {
            // Arrange
            every {
                getAdmin.execute(docParams)
            } returns flowOf(Response.Empty)

            // Act
            val result = useCase.execute(docParams, PersonCategory.ADMIN).single()

            // Arrange
            assertTrue(result is Response.Empty)
            verify { getAdmin.execute(docParams) }
        }

    @Test
    fun `should return Response Success when File Response is Empty and searching by Document Parameters`() =
        runTest {
            // Arrange
            val pDataWFList = listOf(pDataWF)

            every {
                getAdmin.execute(docParams)
            } returns flowOf(Response.Success(admin))

            every {
                getFile.execute(any() as QueryParameters)
            } returns flowOf(Response.Empty)

            every {
                getPDataWF.execute(any() as QueryParameters)
            } returns flowOf(Response.Success(pDataWFList))

            // Act
            val result = useCase.execute(docParams, PersonCategory.ADMIN).single()

            // Arrange
            assertTrue(result is Response.Success)
            assertEquals(result.data.person, admin)
            assertNull(result.data.photo)
            assertEquals(result.data.pDataWFSet.first(), pDataWF)
            verifyOrder {
                getAdmin.execute(docParams)
                getFile.execute(any() as QueryParameters)
                getPDataWF.execute(any() as QueryParameters)
            }
        }

    @Test
    fun `should return Response Success when PDataWF Response is Empty and searching by Document Parameters`() =
        runTest {
            // Arrange
            val photoList = listOf(photo)

            every {
                getAdmin.execute(docParams)
            } returns flowOf(Response.Success(admin))

            every {
                getFile.execute(any() as QueryParameters)
            } returns flowOf(Response.Success(photoList))

            every {
                getPDataWF.execute(any() as QueryParameters)
            } returns flowOf(Response.Empty)

            // Act
            val result = useCase.execute(docParams, PersonCategory.ADMIN).single()

            // Arrange
            assertTrue(result is Response.Success)
            assertEquals(result.data.person, admin)
            assertEquals(result.data.photo, photo)
            assertTrue(result.data.pDataWFSet.isEmpty())
            verifyOrder {
                getAdmin.execute(docParams)
                getFile.execute(any() as QueryParameters)
                getPDataWF.execute(any() as QueryParameters)
            }
        }

    //----------------------------------------------------------------------------------------------
    // Testing "execute(params: QueryParameters, category: PersonCategory)"
    //----------------------------------------------------------------------------------------------

    @Test
    fun `should return Response Success when search by Query Parameters and Admin`() = runTest {
        // Arrange
        val adminList = listOf(admin)
        val photoList = listOf(photo)
        val pDataWFList = listOf(pDataWF)

        every {
            getAdmin.execute(queryParams)
        } returns flowOf(Response.Success(adminList))

        every {
            getFile.execute(any() as QueryParameters)
        } returns flowOf(Response.Success(photoList))

        every {
            getPDataWF.execute(any() as QueryParameters)
        } returns flowOf(Response.Success(pDataWFList))

        // Act
        val result = useCase.execute(queryParams, PersonCategory.ADMIN).single()

        // Arrange
        assertTrue(result is Response.Success)

        val data = result.data.first()
        assertEquals(data.person, admin)
        assertEquals(data.photo, photo)
        assertEquals(data.pDataWFSet.first(), pDataWF)

        verifyOrder {
            getAdmin.execute(queryParams)
            getFile.execute(any() as QueryParameters)
            getPDataWF.execute(any() as QueryParameters)
        }
    }

    @Test
    fun `should return Response Success when search by Query Parameters and Driver`() = runTest {
        // Arrange
        val driverList = listOf(driver)
        val photoList = listOf(photo)
        val pDataWFList = listOf(pDataWF)

        every {
            getDriver.execute(queryParams)
        } returns flowOf(Response.Success(driverList))

        every {
            getFile.execute(any() as QueryParameters)
        } returns flowOf(Response.Success(photoList))

        every {
            getPDataWF.execute(any() as QueryParameters)
        } returns flowOf(Response.Success(pDataWFList))

        // Act
        val result = useCase.execute(queryParams, PersonCategory.DRIVER).single()

        // Arrange
        assertTrue(result is Response.Success)
        val data = result.data.first()
        assertEquals(data.person, driver)
        assertEquals(data.photo, photo)
        assertEquals(data.pDataWFSet.first(), pDataWF)
        verifyOrder {
            getDriver.execute(queryParams)
            getFile.execute(any() as QueryParameters)
            getPDataWF.execute(any() as QueryParameters)
        }
    }

    @Test
    fun `should return Response Empty when Person Response is Empty and searching by Query Parameters`() =
        runTest {
            // Arrange
            every {
                getAdmin.execute(queryParams)
            } returns flowOf(Response.Empty)

            // Act
            val result = useCase.execute(queryParams, PersonCategory.ADMIN).single()

            // Arrange
            assertTrue(result is Response.Empty)
            verify { getAdmin.execute(queryParams) }
        }

    @Test
    fun `should return Response Success when File Response is Empty and searching by Query Parameters`() =
        runTest {
            // Arrange
            val adminList = listOf(admin)
            val pDataWFList = listOf(pDataWF)

            every {
                getAdmin.execute(queryParams)
            } returns flowOf(Response.Success(adminList))

            every {
                getFile.execute(any() as QueryParameters)
            } returns flowOf(Response.Empty)

            every {
                getPDataWF.execute(any() as QueryParameters)
            } returns flowOf(Response.Success(pDataWFList))

            // Act
            val result = useCase.execute(queryParams, PersonCategory.ADMIN).single()

            // Arrange
            assertTrue(result is Response.Success)
            val data = result.data.first()
            assertEquals(data.person, admin)
            assertNull(data.photo)
            assertEquals(data.pDataWFSet.first(), pDataWF)
            verifyOrder {
                getAdmin.execute(queryParams)
                getFile.execute(any() as QueryParameters)
                getPDataWF.execute(any() as QueryParameters)
            }
        }

    @Test
    fun `should return Response Success when PDataWF Response is Empty and searching by Query Parameters`() =
        runTest {
            // Arrange
            val adminList = listOf(admin)
            val photoList = listOf(photo)

            every {
                getAdmin.execute(queryParams)
            } returns flowOf(Response.Success(adminList))

            every {
                getFile.execute(any() as QueryParameters)
            } returns flowOf(Response.Success(photoList))

            every {
                getPDataWF.execute(any() as QueryParameters)
            } returns flowOf(Response.Empty)

            // Act
            val result = useCase.execute(queryParams, PersonCategory.ADMIN).single()

            // Arrange
            assertTrue(result is Response.Success)
            val data = result.data.first()
            assertEquals(data.person, admin)
            assertEquals(data.photo, photo)
            assertTrue(data.pDataWFSet.isEmpty())
            verifyOrder {
                getAdmin.execute(queryParams)
                getFile.execute(any() as QueryParameters)
                getPDataWF.execute(any() as QueryParameters)
            }
        }

}