package com.example.truckercore.layers.presentation.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.truckercore.R
import com.example.truckercore.core.my_lib.expressions.loadGif
import com.example.truckercore.databinding.ActivityNotificationBinding

/**
 * Activity responsible for displaying notification-style error messages.
 *
 * This screen shows:
 * - An animated GIF
 * - A title message
 * - A body message
 *
 * All content is configurable via Intent extras, with fallback
 * default values when extras are not provided.
 */
class NotificationActivity : AppCompatActivity() {

    // ViewBinding backing property
    private var _binding: ActivityNotificationBinding? = null

    // Non-nullable access to the binding
    private val binding get() = _binding!!

    /**
     * Called when the activity is first created.
     * Initializes UI, binds data from Intent extras,
     * and sets click listeners.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate ViewBinding and set the content view
        _binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bind UI elements
        animateGif()
        bindTitleMessage()
        bindBodyMessage()
        setListeners()
    }

    /**
     * Sets click listeners for UI components.
     */
    private fun setListeners() {
        // Close the activity when the FAB is clicked
        binding.actErrorFab.setOnClickListener {
            finish()
        }
    }

    /**
     * Loads and animates the GIF displayed on the screen.
     * Falls back to a default GIF if none is provided.
     */
    private fun animateGif() {
        val gif = intent.getIntExtra(GIF_TAG, R.drawable.gif_unknown)
        binding.actErrorImage.loadGif(gif, this)
    }

    /**
     * Binds the title message from the Intent extras.
     * Uses a default title if none is provided.
     */
    private fun bindTitleMessage() {
        val message = intent.getStringExtra(HEADER_TAG) ?: DEFAULT_CRITICAL_TITLE
        binding.actErrorTitle.text = message
    }

    /**
     * Binds the body message from the Intent extras.
     * Uses a default message if none is provided.
     */
    private fun bindBodyMessage() {
        val message = intent.getStringExtra(MESSAGE_TAG) ?: DEFAULT_CRITICAL_MESSAGE
        binding.actErrorMessage.text = message
    }

    companion object {

        // Intent extra keys
        private const val HEADER_TAG = "error_header"
        private const val MESSAGE_TAG = "error_body"
        private const val GIF_TAG = "gif_resource"

        // Default UI messages
        private const val DEFAULT_CRITICAL_TITLE = "An error occurred"

        private const val DEFAULT_CRITICAL_MESSAGE =
            "Something went wrong. Please try again.\n" +
                    "If the problem persists, contact support."

        /**
         * Creates an Intent to start [NotificationActivity].
         *
         * @param context Context used to start the activity
         * @param gifRes Optional GIF resource ID
         * @param title Optional title text
         * @param message Optional body message
         *
         * @return Configured Intent for launching the activity
         */
        fun newInstance(
            context: Context,
            gifRes: Int? = null,
            title: String? = null,
            message: String? = null
        ): Intent =
            Intent(context, NotificationActivity::class.java).apply {
                putExtra(GIF_TAG, gifRes)
                putExtra(HEADER_TAG, title)
                putExtra(MESSAGE_TAG, message)
            }
    }

}