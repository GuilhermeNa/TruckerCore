package com.example.truckercore.view.nav_login.fragments.continue_register

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import com.example.truckercore.R
import com.example.truckercore._shared.expressions.navigateToDirection
import com.example.truckercore.databinding.FragmentContinueRegisterBinding
import com.example.truckercore.view_model.view_models.continue_register.state.ContinueRegisterState
import com.example.truckercore.view_model.view_models.continue_register.ContinueRegisterViewModel
import com.example.truckercore.view_model.view_models.continue_register.state.ContinueRegisterDirection
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment responsible for displaying the Continue Register screen.
 *
 * This screen allows the user to:
 * - View their current email.
 * - See whether their email is verified.
 * - Check if a user account exists for the email.
 *
 * The UI is driven by a [ContinueRegisterViewModel] which exposes a [StateFlow] of [ContinueRegisterState].
 * Depending on the UI state, the fragment updates the UI or triggers navigation events.
 *
 * Responsibilities:
 * - Observes the ViewModel state and updates the UI accordingly.
 * - Shows status indicators using level-list drawables.
 * - Handles user actions like "continue" or "change email".
 */
class ContinueRegisterFragment : Fragment() {

    private var _binding: FragmentContinueRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ContinueRegisterViewModel by viewModel()

    // ---------------------------------------------------------------------------------------------
    // Lifecycle - OnCreate
    // ---------------------------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initialize()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
             /*   viewModel.uiState.collect { state ->
                    when (state) {
                        ContinueRegisterState.Loading -> Unit
                        ContinueRegisterState.Error -> navigateToEmailAuthFragment()
                        is ContinueRegisterState.Success -> {
                            _uiModel = state.data
                            bind()
                        }
                    }
                }*/
            }
        }
    }

    // ---------------------------------------------------------------------------------------------
    // UI Binding
    // ---------------------------------------------------------------------------------------------

    private fun bind() {
        bindEmail()
        bindEmailStatus()
        bindVerifiedStatus()
        bindUserExistsStatus()
    }

    private fun bindUserExistsStatus() {
        val expectedLevel = uiModel.expectedUserExistsImageLevel
        val imageLevel = getImageLevel(expectedLevel)
        binding.fragContinueRegisterNameStatusText.setDrawableLevel(imageLevel)
    }

    private fun bindVerifiedStatus() {
        val expectedLevel = uiModel.expectedVerifiedImageLevel
        val imageLevel = getImageLevel(expectedLevel)
        binding.fragContinueRegisterVerifiedStatusText.setDrawableLevel(imageLevel)
    }

    private fun bindEmailStatus() {
        val imageLevel = getImageLevel(ContinueRegisterImageLevel.DONE)
        binding.fragContinueRegisterEmailStatusText.setDrawableLevel(imageLevel)
    }

    private fun bindEmail() {
        binding.fragContinueRegisterEmailText.text = uiModel.email
    }

    private fun getImageLevel(level: ContinueRegisterImageLevel): Drawable? {
        return ContextCompat.getDrawable(
            requireContext(),
            R.drawable.level_list_status_icons
        )?.apply { setLevel(level.value) }
    }

    private fun TextView.setDrawableLevel(imageLevel: Drawable?) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(imageLevel, null, null, null)
    }

    // ---------------------------------------------------------------------------------------------
    // Navigation
    // ---------------------------------------------------------------------------------------------

    private fun navigateToEmailAuthFragment() {
        viewModel.clearCurrentUser()
        val direction = ContinueRegisterFragmentDirections
            .actionContinueRegisterFragmentToEmailAuthFragment()
        navigateToDirection(direction)
    }

    private fun setOnContinueCLickListener() {
        binding.fragContinueRegisterFinishRegisterButton.setOnClickListener {
            navigateToDirection(getContinueRegisterDirection())
        }
    }

    private fun getContinueRegisterDirection(): NavDirections {
        val expectedDirection = uiModel.expectedNavigationDirection
        return when (expectedDirection) {
            ContinueRegisterDirection.VERIFY_EMAIL -> ContinueRegisterFragmentDirections
                .actionContinueRegisterFragmentToVerifyingEmailFragment()

            ContinueRegisterDirection.CREATE_USER -> TODO()
        }
    }

    private fun setOnNewEmailClickListener() {
        binding.fragContinueRegisterNewEmailButton.setOnClickListener {
            navigateToEmailAuthFragment()
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Lifecycle - View Binding
    // ---------------------------------------------------------------------------------------------

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContinueRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnContinueCLickListener()
        setOnNewEmailClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}