package com.example.truckercore.view_model.welcome_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.R
import com.example.truckercore.model.shared.errors.InvalidStateException
import com.example.truckercore.view.enums.Flavor
import com.example.truckercore.view_model.states.FragState
import com.example.truckercore.view_model.states.FragState.Initial
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val NULL_FLAVOR = "Received flavor is not null and data cannot be created without it."

class WelcomeFragmentViewModel : ViewModel() {

    private val _fragmentState: MutableStateFlow<FragState<List<WelcomePagerData>>> =
        MutableStateFlow(Initial)
    val fragmentState get() = _fragmentState.asStateFlow()
    val data get() = (_fragmentState.value as FragState.Loaded).data

    private val _fragmentEvent = MutableSharedFlow<WelcomeFragmentEvent>()
    val fragmentEvent get() = _fragmentEvent.asSharedFlow()

    //
    private val _leftFabState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Invisible)
    val leftFabState get() = _leftFabState.asStateFlow()

    private var _rightFabState: FabState = FabState.Paginate
    val rightFabState get() = _rightFabState

    private var _lastPagerPos: Int = 0
    val pagerPos get() = _lastPagerPos

    //----------------------------------------------------------------------------------------------

    fun run(flavor: Flavor?) {
        val state = flavor?.let { FragState.Loaded(buildFragData(it)) }
            ?: FragState.Error(InvalidStateException(NULL_FLAVOR))

        updateFragmentState(state)
    }

    private fun buildFragData(it: Flavor): List<WelcomePagerData> {
        return when (it) {
            Flavor.INDIVIDUAL -> listOf(
                getCommonWelcomeData(),
                getCommonDocumentData(),
                getBusinessAdminIntegrationData(),
                getCommonWorkingTogether()
            )

            Flavor.BUSINESS_ADMIN -> listOf(
                getCommonWelcomeData(),
                getCommonDocumentData(),
                getBusinessAdminIntegrationData(),
                getCommonWorkingTogether()
            )

            Flavor.BUSINESS_DRIVER -> listOf(
                getCommonWelcomeData(),
                getCommonDocumentData(),
                getBusinessAdminIntegrationData(),
                getCommonWorkingTogether()
            )
        }
    }

    private fun getCommonWelcomeData() = WelcomePagerData(
        res = R.drawable.gif_welcome,
        title = "Bem vindo",
        message = "Aqui você encontrará funcionalidades focadas em ajudar motoristas e gestores de frota." +
                " Nosso objetivo é facilitar seu dia a dia com praticidade e eficiência." +
                " Conte conosco para tornar sua jornada mais simples e agradável." +
                " Estamos felizes em ter você com a gente."
    )

    private fun getCommonDocumentData() = WelcomePagerData(
        res = R.drawable.gif_clock,
        title = "De olho no prazo",
        message = "Nunca mais perca a data de renovação dos seus documentos." +
                " Cadastre-os e deixe que a gente fique de olho nos prazos." +
                " Enviaremos notificações para que você não perca as datas importantes." +
                " Mantenha sua documentação sempre em dia com facilidade e sem preocupações."
    )

    private fun getBusinessAdminIntegrationData() = WelcomePagerData(
        res = R.drawable.git_integration,
        title = "Integração com sua equipe",
        message = "Forneça acesso a outros membros da sua equipe." +
                " Compartilhe informações essenciais para o dia a dia, como, por exemplo, a documentação necessária para um caminhão transitar."
    )

    private fun getCommonWorkingTogether() = WelcomePagerData(
        res = R.drawable.gif_working,
        title = "Estamos em desenvolvimento",
        message = "Ainda estamos na nossa fase inicial de desenvolvimento." +
                " Portanto, espere muitas novidades em breve." +
                " Nosso objetivo é atendê-lo com excelência."
    )

    private fun updateFragmentState(newState: FragState<List<WelcomePagerData>>) {
        _fragmentState.value = newState
    }

    fun notifyPagerChanged(position: Int) {
        _lastPagerPos = position
        checkLeftFabVisibility(position)
        checkRightFabVisibility(position)
    }

    private fun checkLeftFabVisibility(position: Int) {
        val firstPager = 0
        _leftFabState.value = when (position) {
            firstPager -> ViewState.Invisible
            else -> ViewState.Enabled
        }
    }

    private fun checkRightFabVisibility(position: Int) {
        val lastPager = data.size - 1
        _rightFabState = when (position) {
            lastPager -> FabState.Navigate
            else -> FabState.Paginate
        }
    }

    fun setEvent(newEvent: WelcomeFragmentEvent) {
        viewModelScope.launch {
            _fragmentEvent.emit(newEvent)
        }
    }

}