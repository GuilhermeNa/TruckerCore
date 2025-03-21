package com.example.truckercore.view_model.welcome_fragment

import androidx.lifecycle.ViewModel
import com.example.truckercore.R
import com.example.truckercore.model.shared.errors.InvalidStateException
import com.example.truckercore.view.enums.Flavor
import com.example.truckercore.view_model.states.FragState
import com.example.truckercore.view_model.states.FragState.Initial
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val NULL_FLAVOR = "Received flavor is not null and data cannot be created without it."

class WelcomeFragmentViewModel : ViewModel() {

    private val _fragmentState: MutableStateFlow<FragState<List<WelcomePagerData>>> =
        MutableStateFlow(Initial)

    val fragmentState get() = _fragmentState.asStateFlow()

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
        title = "Bem Vindo",
        message = "Aqui você encontrará várias funcionalidades voltadas a ajudar motoristas e gestores de frota." +
                " \n Nosso objetivo é facilitar seu dia a dia com praticidade e eficiência. " +
                "Conte conosco para tornar sua jornada mais simples e agradável." +
                " \n Estamos felizes em ter você com a gente."
    )

    private fun getCommonDocumentData() = WelcomePagerData(
        res = R.drawable.git_searching,
        title = "Cadastro de Documentos",
        message = "Cadastre documentos, como licenciamento, licenças estaduais e federais, CNH, entre outros." +
                "\nAcompanhe os prazos de renovação e receba avisos antecipados para não perder as datas importantes." +
                "\nMantenha sua documentação sempre em dia com facilidade e sem preocupações." +
                "\nTudo organizado em um único lugar para sua conveniência.\nNão deixe mais prazos passarem despercebidos."
    )

    private fun getBusinessAdminIntegrationData() = WelcomePagerData(
        res = R.drawable.git_integration,
        title = "Integração com seus motoristas",
        message = "Como gestor, você tem a opção de cadastrar e fornecer acesso aos seus motoristas, " +
                "permitindo que eles acessem a documentação necessária." +
                "\nAlém disso, você pode compartilhar outras informações adicionais essenciais para o trabalho diário." +
                "\nIsso facilita a gestão da frota, melhora a comunicação e mantém todos na mesma página." +
                "\nCom essa integração, os motoristas terão acesso fácil e rápido aos dados que precisam para realizar suas atividades." +
                "\nAcompanhe tudo de forma eficiente e organizada em um único lugar."
    )

    private fun getCommonWorkingTogether() = WelcomePagerData(
        res = R.drawable.gif_working,
        title = "Foco no que importa",
        message = "Estamos aqui para cuidar dos detalhes administrativos, para que você possa se concentrar no que realmente importa, o sucesso do seu negócio." +
                "\nVamos trabalhar juntos para otimizar seu tempo e suas tarefas."
    )

    private fun updateFragmentState(newState: FragState<List<WelcomePagerData>>) {
        _fragmentState.value = newState
    }

}