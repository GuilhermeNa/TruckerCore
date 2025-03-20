package com.example.truckercore.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.truckercore.R
import com.example.truckercore.databinding.ActivityErrorBinding
import com.example.truckercore.view.expressions.loadGif
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val HEADER_MESSAGE = "error_header"

private const val NULL_HEADER_MESSAGE = "Error header message is null."

private const val BODY_MESSAGE = "error_body"

private const val NULL_BODY_MESSAGE = "Error body message is null."

class ErrorActivity : AppCompatActivity() {

    private var _binding: ActivityErrorBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityErrorBinding.inflate(layoutInflater)
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
        binding.actErrorImage.loadGif(R.drawable.gif_error, this)
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

        fun newInstance(context: Context, errorHeader: String, errorBody: String): Intent =
            Intent(context, ErrorActivity::class.java).apply {
                putExtra(HEADER_MESSAGE, errorHeader)
                putExtra(BODY_MESSAGE, errorBody)
            }

    }

}