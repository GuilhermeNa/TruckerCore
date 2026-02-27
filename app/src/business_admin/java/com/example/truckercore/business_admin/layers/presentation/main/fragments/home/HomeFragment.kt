package com.example.truckercore.business_admin.layers.presentation.main.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import com.example.truckercore.business_admin.layers.presentation.main.fragments.business.BusinessFragment
import com.example.truckercore.business_admin.layers.presentation.main.fragments.employee.EmployeeFragment
import com.example.truckercore.business_admin.layers.presentation.main.fragments.fine.FineFragment
import com.example.truckercore.business_admin.layers.presentation.main.fragments.fleet.FleetFragment
import com.example.truckercore.business_admin.layers.presentation.main.fragments.home.view_model.HomeState
import com.example.truckercore.business_admin.layers.presentation.main.fragments.home.view_model.HomeViewModel
import com.example.truckercore.core.my_lib.expressions.navigateToDirection
import com.example.truckercore.core.my_lib.expressions.popClickEffect
import com.example.truckercore.databinding.FragmentHomeBinding
import com.example.truckercore.layers.presentation.base.abstractions.view.private.PrivateLockedFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * HomeFragment
 *
 * Main entry fragment of the application after authentication.
 *
 * This fragment acts as a navigation hub, providing access to specialized feature screens:
 * - [BusinessFragment]
 * - [EmployeeFragment]
 * - [FleetFragment]
 * - [FineFragment]
 *
 * Architecture:
 * - Uses ViewBinding for UI access.
 * - Uses a ViewModel [HomeViewModel] backed by a StateFlow for UI state management.
 * - Follows a unidirectional data flow approach where UI reacts to state updates.
 *
 * Interaction Control:
 * - Click interactions are temporarily disabled to prevent double taps and navigation race conditions.
 * - A small UI delay (UI_TIMER) ensures ripple animations complete before navigation occurs.
 *
 * Extensibility:
 * - Designed to easily support additional navigation cards in the future.
 * - Simply add new state properties, UI bindings, and click listeners following the existing pattern.
 */
class HomeFragment : PrivateLockedFragment() {

    // Cleared in onDestroyView to prevent memory leaks.
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // ViewModel providing UI state and interaction control.
    private val viewModel: HomeViewModel by viewModel()

    // Resource manager responsible for handling ripple color states.
    private var fragRes = HomeResource()

    // ---------------------------------------------------------------------------------------------
    // Lifecycle - onCreate
    // ---------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { state ->
                    bindCardText(state)
                    handleCardEnabled(state.interactionEnabled)
                }
            }
        }
    }

    /**
     * Binds formatted Spannable text from state into the corresponding UI cards.
     */
    private fun bindCardText(state: HomeState) {
        binding.fragHomeBusinessDescription.text = state.businessSpannable
        binding.fragHomeEmployeesDescription.text = state.employeesSpannable
        binding.fragHomeFleetDescription.text = state.fleetSpannable
        binding.fragHomeFineDescription.text = state.fineSpannable
    }

    /**
     * Enables or disables card ripple interaction.
     *
     * When disabling interaction:
     * - A delay is applied to allow ripple/click animations to complete.
     *
     * @param interactionEnabled true if cards should be interactive
     */
    private suspend fun handleCardEnabled(interactionEnabled: Boolean) {
        // Get correct ripple effect from resource
        val color = fragRes.get(interactionEnabled)

        // Ensures animation completion before removing ripple effect
        if (!interactionEnabled) delay(UI_TIMER)

        binding.fragHomeBusinessCard.rippleColor = color
        binding.fragHomeEmployeesCard.rippleColor = color
        binding.fragHomeFleetCard.rippleColor = color
        binding.fragHomeFineCard.rippleColor = color
    }

    // ---------------------------------------------------------------------------------------------
    // Lifecycle - onCreateView
    // ---------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    // ---------------------------------------------------------------------------------------------
    // Lifecycle - onViewCreated
    // ---------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragmentResource()
        setOnClickListeners()
    }

    /**
     * Initializes base ripple color resource only once.
     */
    private fun initFragmentResource() {
        if (fragRes.isInitialized()) return
        fragRes.setBaseRipple(binding.fragHomeBusinessCard.rippleColor)
    }

    /**
     * Sets click listeners for all navigation cards.
     */
    private fun setOnClickListeners() {

        binding.fragHomeBusinessCard.setOnClickListener {
            if (!interactionEnabled()) return@setOnClickListener

            val direction = HomeFragmentDirections.actionNavHomeToBusinessFragment()
            triggerCardInteraction(direction, it)
        }

        binding.fragHomeEmployeesCard.setOnClickListener {
            if (!interactionEnabled()) return@setOnClickListener

            val direction = HomeFragmentDirections.actionNavHomeToEmployeeFragment()
            triggerCardInteraction(direction, it)
        }

        binding.fragHomeFleetCard.setOnClickListener {
            if (!interactionEnabled()) return@setOnClickListener

            val direction = HomeFragmentDirections.actionNavHomeToFleetFragment()
            triggerCardInteraction(direction, it)
        }

        binding.fragHomeFineCard.setOnClickListener {
            if (!interactionEnabled()) return@setOnClickListener

            val direction = HomeFragmentDirections.actionNavHomeToFineFragment()
            triggerCardInteraction(direction, it)
        }
    }

    /**
     * Handles the full interaction lifecycle for a navigation card.
     *
     * Responsibilities:
     * - Disables further UI interaction to prevent double-click navigation.
     * - Triggers the click animation effect.
     * - Delays navigation to allow ripple/animation completion.
     *
     * This method centralizes navigation behavior, ensuring:
     * - Consistent UX across all cards.
     * - Protection against rapid repeated taps.
     * - Cleaner click listener implementations.
     *
     * @param dir The navigation direction generated by SafeArgs.
     * @param view The clicked view used to trigger visual feedback.
     */
    private fun triggerCardInteraction(dir: NavDirections, view: View) {

        // Immediately disable further interactions
        viewModel.setInteraction(enabled = false)

        // Trigger visual click animation
        view.popClickEffect()

        // Delay navigation to allow animation completion
        lifecycleScope.launch {
            delay(UI_TIMER)
            navigateToDirection(dir)
        }
    }

    /**
     * Returns whether UI interaction is currently enabled.
     */
    private fun interactionEnabled() =
        viewModel.stateFlow.value.interactionEnabled

    //----------------------------------------------------------------------------------------------
    // on Resume
    //----------------------------------------------------------------------------------------------
    override fun onResume() {
        super.onResume()

        // Re-enables interaction when fragment resumes.
        viewModel.setInteraction(enabled = true)
    }

    //----------------------------------------------------------------------------------------------
    // on Destroy View
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private companion object {
        /**
         * Delay used to synchronize animation and navigation.
         */
        private const val UI_TIMER = 250L
    }

}