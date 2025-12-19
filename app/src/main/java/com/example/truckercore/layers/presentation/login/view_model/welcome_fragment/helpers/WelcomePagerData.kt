package com.example.truckercore.layers.presentation.login.view_model.welcome_fragment.helpers

import android.os.Parcelable
import com.example.truckercore.R
import kotlinx.parcelize.Parcelize

/**
 * Data class representing the information for each page in the WelcomePager.
 * The class is annotated with @Parcelize to make it Parcelable, which allows instances of this class to be passed
 * between Android components via Intents or Bundles.
 *
 * @property res An integer representing the resource ID of an image or animation that will be displayed on the page.
 * @property title The title text displayed on the page.
 * @property message The message text displayed on the page.
 */
@Parcelize
data class WelcomePagerData(
    /**
     * The resource ID of the image or animation associated with this page.
     * It can refer to drawable resources (e.g., `R.drawable.some_image`).
     */
    val res: Int,
    /**
     * The title text to be displayed at the top or in a prominent position on the page.
     */
    val title: String,
    /**
     * The message text to be displayed on the page, providing additional information or context.
     */
    val message: String
) : Parcelable {

    companion object {

        // Common welcome data for all flavors
        fun welcomeData() = WelcomePagerData(
            res = R.drawable.gif_welcome,
            title = "Bem vindo",
            message = "Aqui você encontrará funcionalidades focadas em ajudar motoristas e gestores de frota." +
                    " Nosso objetivo é facilitar seu dia a dia com praticidade e eficiência." +
                    " Conte conosco para tornar sua jornada mais simples e agradável." +
                    " Estamos felizes em ter você com a gente."
        )

        // Common document data for all flavors
        fun documentData() = WelcomePagerData(
            res = R.drawable.gif_clock,
            title = "De olho no prazo",
            message = "Nunca mais perca a data de renovação dos seus documentos." +
                    " Cadastre-os e deixe que a gente fique de olho nos prazos." +
                    " Enviaremos notificações para que você não perca as datas importantes." +
                    " Mantenha sua documentação sempre em dia com facilidade e sem preocupações."
        )

        // Business admin specific data
        fun integrationData() = WelcomePagerData(
            res = R.drawable.git_integration,
            title = "Integração com sua equipe",
            message = "Forneça acesso a outros membros da sua equipe." +
                    " Compartilhe informações essenciais para o dia a dia, como, por exemplo, a documentação necessária para um caminhão transitar."
        )

        // Common working together data for all flavors
        fun inProgressData() = WelcomePagerData(
            res = R.drawable.gif_working,
            title = "Estamos em desenvolvimento",
            message = "Ainda estamos na nossa fase inicial de desenvolvimento." +
                    " Portanto, espere muitas novidades em breve." +
                    " Nosso objetivo é atendê-lo com excelência."
        )

    }

}