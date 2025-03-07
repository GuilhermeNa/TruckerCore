package com.example.truckercore.unit.model.infrastructure.security.authentication.entity

import com.example.truckercore._test_data_provider.TestUserDataProvider
import com.example.truckercore.infrastructure.security.authentication.entity.LoggedUserDetails
import com.example.truckercore.shared.errors.InvalidStateException
import com.example.truckercore.modules.person.shared.person_details.PersonWithDetails
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class LoggedUserDetailsTest {

    private val userProvider = TestUserDataProvider
    private val personWD: PersonWithDetails = mockk()

    @Test
    fun `should create the logged user details when all data is complete`() {
        // Arrange
        val user = userProvider.getBaseEntity().copy(id = USER_ID)
        every { personWD.userId } returns USER_ID

        // Act
        val result = LoggedUserDetails(
            user = user,
            personWD = personWD
        )

        // Arrange
        assertEquals(result.user.id, result.personWD.userId)
    }

    @Test
    fun `should throw InvalidStateException when Person Details does not belong to provided user`() {
        // Arrange
        val user = userProvider.getBaseEntity().copy(id = USER_ID)
        every { personWD.userId } returns WRONG_ID

        // Act && Arrange
        assertThrows<InvalidStateException> {
            LoggedUserDetails(
                user = user,
                personWD = personWD
            )
        }
    }

    companion object {
        private const val USER_ID = "userId"
        private const val WRONG_ID = "wrongId"
    }

}