package com.example.truckercore.unit.model.modules.vip.entity

import com.example.truckercore._test_data_provider.TestVipDataProvider
import com.example.truckercore.model.modules.vip.entity.Vip
import com.example.truckercore.model.shared.enums.PersistenceStatus
import com.example.truckercore.model.shared.errors.InvalidStateException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import kotlin.test.assertTrue

internal class VipTest {

    private val provider = TestVipDataProvider

    @Test
    fun `should create an valid entity`() {
        // Arrange
        val vip = provider.getBaseEntity()

        // Assert
        assertTrue(vip.vipStart.isBefore(vip.vipEnd))
        assertTrue(vip.notificationDate.isBefore(vip.vipEnd))
        assertTrue(vip.vipStart.isBefore(vip.notificationDate))
    }

    @Test
    fun `should throw InvalidStateException when vipStart is after vipEnd`() {
        // Act && Assert
        assertThrows<InvalidStateException> {
            Vip(
                businessCentralId = "centralId",
                id = "vipId",
                lastModifierId = "userId",
                creationDate = LocalDateTime.now(),
                lastUpdate = LocalDateTime.now(),
                persistenceStatus = PersistenceStatus.PERSISTED,
                vipStart = LocalDateTime.now().plusDays(10),
                vipEnd = LocalDateTime.now().plusDays(5),
                notificationDate = LocalDateTime.now().plusDays(2),
                userId = "userId",
                isActive = true
            )
        }
    }

    @Test
    fun `should throw InvalidStateException when notification is after vipEnd`() {
        // Act && Assert
        assertThrows<InvalidStateException> {
            Vip(
                businessCentralId = "centralId",
                id = "vipId",
                lastModifierId = "userId",
                creationDate = LocalDateTime.now(),
                lastUpdate = LocalDateTime.now(),
                persistenceStatus = PersistenceStatus.PERSISTED,
                vipStart = LocalDateTime.now(),
                vipEnd = LocalDateTime.now().plusDays(5),
                notificationDate = LocalDateTime.now().plusDays(10),
                userId = "userId",
                isActive = true
            )
        }
    }

    @Test
    fun `should throw InvalidStateException when vipStart is after notification`() {
        // Act && Assert
        assertThrows<InvalidStateException> {
            Vip(
                businessCentralId = "centralId",
                id = "vipId",
                lastModifierId = "userId",
                creationDate = LocalDateTime.now(),
                lastUpdate = LocalDateTime.now(),
                persistenceStatus = PersistenceStatus.PERSISTED,
                vipStart = LocalDateTime.now().plusDays(5),
                vipEnd = LocalDateTime.now().plusDays(10),
                notificationDate = LocalDateTime.now(),
                userId = "userId",
                isActive = true
            )
        }
    }

}