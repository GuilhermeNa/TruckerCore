package com.example.truckercore.view.fragments.verifying_email

import com.example.truckercore.databinding.FragmentVerifyingEmailBinding
import com.example.truckercore.view.expressions.showSnackBarRed
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailFragData
import kotlinx.coroutines.delay

class VerifyingEmailFragStateHandler(
    private val binding: FragmentVerifyingEmailBinding,
    email: String
) {

    private val view = binding.root.rootView
    private val textProvider = VerifyingEmailTextProvider(email)

    //----------------------------------------------------------------------------------------------

    fun setEmailSentWIthButtonDisabled() {
        setButton(enable = false)
        setInState1()
        val viewData = textProvider.invoke(true)
        bindData(viewData)
    }

    suspend fun setEmailSentWithButtonEnabled() {
        val viewData = textProvider.invoke(true)
        bindData(viewData)
        setInState2()
        delay(500)
        setButton(enable = true)

    }

    fun setEmailNotSendState() {
        setInState2()
        val viewData = textProvider.invoke(false)
        bindData(viewData)
    }

    fun setErrorState(message: String) {
        view.showSnackBarRed(message)
    }

    private fun bindData(data: VerifyingEmailFragData) {
        binding.run {
            fragVerifyingEmailTitle.text = data.title
            fragVerifyingEmailMessage.text = data.text
            fragVerifyingEmailSentTo.text = data.email
        }
    }

    private fun setInState1() {
            binding.fragVerifyingEmailMain.transitionToStart()
    }

    private fun setInState2() {
        binding.fragVerifyingEmailMain.transitionToEnd()
    }

    private fun setButton(enable: Boolean) {
        binding.fragVerifyingEmailButtonResend.isEnabled = enable
    }

    fun updateCounter(counter: Int) {
        val value = if (counter >= 10) "$counter" else "0$counter"
        binding.fragVerifyingEmailTimer.text = value
    }

}


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
