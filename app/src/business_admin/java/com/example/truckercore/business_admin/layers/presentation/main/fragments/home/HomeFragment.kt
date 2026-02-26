package com.example.truckercore.business_admin.layers.presentation.main.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import com.example.truckercore.business_admin.layers.presentation.main.fragments.home.view_model.HomeState
import com.example.truckercore.business_admin.layers.presentation.main.fragments.home.view_model.HomeViewModel
import com.example.truckercore.core.my_lib.expressions.navigateToDirection
import com.example.truckercore.core.my_lib.expressions.popClickEffect
import com.example.truckercore.databinding.FragmentHomeBinding
import com.example.truckercore.layers.presentation.base.abstractions.view.private.PrivateLockedFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : PrivateLockedFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    private var fragRes = HomeResource()

    //----------------------------------------------------------------------------------------------
    // On Create
    //----------------------------------------------------------------------------------------------
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

    private fun bindCardText(state: HomeState) {
        binding.fragHomeBusinessDescription.text = state.businessSpannable
        binding.fragHomeEmployeesDescription.text = state.employeesSpannable
        binding.fragHomeFleetDescription.text = state.fleetSpannable
        binding.fragHomeFineDescription.text = state.fineSpannable
    }

    private suspend fun handleCardEnabled(interactionEnabled: Boolean) {
        val color = fragRes.get(interactionEnabled)

        if(!interactionEnabled) delay(UI_TIMER)

        binding.fragHomeBusinessCard.rippleColor = color
        binding.fragHomeEmployeesCard.rippleColor = color
        binding.fragHomeFleetCard.rippleColor = color
        binding.fragHomeFineCard.rippleColor = color
    }

    //----------------------------------------------------------------------------------------------
    // On Create View
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // On View Create
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragmentResource()
        setOnClickListeners()
    }

    private fun initFragmentResource() {
        if (fragRes.isInitialized()) return

        fragRes.setBaseRipple(binding.fragHomeBusinessCard.rippleColor)
    }

    private fun setOnClickListeners() {
        // Business Card Listener
        binding.fragHomeBusinessCard.setOnClickListener {
            if (!interactionEnabled()) return@setOnClickListener
            else {
                it.popAndDisableInteraction()
                val direction = HomeFragmentDirections.actionNavHomeToBusinessFragment()
                navigateAfterDelay(direction)
            }
        }

        // Employee Card Listener
        binding.fragHomeEmployeesCard.setOnClickListener {
            if (!interactionEnabled()) return@setOnClickListener
            else {
                it.popAndDisableInteraction()
                val direction = HomeFragmentDirections.actionNavHomeToEmployeeFragment()
                navigateAfterDelay(direction)
            }
        }

        // Fleet Card Listener
        binding.fragHomeFleetCard.setOnClickListener {
            if (!interactionEnabled()) return@setOnClickListener
            else {
                it.popAndDisableInteraction()
                val direction = HomeFragmentDirections.actionNavHomeToFleetFragment()
                navigateAfterDelay(direction)
            }
        }

        // Fine Card Listener
        binding.fragHomeFineCard.setOnClickListener {
            if (!interactionEnabled()) return@setOnClickListener
            else {
                it.popAndDisableInteraction()
                val direction = HomeFragmentDirections.actionNavHomeToFineFragment()
                navigateAfterDelay(direction)
            }
        }
    }

    private fun interactionEnabled() = viewModel.stateFlow.value.interactionEnabled

    private fun View.popAndDisableInteraction() {
        this.popClickEffect()
        viewModel.setInteraction(enabled = false)
    }

    private fun navigateAfterDelay(direction: NavDirections) {
        lifecycleScope.launch {
            delay(UI_TIMER)
            navigateToDirection(direction)
        }
    }

    //----------------------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------------------
    override fun onResume() {
        super.onResume()
        viewModel.setInteraction(enabled = true)
    }

    //----------------------------------------------------------------------------------------------
    // On Destroy View
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private companion object {
        private const val UI_TIMER = 250L
    }

}