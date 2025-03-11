package com.example.truckercore.unit.model.modules.business_central.entity

import com.example.truckercore._test_data_provider.TestBusinessCentralDataProvider
import com.example.truckercore._test_data_provider.TestUserDataProvider
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BusinessCentralTest {

    private val centralProvider = TestBusinessCentralDataProvider
    private val userProvider = TestUserDataProvider

    @Test
    fun `should return true when the received id is found in authorized user ids`() {
        // Arrange
        val userId = "userId1"
        val central = centralProvider.getBaseEntity().copy(authorizedUserIds = hashSetOf(userId))
        val user = userProvider.getBaseEntity().copy(id = userId)

        // Act
        val result = central.hasSystemAccess(userId)

        // Assert
        assertTrue(result)
    }

    @Test
    fun `should return false when the received id is not found in authorized user ids`() {
        // Arrange
        val userId = "userId1"
        val central = centralProvider.getBaseEntity().copy(authorizedUserIds = hashSetOf("difId"))
        val user = userProvider.getBaseEntity().copy(id = userId)

        // Act
        val result = central.hasSystemAccess(userId)

        // Assert
        assertFalse(result)
    }

}