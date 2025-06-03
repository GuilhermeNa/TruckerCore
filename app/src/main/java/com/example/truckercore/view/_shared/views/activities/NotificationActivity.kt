package com.example.truckercore.view._shared.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.truckercore.R
import com.example.truckercore._shared.expressions.loadGif
import com.example.truckercore.databinding.ActivityNotificationBinding

private const val HEADER_MESSAGE = "error_header"

private const val NULL_HEADER_MESSAGE = "Error header message is null."

private const val BODY_MESSAGE = "error_body"

private const val NULL_BODY_MESSAGE = "Error body message is null."

private const val GIF_RESOURCE = "gif_resource"

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
        val gif = intent.getIntExtra(GIF_RESOURCE, R.drawable.gif_unknown)
        binding.actErrorImage.loadGif(gif, this)
    }

    private fun bindTitleMessage() {
        val message = intent.getStringExtra(HEADER_MESSAGE) ?: NULL_HEADER_MESSAGE
        binding.actErrorTitle.text = message
    }

    private fun bindBodyMessage() {
        val message = intent.getStringExtra(BODY_MESSAGE) ?: NULL_BODY_MESSAGE
        binding.actErrorMessage.text = message
    }

    companion object {
        fun newInstance(
            context: Context,
            gifRes: Int? = null,
            title: String,
            message: String
        ): Intent =
            Intent(context, NotificationActivity::class.java).apply {
                putExtra(GIF_RESOURCE, gifRes)
                putExtra(HEADER_MESSAGE, title)
                putExtra(BODY_MESSAGE, message)
            }
    }

}