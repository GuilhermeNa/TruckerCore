package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.truckercore.core.error.core.AppException
import com.example.truckercore.core.my_lib.expressions.foldRequired
import com.example.truckercore.layers.data_2.repository.interfaces.CompanyRepository
import com.example.truckercore.layers.domain.model.company.Company
import com.example.truckercore.layers.domain.singletons.session.SessionManager
import com.example.truckercore.layers.presentation.base.abstractions.view_model.BaseViewModel
import com.example.truckercore.layers.presentation.base.managers.EffectManager
import com.example.truckercore.layers.presentation.base.managers.StateManager
import kotlinx.coroutines.launch

class EditBusinessViewModel(
    private val sessionManager: SessionManager,
    private val repository: CompanyRepository
) : BaseViewModel() {

    private val stateManager =
        StateManager<EditBusinessState>(EditBusinessState.Loading)
    val stateFlow = stateManager.stateFlow

    private val effectManager = EffectManager<EditBusinessEffect>()
    val effectFlow = effectManager.effectFlow

    private val validationHash = hashMapOf(
        Pair(NAME, false),
        Pair(CNPJ, false),
        Pair(STATE, false),
        Pair(MUNICIPAL, false),
        Pair(OPENING, false)
    )
    private var ignored = 0
    private val shouldIgnore get() = ignored < AMOUNT_OF_FIELDS + NUMBER_OF_TXT_MASKS

    //----------------------------------------------------------------------------------------------
    // FETCH COMPANY
    //----------------------------------------------------------------------------------------------
    fun fetchCompany() {
        viewModelScope.launch {
            repository.fetch(id = sessionManager.companyId())
                .foldRequired(::handleSuccess, ::handleError)
        }
    }

    private fun handleSuccess(company: Company) {
        // Emit new State
        val newState = stateManager.currentState().waitingInput()
        stateManager.update(newState)

        // Emit bind data Effect
        val data = EditBusinessView.from(company)
        effectManager.trySend(EditBusinessEffect.BindData(data))
    }

    private fun handleError(error: AppException) {
        Log.e(classTag, "${error.message}", error)
        val newState = stateManager.currentState().failure()
        stateManager.update(newState)
    }

    //----------------------------------------------------------------------------------------------
    // PUBLIC
    //----------------------------------------------------------------------------------------------
    private fun validateField(field: Int, actual: Boolean) {
        validationHash[field] = actual
        updateState()
    }

    private fun updateState() {
        if (shouldIgnore) {
            ignored++
            return
        }

        val newState =
            if (validationHash.containsValue(false)) {
                stateManager.currentState().waitingInput()
            } else {
                stateManager.currentState().readyToSave()
            }

        stateManager.update(newState)
    }

    fun validateName(text: String): String? {
        val clean = text.trim()
        val hasMinimumSize = clean.length >= NAME_MIN_SIZE

        return if (clean.isEmpty() || hasMinimumSize) {
            validateField(NAME, true)
            null
        } else {
            validateField(NAME, false)
            SHORT_NAME
        }
    }

    fun validateCnpj(text: String): String? {
        val clean = text
            .replace(Regex("\\D"), "")
            .trim()
        val correctSize = clean.length == CNPJ_SIZE

        return if (clean.isEmpty() || correctSize) {
            validateField(CNPJ, true)
            null
        } else {
            validateField(CNPJ, false)
            INCORRECT_CNPJ
        }
    }

    fun validateState(text: String): String? {
        val clean = text.trim()
        val correctSize = clean.length in STATE_MIN_SIZE..STATE_MAX_SIZE

        return if (text.isEmpty() || correctSize) {
            validateField(STATE, true)
            null
        } else {
            validateField(STATE, false)
            INCORRECT_STATE
        }
    }

    fun validateMunicipal(text: String): String? {
        val clean = text.trim()
        val correctSize = clean.length in MUNICIPAL_MIN_SIZE..MUNICIPAL_MAX_SIZE

        return if (text.isEmpty() || correctSize) {
            validateField(MUNICIPAL, true)
            null
        } else {
            validateField(MUNICIPAL, false)
            INCORRECT_MUNICIPAL
        }
    }

    fun validateOpening(text: String): String? {
        val clean = text
            .replace(Regex("\\D"), "")
            .trim()
        val correctSize = clean.length == DATE_SIZE

        return if (clean.isEmpty() || correctSize) {
            validateField(OPENING, true)
            null
        } else {
            validateField(OPENING, false)
            INCORRECT_DATE
        }
    }

    private companion object {
        private const val CNPJ_SIZE = 14
        private const val NAME_MIN_SIZE = 3
        private const val DATE_SIZE = 6

        private const val AMOUNT_OF_FIELDS = 5
        private const val NUMBER_OF_TXT_MASKS = 2

        private const val STATE_MIN_SIZE = 8
        private const val STATE_MAX_SIZE = 12

        private const val MUNICIPAL_MIN_SIZE = 7
        private const val MUNICIPAL_MAX_SIZE = 15

        private const val INCORRECT_DATE = "Data deve ter 6 caracteres"
        private const val SHORT_NAME = "Nome deve ter pelo menos 3 caracteres"
        private const val INCORRECT_CNPJ = "Cnpj deve ter 14 caracteres"
        private const val INCORRECT_STATE = "Deve ter entre 8 e 12 caracteres"
        private const val INCORRECT_MUNICIPAL = "Deve ter entre 7 e 15 caracteres"

        private const val NAME = 1
        private const val CNPJ = 2
        private const val STATE = 3
        private const val MUNICIPAL = 4
        private const val OPENING = 5
    }

}

/*
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
*/
