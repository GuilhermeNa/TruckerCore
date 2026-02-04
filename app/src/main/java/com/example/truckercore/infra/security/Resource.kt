package com.example.truckercore.infra.security

enum class Resource {
    COMPANY,
    USER,
    EMPLOYEE,
    PERMISSION_CODE,
    VEHICLE,
    DRIVE_LICENSE,
    CRLV,
    STATE_AET,
    FEDERAL_AET;

    fun isCompany() = this == COMPANY

    fun isUser() = this == USER

    fun isEmployee() = this == EMPLOYEE

    fun isVehicle() = this == VEHICLE

    fun isDriveLicense() = this == DRIVE_LICENSE

}