package com.example.truckercore.business_admin.layers.presentation.main.fragments.business.content

data class BusinessView(
    val name: String? = "-",
    val cnpj: String? = "-",
    val inscState: String? = "-",
    val inscMunicipal: String? = "-",
    val opening: String? = "-",

    val isFilled: Boolean
) {

    val fabEnabled get() = isFilled

    val notificationEnabled get() = !isFilled

}