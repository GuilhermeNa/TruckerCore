package com.example.truckercore.view.enums

enum class Flavor(private val fieldName: String) {
    INDIVIDUAL("individual"),
    BUSINESS_ADMIN("business_admin"),
    BUSINESS_DRIVER("business_driver");

    fun getName() = fieldName

}