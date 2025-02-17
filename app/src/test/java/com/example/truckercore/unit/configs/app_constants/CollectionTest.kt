package com.example.truckercore.unit.configs.app_constants

import com.example.truckercore.configs.app_constants.Collection
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class CollectionTest {

    @Test
    fun `test getName should return correct collection name`() {
        assertEquals("business_central", Collection.CENTRAL)
        assertEquals("cargo", Collection.CARGO.getName())
        assertEquals("travel", Collection.TRAVEL.getName())
        assertEquals("freight", Collection.FREIGHT.getName())
        assertEquals("outlay", Collection.OUTLAY.getName())
        assertEquals("refuel", Collection.REFUEL.getName())
        assertEquals("refund", Collection.REFUND.getName())
        assertEquals("aid", Collection.AID.getName())
        assertEquals("freight_unloading", Collection.FREIGHT_UNLOADING.getName())
        assertEquals("payload", Collection.PAYLOAD.getName())
        assertEquals("freight_loading", Collection.FREIGHT_LOADING.getName())
        assertEquals("file", Collection.FILE.getName())
        assertEquals("personal_data", Collection.PERSONAL_DATA.getName())
        assertEquals("employee_contract", Collection.EMPLOYEE_CONTRACT.getName())
        assertEquals("vacation", Collection.VACATION.getName())
        assertEquals("thirteenth", Collection.THIRTEENTH.getName())
        assertEquals("allowance", Collection.ALLOWANCE.getName())
        assertEquals("enjoy", Collection.ENJOY.getName())
        assertEquals("payroll", Collection.PAYROLL.getName())
        assertEquals("admin", Collection.ADMIN.getName())
        assertEquals("driver", Collection.DRIVER.getName())
        assertEquals("truck", Collection.TRUCK.getName())
        assertEquals("user", Collection.USER.getName())
        assertEquals("trailer", Collection.TRAILER.getName())
        assertEquals("licensing", Collection.LICENSING.getName())
    }

}