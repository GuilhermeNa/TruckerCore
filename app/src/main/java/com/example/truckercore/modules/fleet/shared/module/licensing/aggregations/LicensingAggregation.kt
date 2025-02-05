package com.example.truckercore.modules.fleet.shared.module.licensing.aggregations

data class LicensingAggregation(val file: Boolean = false) {

    fun shouldGetFile() = file

}