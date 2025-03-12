package com.example.truckercore.unit.model.app_constants

import com.example.truckercore.model.configs.app_constants.Field
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test
import kotlin.test.assertTrue

class FieldTest {

    private val expectedEnums: HashSet<Field> = hashSetOf(
        Field.BUSINESS_CENTRAL_ID,
        Field.EMPLOYEE_ID,
        Field.INITIAL_DATE,
        Field.ODOMETER,
        Field.TRUCK_ID,
        Field.STATUS,
        Field.CUSTOMER_ID,
        Field.TRAVEL_ID,
        Field.LABEL_ID,
        Field.FREIGHT_ID,
        Field.PARENT_ID,
        Field.TYPE,
        Field.VACATION_ID,
        Field.USER_ID,
        Field.PLATE,
        Field.EXERCISE,
        Field.BRAND,
        Field.CATEGORY,
        Field.COLOR,
        Field.URL,
        Field.ID,
        Field.LAST_MODIFIER_ID,
        Field.CREATION_DATE,
        Field.LAST_UPDATE,
        Field.IS_UPDATING,
        Field.NAME,
        Field.EMAIL,
        Field.NUMBER,
        Field.EMISSION_DATE,
        Field.EXPIRATION_DATE,
        Field.KEYS,
        Field.LEVEL,
        Field.IS_VIP,
        Field.VIP_START,
        Field.VIP_END,
        Field.PERSON_FLAG,
        Field.PERMISSIONS,
        Field.PERSISTENCE_STATUS,
        Field.EMPLOYEE_STATUS,
        Field.AUTHORIZED_USER_IDS,
        Field.IS_ACTIVE,
        Field.NOTIFICATION_DATE
    )

    @Test
    fun `test getName should return correct field name`() {
        assertEquals("centralId", Field.BUSINESS_CENTRAL_ID.getName())
        assertEquals("employeeId", Field.EMPLOYEE_ID.getName())
        assertEquals("initialDate", Field.INITIAL_DATE.getName())
        assertEquals("odometer", Field.ODOMETER.getName())
        assertEquals("truckId", Field.TRUCK_ID.getName())
        assertEquals("status", Field.STATUS.getName())
        assertEquals("customerId", Field.CUSTOMER_ID.getName())
        assertEquals("travelId", Field.TRAVEL_ID.getName())
        assertEquals("labelId", Field.LABEL_ID.getName())
        assertEquals("freightId", Field.FREIGHT_ID.getName())
        assertEquals("parentId", Field.PARENT_ID.getName())
        assertEquals("type", Field.TYPE.getName())
        assertEquals("vacationId", Field.VACATION_ID.getName())
        assertEquals("userId", Field.USER_ID.getName())
        assertEquals("plate", Field.PLATE.getName())
        assertEquals("exercise", Field.EXERCISE.getName())
        assertEquals("brand", Field.BRAND.getName())
        assertEquals("category", Field.CATEGORY.getName())
        assertEquals("color", Field.COLOR.getName())
        assertEquals("url", Field.URL.getName())
        assertEquals("id", Field.ID.getName())
        assertEquals("lastModifierId", Field.LAST_MODIFIER_ID.getName())
        assertEquals("creationDate", Field.CREATION_DATE.getName())
        assertEquals("lastUpdate", Field.LAST_UPDATE.getName())
        assertEquals("isUpdating", Field.IS_UPDATING.getName())
        assertEquals("name", Field.NAME.getName())
        assertEquals("email", Field.EMAIL.getName())
        assertEquals("number", Field.NUMBER.getName())
        assertEquals("emissionDate", Field.EMISSION_DATE.getName())
        assertEquals("expirationDate", Field.EXPIRATION_DATE.getName())
        assertEquals("level", Field.LEVEL.getName())
        assertEquals("personFlag", Field.PERSON_FLAG.getName())
        assertEquals("permissions", Field.PERMISSIONS.getName())
        assertEquals("persistenceStatus", Field.PERSISTENCE_STATUS.getName())
        assertEquals("employeeStatus", Field.EMPLOYEE_STATUS.getName())
        assertEquals("keys", Field.KEYS.getName())
        assertEquals("isVip", Field.IS_VIP.getName())
        assertEquals("vipStart", Field.VIP_START.getName())
        assertEquals("vipEnd", Field.VIP_END.getName())
        assertEquals("authorizedUserIds", Field.AUTHORIZED_USER_IDS.getName())
        assertEquals("isActive", Field.IS_ACTIVE.getName())
        assertEquals("notificationDate", Field.NOTIFICATION_DATE.getName())
    }

    @Test
    fun `should contain the expected enums`() {
        // Arrange
        val actualEnums = Field.entries.toHashSet()

        // Assert
        actualEnums.forEach { enum ->
            assertTrue(expectedEnums.contains(enum))
        }

    }

}