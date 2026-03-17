package com.example.truckercore.business_admin.layers.presentation.main.fragments.business.view_model

import com.example.truckercore.core.my_lib.expressions.span
import com.example.truckercore.layers.data_2.repository.interfaces.UserRepository
import com.example.truckercore.layers.domain.base.enums.Status
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.others.Cnpj
import com.example.truckercore.layers.domain.base.others.CompanyName
import com.example.truckercore.layers.domain.base.others.MunicipalRegistration
import com.example.truckercore.layers.domain.base.others.StateRegistration
import com.example.truckercore.layers.domain.model.company.Company
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.layers.presentation.base.managers.StateManager

class BusinessViewModel(

) : BaseViewModel() {

    private val stateManager = StateManager(BusinessState())
    val stateFlow get() = stateManager.stateFlow

    val notification = NOTIFICATION.span(TXT_TO_EDIT, size = 15, bold = true)

    val company = Company(
        id = CompanyID("123"),
        status = Status.ACTIVE,
        _cnpj = Cnpj("09.319.376/0001-38"),
        _name = CompanyName("GH Transportes LTDA"),
        _stateRegistration = StateRegistration("12345678910"),
        _municipalRegistration = MunicipalRegistration("12345678")
    )

    fun getRntrc() = "012553245"

    fun getName(): String = company.name?.value ?: DEFAULT

    fun getCnpj() = company.cnpj?.value ?: DEFAULT

    fun getOpening() = company.opening?.toString() ?: DEFAULT

    fun getMunicipalInsc() = company.municipalRegistration?.value ?: DEFAULT

    fun getStateInsc() = company.stateRegistration?.value ?: DEFAULT

    private companion object {
        private const val NOTIFICATION = "Você ainda não completou o cadastro da sua empresa." +
                " Finalize-o para ter acesso a todas as funcionalidades."
        private const val TXT_TO_EDIT = "cadastro da sua empresa"

        private const val DEFAULT = "-"

    }

}