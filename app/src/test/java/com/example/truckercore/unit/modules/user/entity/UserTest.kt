package com.example.truckercore.unit.modules.user.entity

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore.infrastructure.security.permissions.enums.Permission
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class UserTest {

    private val provider = TestUserDataProvider

    @Test
    fun `hasPermission() should return true if user has received permission`() {
        // Arrange
        val expectedPermission = Permission.VIEW_USER
        val user = provider.getBaseEntity()
            .copy(permissions = hashSetOf(Permission.VIEW_USER))

        // Call
        val result = user.hasPermission(expectedPermission)

        // Assertion
        assertTrue(result)
    }

    @Test
    fun `hasPermission() should return false if user has received permission`() {
        // Arrange
        val expectedPermission = Permission.VIEW_USER
        val user = provider.getBaseEntity()
            .copy(permissions = hashSetOf())

        // Call
        val result = user.hasPermission(expectedPermission)

        // Assertion
        assertFalse(result)
    }

    @Test
    fun `isVipActive() should return true if the user is vip and it ends in D+1`() {
        // Arrange
        val entity = provider.getBaseEntity()
            .copy(isVip = true, vipEnd = LocalDateTime.now().plusDays(10))

        // Call
        val result = entity.isVipActive()

        // Assertion
        assertTrue(result)
    }

    @Test
    fun `isVipActive() should return true if the user is vip and it ends today`() {
        // Arrange
        val entity = provider.getBaseEntity()
            .copy(
                isVip = true,
                vipEnd = LocalDateTime.now()
                    .withHour(23)
                    .withMinute(59)
                    .withSecond(59)
            )

        // Call
        val result = entity.isVipActive()

        // Assertion
        assertTrue(result)
    }

    @Test
    fun `isVipActive() should return false if the end date is before today`() {
        // Arrange
        val entity = provider.getBaseEntity()
            .copy(isVip = true, vipEnd = LocalDateTime.now().minusDays(5))

        // Call
        val result = entity.isVipActive()

        // Assertion
        assertFalse(result)
    }

    @Test
    fun `isVipActive() should return false if the user is not vip`() {
        // Arrange
        val entity = provider.getBaseEntity()
            .copy(isVip = false, vipEnd = LocalDateTime.now().plusDays(5))

        // Call
        val result = entity.isVipActive()

        // Assertion
        assertFalse(result)
    }

    @Test
    fun `isVipActive() should return false if the end date is before today and user is not vip`() {
        // Arrange
        val entity = provider.getBaseEntity()
            .copy(isVip = false, vipEnd = LocalDateTime.now().minusDays(5))

        // Call
        val result = entity.isVipActive()

        // Assertion
        assertFalse(result)
    }

}