/*
package com.example.truckercore.unit.model.infrastructure.security.authentication.entity

import com.example.truckercore.model.infrastructure.security.authentication.entity.SessionInfo
import com.example.truckercore.model.modules.person.shared.person_details.PersonWithDetails
import com.example.truckercore.model.shared.errors.InvalidStateException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SessionInfoTest {

    private val userProvider = TestUserDataProvider
    private val vipProvider = TestVipDataProvider
    private val centralProvider = TestBusinessCentralDataProvider
    private val personWD: PersonWithDetails = mockk()

    @Test
    fun `should create the logged user details when all data is complete`() {
        // Arrange
        val user = userProvider.getBaseEntity().copy(id = USER_ID, businessCentralId = CENTRAL_ID)
        val central = centralProvider.getBaseEntity().copy(id = CENTRAL_ID)
        val vip = vipProvider.getBaseEntity().copy(userId = USER_ID)
        every { personWD.userId } returns USER_ID

        // Act
        val result = SessionInfo(
            user = user,
            central = central,
            personWD = personWD,
            vips = listOf(vip)
        )

        // Arrange
        assertEquals(result.user.id, result.personWD.userId)
        assertEquals(result.user.businessCentralId, result.central.id)
        assertEquals(result.user.id, result.vips[0].userId)
    }

    @Test
    fun `should create the logged user details when vips are not provided`() {
        // Arrange
        val user = userProvider.getBaseEntity().copy(id = USER_ID, businessCentralId = CENTRAL_ID)
        val central = centralProvider.getBaseEntity().copy(id = CENTRAL_ID)
        every { personWD.userId } returns USER_ID

        // Act
        val result = SessionInfo(
            user = user,
            central = central,
            personWD = personWD
        )

        // Arrange
        assertEquals(result.user.id, result.personWD.userId)
        assertEquals(result.user.businessCentralId, result.central.id)
        assertEquals(result.vips, emptyList())
    }

    @Test
    fun `should throw InvalidStateException when Person Details does not belong to provided user`() {
        // Arrange
        val user = userProvider.getBaseEntity().copy(id = USER_ID, businessCentralId = CENTRAL_ID)
        val central = centralProvider.getBaseEntity().copy(id = CENTRAL_ID)
        every { personWD.userId } returns WRONG_ID

        // Act && Arrange
        val exception = assertThrows<InvalidStateException> {
            SessionInfo(
                user = user,
                central = central,
                personWD = personWD
            )
        }

        assertTrue(exception.message.equals("Person details does not belong to the provided user."))
    }

    @Test
    fun `should throw InvalidStateException when Vip does not belong to provided user`() {
        // Arrange
        val user = userProvider.getBaseEntity().copy(id = USER_ID, businessCentralId = CENTRAL_ID)
        val central = centralProvider.getBaseEntity().copy(id = CENTRAL_ID)
        val vip = vipProvider.getBaseEntity().copy(userId = WRONG_ID)
        every { personWD.userId } returns USER_ID

        // Act && Arrange
        val exception = assertThrows<InvalidStateException> {
            SessionInfo(
                user = user,
                central = central,
                personWD = personWD,
                vips = listOf(vip)
            )
        }

        assertTrue(exception.message.equals("Vip does not belong to the provided user."))
    }

    @Test
    fun `should throw InvalidStateException when Central does not belong to provided user`() {
        // Arrange
        val user = userProvider.getBaseEntity().copy(id = USER_ID, businessCentralId = WRONG_ID)
        val central = centralProvider.getBaseEntity().copy(id = CENTRAL_ID)
        val vip = vipProvider.getBaseEntity().copy(userId = USER_ID)
        every { personWD.userId } returns USER_ID

        // Act && Arrange
        val exception = assertThrows<InvalidStateException> {
            SessionInfo(
                user = user,
                central = central,
                personWD = personWD,
                vips = listOf(vip)
            )
        }

        assertTrue(exception.message.equals("Business Central does not belong to the provided user."))
    }

    companion object {
        private const val USER_ID = "userId"
        private const val CENTRAL_ID = "centralId"
        private const val WRONG_ID = "wrongId"
    }

}*/
