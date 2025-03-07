package com.example.truckercore.unit.model.shared.modules.personal_data.aggregations

import com.example.truckercore._test_data_provider.TestFileDataProvider
import com.example.truckercore._test_data_provider.TestPersonalDataDataProvider
import com.example.truckercore.shared.errors.InvalidStateException
import com.example.truckercore.shared.modules.personal_data.aggregations.PersonalDataWithFile
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class PersonalDataWithFileTest {

    private val pDataProvider = TestPersonalDataDataProvider
    private val fileProvider = TestFileDataProvider

    @Test
    fun `should create PersonalDataWithFile when all data is provided`() {
        // Arrange
        val pDataWF = PersonalDataWithFile(
            pDataProvider.getBaseEntity().copy(id = P_DATA_ID),
            listOf(fileProvider.getBaseEntity().copy(parentId = P_DATA_ID))
        )

        // Assertions
        val pDataId = pDataWF.pData.id
        val fileParentId = pDataWF.files.first().parentId
        assertEquals(pDataId, fileParentId)
    }

    @Test
    fun `should create a PersonalDataWithFile with empty list when files are not provided`() {
        // Arrange
        val pDataWF = PersonalDataWithFile(pDataProvider.getBaseEntity())

        // Assertions
        assertEquals(pDataWF.files, emptyList())
    }

    @Test
    fun `should throw InvalidStateException when the File does not belong to PersonalData`() {
        // Act && Assert
        assertThrows<InvalidStateException> {
            PersonalDataWithFile(
                pDataProvider.getBaseEntity().copy(id = P_DATA_ID),
                listOf(fileProvider.getBaseEntity().copy(parentId = "wrongId"))
            )
        }
    }

    companion object {
        private const val P_DATA_ID = "pDataId"
    }

}