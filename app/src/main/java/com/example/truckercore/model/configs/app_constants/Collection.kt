package com.example.truckercore.model.configs.app_constants

/**
 * Enum class representing different collections in the system.
 * Each constant corresponds to a collection with a specific name.
 */
enum class Collection(private val collectionName: String) {
    CENTRAL("business_central"),
    CARGO("cargo"),
    TRAVEL("travel"),
    FREIGHT("freight"),
    OUTLAY("outlay"),
    REFUEL("refuel"),
    REFUND("refund"),
    AID("aid"),
    FREIGHT_UNLOADING("freight_unloading"),
    PAYLOAD("payload"),
    FREIGHT_LOADING("freight_loading"),
    FILE("file"),
    PERSONAL_DATA("personal_data"),
    EMPLOYEE_CONTRACT("employee_contract"),
    VACATION("vacation"),
    THIRTEENTH("thirteenth"),
    ALLOWANCE("allowance"),
    ENJOY("enjoy"),
    PAYROLL("payroll"),
    ADMIN("admin"),
    DRIVER("driver"),
    TRUCK("truck"),
    USER("user"),
    TRAILER("trailer"),
    LICENSING("licensing"),
    VIP("vip"),
    NOTIFICATION("notification")
    ;

    /**
     * Gets the name of the collection.
     *
     * @return The name of the collection.
     */
    fun getName(): String = collectionName

}