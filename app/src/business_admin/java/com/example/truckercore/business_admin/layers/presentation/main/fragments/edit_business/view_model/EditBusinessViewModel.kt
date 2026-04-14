package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.core.my_lib.expressions.foldRequired
import com.example.truckercore.layers.data_2.repository.interfaces.CompanyRepository
import com.example.truckercore.layers.domain.base.others.Cnpj
import com.example.truckercore.layers.domain.base.others.CompanyName
import com.example.truckercore.layers.domain.base.others.MunicipalRegistration
import com.example.truckercore.layers.domain.base.others.StateRegistration
import com.example.truckercore.layers.domain.model.company.Company
import com.example.truckercore.layers.domain.model.company.CompanyOptional
import com.example.truckercore.layers.domain.singletons.session.SessionManager
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.layers.presentation.base.managers.StateManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class EditBusinessViewModel(
    private val sessionManager: SessionManager,
    private val repository: CompanyRepository
) : BaseViewModel() {

    private val stateManager = StateManager(EditBusinessState())
    val stateFlow = stateManager.stateFlow
    val state get() = stateManager.currentState()

    private val _messageChannel = Channel<String>(Channel.BUFFERED)
    val messageFlow get() = _messageChannel.receiveAsFlow()

    //----------------------------------------------------------------------------------------------
    fun fetchCompany() {
        viewModelScope.launch {
            repository.fetch(id = sessionManager.companyId())
                .foldRequired(::handleSuccess, ::handleError)
        }
    }

    private fun handleSuccess(company: Company) {
        val editBusinessView = EditBusinessView.from(company)
        stateManager.update(state.companyFound(editBusinessView))
    }

    private fun handleError(error: AppException) {
        Log.e(classTag, "${error.message}", error)
        stateManager.update(state.failure())
    }

    //----------------------------------------------------------------------------------------------
    // PUBLIC
    //----------------------------------------------------------------------------------------------
    fun updateName(value: String) {
        val newState = state.updateName(value)
        stateManager.update(newState)
    }

    fun updateCnpj(value: String) {
        val newState = state.updateCnpj(value)
        stateManager.update(newState)
    }

    fun updateState(value: String) {
        val newState = state.updateState(value)
        stateManager.update(newState)
    }

    fun updateMunicipal(value: String) {
        val newState = state.updateMunicipal(value)
        stateManager.update(newState)
    }

    fun updateOpening(value: String) {
        val newState = state.updateOpening(value)
        stateManager.update(newState)
    }

    fun save() {
        if (!dataReady()) {
            _messageChannel.trySend(ERROR)
            return
        }

        val optional = getCompanyOptional()

        optional
        // TODO("salvar")

    }

    private fun getCompanyOptional(): CompanyOptional {
        return with(state.companyView) {
            CompanyOptional(
                cnpj = Cnpj(cnpj),
                name = CompanyName(name),
                stateRegistration = StateRegistration(stateReg),
                municipalRegistration = MunicipalRegistration(municipalReg),
                opening = LocalDate.of(year, month, day)
            )
        }
    }

    private fun dataReady() = !state.hasError

    private companion object {
        private const val ERROR = "Campos inválidos"
    }

}