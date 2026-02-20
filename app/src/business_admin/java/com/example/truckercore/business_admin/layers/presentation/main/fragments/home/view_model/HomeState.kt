package com.example.truckercore.business_admin.layers.presentation.main.fragments.home.view_model

import android.text.SpannableString
import com.example.truckercore.core.my_lib.expressions.span
import com.example.truckercore.layers.presentation.base.contracts.State

data class HomeState private constructor(
    val businessSpannable: SpannableString,
    val employeesSpannable: SpannableString,
    val fleetSpannable: SpannableString,
    val fineSpannable: SpannableString,
    val interactionEnabled: Boolean = true
) : State {

    fun setInteraction(enabled: Boolean) = copy(interactionEnabled = enabled)

    companion object {
        private const val EMPHASIS_SIZE = 13
        private const val BUSINESS_TXT = "Controle da empresa e\ninformações gerais"
        private const val EMPLOYEE_TXT = "Gestão de equipe e\ncadastros ativos"
        private const val FLEET_TXT = "Veículos e\nacesso a documentos"
        private const val FINE_TXT = "Multas e ocorrências\nhistorico completo"

        fun create() = HomeState(
            businessSpannable =
            BUSINESS_TXT.span("informações gerais", bold = true, size = EMPHASIS_SIZE),

            employeesSpannable =
            EMPLOYEE_TXT.span("cadastros ativos", bold = true, size = EMPHASIS_SIZE),

            fleetSpannable =
            FLEET_TXT.span("documentos", bold = true, size = EMPHASIS_SIZE),

            fineSpannable =
            FINE_TXT.span("historico", bold = true, size = EMPHASIS_SIZE)
        )

    }

}