package com.example.truckercore.view.fragments.verifying_email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore.databinding.FragmentVerifyingEmailBinding
import com.example.truckercore.view_model.view_models.verifying_email.VerifyingEmailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment responsible for verifying the user's email after account creation.
 *
 * This screen displays a timer and manages the email verification process,
 * including resending the verification email and reacting to the result.
 *
 * Features:
 * - Countdown timer to prevent immediate resends
 * - UI state transitions depending on verification status
 * - Handles effects such as success/failure messages and navigation
 * - Integrates tightly with ViewModel using State/Event/Effect patterns
 */
class VerifyingEmailFragment : Fragment() {

    // View Binding
    private var _binding: FragmentVerifyingEmailBinding? = null
    val binding get() = _binding!!

    // Fragment Handlers
    private val stateHandler by lazy {
        VerifyingEmailFragStateHandler(viewModel.email, this)
    }
    private val effectHandler by lazy { VerifyingEmailFragEffectHandler(this) }

    // ViewModel
    private val viewModel: VerifyingEmailViewModel by viewModel()

    //----------------------------------------------------------------------------------------------
    // On Create
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Observes ViewModel state/effect/event streams during the STARTED lifecycle state
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setFragmentStateManager()
                setCounterStateManager()
                setEffectManager()
            }
        }
    }

    /**
     * Collects the ViewModel state and delegates visual updates to [StateHandler].
     */
    private fun CoroutineScope.setFragmentStateManager() {
        launch {
            viewModel.state.collect { state ->
                stateHandler.onStateChanged(state)
            }
        }
    }

    private fun CoroutineScope.setCounterStateManager() {
        launch {
            viewModel.counter.collect { vle ->
                stateHandler.incrementProgress(vle)
            }
        }
    }

    /**
     * Collects one-time effects from the ViewModel and applies them through [EffectHandler].
     */
    private fun CoroutineScope.setEffectManager() {
        launch {
     /*       viewModel.effect.collect { effect ->

            }*/
        }
    }

    //----------------------------------------------------------------------------------------------
    // On Create View
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyingEmailBinding.inflate(layoutInflater)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // On View Created
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    // Fragment Lifecycle - onDestroyView ----------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}





