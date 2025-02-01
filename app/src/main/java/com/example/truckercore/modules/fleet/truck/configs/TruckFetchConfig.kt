package com.example.truckercore.modules.fleet.truck.configs

data class TruckFetchConfig(
    val shouldAddDriver: Boolean = false,
    val shouldAddTrailers: Boolean = false,
    val shouldAddLicensing: Boolean = false
)