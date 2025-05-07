package com.example.truckercore.view_model.view_models.welcome_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.R
import com.example.truckercore.model.configs.flavor.Flavor
import com.example.truckercore.view_model.states.WelcomeFragState
import com.example.truckercore.view_model.states.WelcomeFragState.Stage
import com.example.truckercore.view_model.states.WelcomeFragState.Initial
import com.example.truckercore.view_model.states.WelcomeFragState.Success
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val GENERIC_ERROR_MESSAGE =
    "Um erro ocorreu durante o carregamento de recursos de imagem ou texto."

/**
 * WelcomeFragmentViewModel is responsible for managing the state and events of the WelcomeFragment.
 * It holds the data, state, and event management for the fragment, including interaction with the ViewModel's logic
 * related to the ViewPager position, page data, and fragment UI stages.
 */
class WelcomeFragmentViewModel(actualFlavor: Flavor) : ViewModel() {

    // Fragment State --------------------------------------------------------------
    // MutableStateFlow that holds the current state of the fragment.
    private val _fragmentState: MutableStateFlow<WelcomeFragState> = MutableStateFlow(Initial)
    val fragmentState get() = _fragmentState.asStateFlow()

    // Fragment Event --------------------------------------------------------------
    // MutableSharedFlow to emit events that the fragment will react to.
    private val _fragmentEvent = MutableSharedFlow<WelcomeFragEvent>()
    val fragmentEvent get() = _fragmentEvent.asSharedFlow()

    // ViewPager last position ------------------------------------------------------
    // Holds the last position of the ViewPager to manage UI state.
    private var _lastPagerPos: Int = 0
    val pagerPos get() = _lastPagerPos

    //----------------------------------------------------------------------------------------------
    // Initialization block to setup the initial fragment state based on the provided flavor.
    // If an error occurs, it updates the fragment state with an error message.
    //----------------------------------------------------------------------------------------------
    init {
        try {
            val state = Success(
                data = buildFragData(actualFlavor),
                uiStage = Stage.UserInFirsPage
            )

            updateFragmentState(state)

        } catch (e: Exception) {
          /*  updateFragmentState(
                WelcomeFragState.Error(
                    type = ErrorType.UnknownError,
                    message = GENERIC_ERROR_MESSAGE
                )
            )*/
        }
    }

    /**
     * Builds the fragment data based on the provided flavor.
     * It returns a list of WelcomePagerData representing the data to be shown in the ViewPager.
     */
    private fun buildFragData(it: Flavor): List<WelcomePagerData> {

        // Common welcome data for all flavors
        fun getCommonWelcomeData() = WelcomePagerData(
            res = R.drawable.gif_welcome,
            title = "Bem vindo",
            message = "Aqui você encontrará funcionalidades focadas em ajudar motoristas e gestores de frota." +
                    " Nosso objetivo é facilitar seu dia a dia com praticidade e eficiência." +
                    " Conte conosco para tornar sua jornada mais simples e agradável." +
                    " Estamos felizes em ter você com a gente."
        )

        // Common document data for all flavors
        fun getCommonDocumentData() = WelcomePagerData(
            res = R.drawable.gif_clock,
            title = "De olho no prazo",
            message = "Nunca mais perca a data de renovação dos seus documentos." +
                    " Cadastre-os e deixe que a gente fique de olho nos prazos." +
                    " Enviaremos notificações para que você não perca as datas importantes." +
                    " Mantenha sua documentação sempre em dia com facilidade e sem preocupações."
        )

        // Business admin specific data
        fun getBusinessAdminIntegrationData() = WelcomePagerData(
            res = R.drawable.git_integration,
            title = "Integração com sua equipe",
            message = "Forneça acesso a outros membros da sua equipe." +
                    " Compartilhe informações essenciais para o dia a dia, como, por exemplo, a documentação necessária para um caminhão transitar."
        )

        // Common working together data for all flavors
        fun getCommonWorkingTogether() = WelcomePagerData(
            res = R.drawable.gif_working,
            title = "Estamos em desenvolvimento",
            message = "Ainda estamos na nossa fase inicial de desenvolvimento." +
                    " Portanto, espere muitas novidades em breve." +
                    " Nosso objetivo é atendê-lo com excelência."
        )

        // Return the appropriate list of data based on the flavor
        return when (it) {
            Flavor.INDIVIDUAL -> listOf(
                getCommonWelcomeData(),
                getCommonDocumentData(),
                getBusinessAdminIntegrationData(),
                getCommonWorkingTogether()
            )

            Flavor.ADMIN -> listOf(
                getCommonWelcomeData(),
                getCommonDocumentData(),
                getBusinessAdminIntegrationData(),
                getCommonWorkingTogether()
            )

            Flavor.DRIVER -> listOf(
                getCommonWelcomeData(),
                getCommonDocumentData(),
                getBusinessAdminIntegrationData(),
                getCommonWorkingTogether()
            )
        }
    }

    /**
     * Updates the current state of the fragment with the new state.
     */
    private fun updateFragmentState(newState: WelcomeFragState) {
        _fragmentState.value = newState
    }

    /**
     * Notifies the ViewModel that the ViewPager's position has changed.
     * This updates the UI stage based on the new position.
     */
    fun notifyPagerChanged(position: Int) {

        // Helper function to get the current fragment data
        fun getData() = (_fragmentState.value as Success).data

        // Helper function to determine the UI stage based on the position
        fun getActualStage(position: Int): Stage {
            val firstPage = 0
            val lastPage = getData().size - 1
            return when (position) {
                firstPage -> Stage.UserInFirsPage
                lastPage -> Stage.UserInLastPage
                else -> Stage.UserInIntermediatePages
            }
        }

        // Update the last position of the pager
        _lastPagerPos = position

        // Create a new Success state with updated pager position and stage
        val newState = Success(getData(), getActualStage(position))
        updateFragmentState(newState)
    }

    /**
     * Emits a new event to the fragment's event flow.
     */
    fun setEvent(newEvent: WelcomeFragEvent) {
        viewModelScope.launch {
            _fragmentEvent.emit(newEvent)
        }
    }

    /**
     * Returns the current UI stage of the fragment (e.g., first page, last page, or intermediate pages).
     */
    fun getUiStage() = (_fragmentState.value as Success).uiStage

}