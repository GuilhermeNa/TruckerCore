package com.example.truckercore.layers.presentation.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.truckercore.R
import com.example.truckercore.core.my_lib.expressions.loadGif
import com.example.truckercore.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private var _binding: ActivityNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        animateGif()
        bindTitleMessage()
        bindBodyMessage()
        setListeners()
    }

    private fun setListeners() {
        binding.actErrorFab.setOnClickListener {
            finish()
        }
    }

    private fun animateGif() {
        val gif = intent.getIntExtra(GIF_TAG, R.drawable.gif_unknown)
        binding.actErrorImage.loadGif(gif, this)
    }

    private fun bindTitleMessage() {
        val message = intent.getStringExtra(HEADER_TAG) ?: DEFAULT_CRITICAL_TITLE
        binding.actErrorTitle.text = message
    }

    private fun bindBodyMessage() {
        val message = intent.getStringExtra(MESSAGE_TAG) ?: DEFAULT_CRITICAL_MESSAGE
        binding.actErrorMessage.text = message
    }

    companion object {

        private const val HEADER_TAG = "error_header"

        private const val MESSAGE_TAG = "error_body"

        private const val GIF_TAG = "gif_resource"

        private const val DEFAULT_CRITICAL_TITLE = "Ocorreu um erro"

        private const val DEFAULT_CRITICAL_MESSAGE =
            "Algo deu errado. Por favor, tente novamente.\n" +
                    "Se o problema persistir, entre em contato com o suporte."

        fun newInstance(
            context: Context,
            gifRes: Int? = null,
            title: String? = null,
            message: String ? = null
        ): Intent =
            Intent(context, NotificationActivity::class.java).apply {
                putExtra(GIF_TAG, gifRes)
                putExtra(HEADER_TAG, title)
                putExtra(MESSAGE_TAG, message)
            }

    }

}