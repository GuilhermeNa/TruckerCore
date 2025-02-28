package com.example.truckercore.unit.shared.person_details

import com.example.truckercore._test_data_provider.TestAdminDataProvider
import com.example.truckercore._test_data_provider.TestDriverDataProvider
import com.example.truckercore._test_data_provider.TestFileDataProvider
import com.example.truckercore.modules.employee.admin.entity.Admin
import com.example.truckercore.modules.employee.driver.entity.Driver
import com.example.truckercore.shared.errors.InvalidStateException
import com.example.truckercore.shared.modules.personal_data.aggregations.PersonalDataWithFile
import com.example.truckercore.shared.person_details.PersonWithDetails
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PersonWithDetailsTest {

    private val adminProvider = TestAdminDataProvider
    private val driverProvider = TestDriverDataProvider
    private val fileProvider = TestFileDataProvider
    private val pDataWF: PersonalDataWithFile = mockk(relaxed = true)

    @Test
    fun `should create when the person is Admin and all data is provided`() {
        // Arrange
        val admin = adminProvider.getBaseEntity().copy(id = PERSON_ID)
        val file = fileProvider.getBaseEntity().copy(parentId = PERSON_ID)
        every { pDataWF.parentId } returns PERSON_ID

        // Act
        val result = PersonWithDetails(
            person = admin,
            photo = file,
            pDataWFSet = hashSetOf(pDataWF)
        )

        // Assert
        assertTrue(result.person is Admin)
        assertEquals(result.person.id, result.photo?.parentId)
        assertEquals(result.person.id, result.pDataWFSet.first().parentId)
    }

    @Test
    fun `should create when the person is Driver and all data is provided`() {
        // Arrange
        val driver = driverProvider.getBaseEntity().copy(id = PERSON_ID)
        val file = fileProvider.getBaseEntity().copy(parentId = PERSON_ID)
        every { pDataWF.parentId } returns PERSON_ID

        // Act
        val result = PersonWithDetails(
            person = driver,
            photo = file,
            pDataWFSet = hashSetOf(pDataWF)
        )

        // Assert
        assertTrue(result.person is Driver)
        assertEquals(result.person.id, result.photo?.parentId)
        assertEquals(result.person.id, result.pDataWFSet.first().parentId)
    }

    @Test
    fun `should return the userId associated with user`() {
        // Arrange
        val userId = "userId"
        val driver = driverProvider.getBaseEntity().copy(userId = userId, id = PERSON_ID)
        val file = fileProvider.getBaseEntity().copy(parentId = PERSON_ID)
        every { pDataWF.parentId } returns PERSON_ID

        // Act
        val result = PersonWithDetails(
            person = driver,
            photo = file,
            pDataWFSet = hashSetOf(pDataWF)
        )

        // Assert
        assertEquals(userId, result.userId)
    }

    @Test
    fun `should throw InvalidStateException when the photo does not belong to the provided person`() {
        // Arrange
        val person = driverProvider.getBaseEntity().copy(id = PERSON_ID)
        val photo = fileProvider.getBaseEntity().copy(parentId = "WRONG_ID")

        // Act && Assert
        assertThrows<InvalidStateException> {
            PersonWithDetails(
                person = person,
                photo = photo,
                mockk(relaxed = true)
            )
        }

    }

    @Test
    fun `should throw InvalidStateException when the PersonDetails parentId does not belong to the provided person`() {
        // Arrange
        val person = driverProvider.getBaseEntity().copy(id = PERSON_ID)

        // Act && Assert
        assertThrows<InvalidStateException> {
            PersonWithDetails(
                person = person,
                mockk(relaxed = true) { every { parentId } returns "WRONG_ID" }
            )
        }

    }

    companion object {
        private const val PERSON_ID = "personId"
    }

}