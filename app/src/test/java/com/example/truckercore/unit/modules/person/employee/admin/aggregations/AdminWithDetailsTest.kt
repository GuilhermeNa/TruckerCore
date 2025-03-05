package com.example.truckercore.unit.modules.person.employee.admin.aggregations

import com.example.truckercore._test_data_provider.TestAdminDataProvider
import com.example.truckercore._test_data_provider.TestFileDataProvider
import com.example.truckercore._test_data_provider.TestPersonalDataDataProvider
import com.example.truckercore.modules.person.employee.admin.aggregations.AdminWithDetails
import com.example.truckercore.shared.errors.InvalidStateException
import com.example.truckercore.shared.modules.personal_data.aggregations.PersonalDataWithFile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class AdminWithDetailsTest {

    private val adminProvider = TestAdminDataProvider
    private val fileProvider = TestFileDataProvider
    private val pDataProvider = TestPersonalDataDataProvider

    @Test
    fun `should create AdminWithDetails with all fields`() {
        // Arrange
        val admin = adminProvider.getBaseEntity().copy(id = com.example.truckercore.unit.modules.person.employee.admin.aggregations.AdminWithDetailsTest.Companion.ADMIN_ID)
        val file = fileProvider.getBaseEntity().copy(parentId = com.example.truckercore.unit.modules.person.employee.admin.aggregations.AdminWithDetailsTest.Companion.ADMIN_ID)
        val pDataWF = PersonalDataWithFile(
            pDataProvider.getBaseEntity().copy(id = com.example.truckercore.unit.modules.person.employee.admin.aggregations.AdminWithDetailsTest.Companion.P_DATA_ID, parentId = com.example.truckercore.unit.modules.person.employee.admin.aggregations.AdminWithDetailsTest.Companion.ADMIN_ID),
            listOf(fileProvider.getBaseEntity().copy(parentId = com.example.truckercore.unit.modules.person.employee.admin.aggregations.AdminWithDetailsTest.Companion.P_DATA_ID))
        )

        // Call
        val adminWD =
            com.example.truckercore.modules.person.employee.admin.aggregations.AdminWithDetails(
                admin = admin,
                photo = file,
                personalDataWithFile = setOf(pDataWF)
            )

        // Arrange
        assertEquals(adminWD.admin.id, adminWD.photo?.parentId)
        assertEquals(adminWD.admin.id, adminWD.personalDataWithFile.first().parentId)
    }

    @Test
    fun `should throw InvalidStateException when photo id does not belong to provided admin`() {
        // Arrange
        val admin = adminProvider.getBaseEntity().copy(id = com.example.truckercore.unit.modules.person.employee.admin.aggregations.AdminWithDetailsTest.Companion.ADMIN_ID)
        val file = fileProvider.getBaseEntity().copy(parentId = "wrongAdminId") // Invalid parentId
        val pDataWF = PersonalDataWithFile(
            pDataProvider.getBaseEntity().copy(id = com.example.truckercore.unit.modules.person.employee.admin.aggregations.AdminWithDetailsTest.Companion.P_DATA_ID, parentId = com.example.truckercore.unit.modules.person.employee.admin.aggregations.AdminWithDetailsTest.Companion.ADMIN_ID),
            listOf(fileProvider.getBaseEntity().copy(parentId = com.example.truckercore.unit.modules.person.employee.admin.aggregations.AdminWithDetailsTest.Companion.P_DATA_ID))
        )

        // Act & Assert
        assertThrows<InvalidStateException> {
            com.example.truckercore.modules.person.employee.admin.aggregations.AdminWithDetails(
                admin = admin,
                photo = file,
                personalDataWithFile = setOf(pDataWF)
            )
        }

    }

    @Test
    fun `should throw InvalidStateException when pDataWF id does not belong to provided admin`() {
        // Arrange
        val admin = adminProvider.getBaseEntity().copy(id = com.example.truckercore.unit.modules.person.employee.admin.aggregations.AdminWithDetailsTest.Companion.ADMIN_ID)
        val file = fileProvider.getBaseEntity().copy(parentId = com.example.truckercore.unit.modules.person.employee.admin.aggregations.AdminWithDetailsTest.Companion.ADMIN_ID)
        val pDataWF = PersonalDataWithFile(
            pDataProvider.getBaseEntity()
                .copy(id = com.example.truckercore.unit.modules.person.employee.admin.aggregations.AdminWithDetailsTest.Companion.P_DATA_ID, parentId = "wrongAdminId"), // Invalid parentId
            listOf(fileProvider.getBaseEntity().copy(parentId = com.example.truckercore.unit.modules.person.employee.admin.aggregations.AdminWithDetailsTest.Companion.P_DATA_ID))
        )

        // Act & Assert
        assertThrows<InvalidStateException> {
            com.example.truckercore.modules.person.employee.admin.aggregations.AdminWithDetails(
                admin = admin,
                photo = file,
                personalDataWithFile = setOf(pDataWF)
            )
        }
    }

    companion object {
        private const val ADMIN_ID = "adminId"
        private const val P_DATA_ID = "pDataId"
    }

}