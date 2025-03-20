package com.example.truckercore.view.enums

enum class Flavor(private val fieldName: String) {
    INDIVIDUAL("Trucker"),
    BUSINESS_ADMIN("Trucker Empresa"),
    BUSINESS_DRIVER("Trucker Motorista");

    fun getName() = fieldName

}