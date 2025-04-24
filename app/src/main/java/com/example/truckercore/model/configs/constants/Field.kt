package com.example.truckercore.model.configs.constants

/**
 * Enum class representing various fields in the system.
 * Each constant corresponds to a specific field name used within the system.
 */
enum class Field(private val fieldName: String) {
    ID("id"),
    CATEGORY("category"),
    COMPANY_ID("companyId")

    ;

    /**
     * Gets the name of the field.
     *
     * @return The name of the field.
     */
    fun getName(): String = fieldName

}