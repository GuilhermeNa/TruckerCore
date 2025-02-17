package com.example.truckercore.unit.configs.app_constants

import com.example.truckercore.configs.app_constants.Field
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class FieldTest {

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
    }

}