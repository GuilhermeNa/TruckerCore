package com.example.truckercore.shared.utils.expressions

import com.example.truckercore._data_provider.TestStorageFileDataProvider
import com.example.truckercore.configs.database.Field
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class DtoExpressionsKtTest {

    @Test
    fun `getMissingFields() should return the missing fields in the DTO object when there are any`() {
        //Object
        val data = TestStorageFileDataProvider.getBaseDto()
            .copy(masterUid = null, url = null, id = "")

        //Call
        val result = data.getMissingFields()

        // Assertions
        assertTrue(result.contains(Field.MASTER_UID))
        assertTrue(result.contains(Field.URL))
        assertTrue(result.contains(Field.ID))
    }

    @Test
    fun `getMissingFields() should an empty list when required parameters are provided`() {
        //Object
        val data = TestStorageFileDataProvider.getBaseDto()

        //Call
        val result = data.getMissingFields()

        // Assertion
        assertTrue(result.isEmpty())
    }

}