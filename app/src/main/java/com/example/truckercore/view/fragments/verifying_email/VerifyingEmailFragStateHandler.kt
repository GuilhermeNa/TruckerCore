package com.example.truckercore.view.fragments.verifying_email

import com.example.truckercore.databinding.FragmentVerifyingEmailBinding
import com.example.truckercore.view.expressions.showSnackBarRed
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragData
import kotlinx.coroutines.delay




private class VerifyingEmailTextProvider(private val email: String) {

    operator fun invoke(emailSent: Boolean) =
        if (emailSent) createDataWhenEmailSent(email)
        else createDataWhenEmailDoesNotSent(email)

    private fun createDataWhenEmailSent(email: String): VerifyingEmailFragData {
        val title = "Aguardando verificação!"
        val text = "Te enviamos um email de validação. Por favor, verifique sua caixa de" +
                " entrada (ou pasta de spam) e clique no link de verificação para continuar."

        return VerifyingEmailFragData(
            title = title,
            text = text,
            email = email
        )

    }

    private fun createDataWhenEmailDoesNotSent(email: String): VerifyingEmailFragData {
        val title = "Falha ao enviar verificação"
        val text = "Houve um problema ao tentar enviar o e-mail de verificação. Verifique sua " +
                "conexão com a internet e tente novamente."

        return VerifyingEmailFragData(
            title = title,
            text = text,
            email = email
        )
    }

}
