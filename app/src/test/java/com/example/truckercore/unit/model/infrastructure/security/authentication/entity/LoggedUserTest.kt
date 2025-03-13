package com.example.truckercore.unit.model.infrastructure.security.authentication.entity

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore._test_data_provider.TestVipDataProvider
import com.example.truckercore.model.infrastructure.security.authentication.entity.LoggedUser
import com.example.truckercore.model.modules.person.shared.person_details.PersonWithDetails
import com.example.truckercore.model.shared.errors.InvalidStateException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LoggedUserTest {

    private val userProvider = TestUserDataProvider
    private val vipProvider = TestVipDataProvider
    private val personWD: PersonWithDetails = mockk()

    @Test
    fun `should create the logged user details when all data is complete`() {
        // Arrange
        val user = userProvider.getBaseEntity().copy(id = USER_ID)
        val vip = vipProvider.getBaseEntity().copy(userId = USER_ID)
        every { personWD.userId } returns USER_ID

        // Act
        val result = LoggedUser(
            user = user,
            personWD = personWD,
            vips = listOf(vip)
        )

        // Arrange
        assertEquals(result.user.id, result.personWD.userId)
        assertEquals(result.user.id, result.vips[0].userId)
    }

    @Test
    fun `should create the logged user details when vips are not provided`() {
        // Arrange
        val user = userProvider.getBaseEntity().copy(id = USER_ID)
        every { personWD.userId } returns USER_ID

        // Act
        val result = LoggedUser(
            user = user,
            personWD = personWD
        )

        // Arrange
        assertEquals(result.user.id, result.personWD.userId)
        assertEquals(result.vips, emptyList())
    }

    @Test
    fun `should throw InvalidStateException when Person Details does not belong to provided user`() {
        // Arrange
        val user = userProvider.getBaseEntity().copy(id = USER_ID)
        every { personWD.userId } returns WRONG_ID

        // Act && Arrange
        val exception = assertThrows<InvalidStateException> {
            LoggedUser(
                user = user,
                personWD = personWD
            )
        }

        assertTrue(exception.message.equals("Person details does not belong to the provided user."))
    }

    @Test
    fun `should throw InvalidStateException when Vip does not belong to provided user`() {
        // Arrange
        val user = userProvider.getBaseEntity().copy(id = USER_ID)
        val vip = vipProvider.getBaseEntity().copy(userId = WRONG_ID)
        every { personWD.userId } returns USER_ID

        // Act && Arrange
        val exception = assertThrows<InvalidStateException> {
            LoggedUser(
                user = user,
                personWD = personWD,
                vips = listOf(vip)
            )
        }

        assertTrue(exception.message.equals("Vip does not belong to the provided user."))
    }

    companion object {
        private const val USER_ID = "userId"
        private const val WRONG_ID = "wrongId"
    }

}