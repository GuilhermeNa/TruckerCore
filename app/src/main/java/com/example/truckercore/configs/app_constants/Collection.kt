package com.example.truckercore.configs.app_constants

enum class Collection(private val collectionName: String) {
    CENTRAL("central"),
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
    DRIVER("driver"),
    USER("user");

    // Função para acessar o nome da coleção
    fun getName(): String = collectionName
}