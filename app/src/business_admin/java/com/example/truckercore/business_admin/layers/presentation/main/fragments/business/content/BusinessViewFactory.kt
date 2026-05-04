package com.example.truckercore.business_admin.layers.presentation.main.fragments.business.content

import com.example.truckercore.core.my_lib.files.dateTimeFormatPtBr
import com.example.truckercore.layers.domain.model.company.Company

object BusinessViewFactory {

    fun from(company: Company): BusinessView =
        with(company) {
            BusinessView(
                name = name?.value ?: "-",
                cnpj = cnpj?.value ?: "-",
                inscState = stateRegistration?.value ?: "-",
                inscMunicipal = municipalRegistration?.value ?: "-",
                opening = opening?.format(dateTimeFormatPtBr) ?: "-",
                isFilled = isFilled()
            )
        }

}