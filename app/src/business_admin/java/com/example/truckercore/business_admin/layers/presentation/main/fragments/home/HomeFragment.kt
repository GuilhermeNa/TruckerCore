package com.example.truckercore.business_admin.layers.presentation.main.fragments.home

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore.business_admin.layers.presentation.main.fragments.home.view_model.HomeState
import com.example.truckercore.business_admin.layers.presentation.main.fragments.home.view_model.HomeViewModel
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

    //----------------------------------------------------------------------------------------------
    // On Create
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStateManager()
    }

    private fun setStateManager() {
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


    // TODO(transferir interação do card para dentro dos listeners)

    private suspend fun handleCardEnabled(interactionEnabled: Boolean) {
        val rippleColor = getRippleColor(interactionEnabled)

        if (!interactionEnabled) delay(1000)

        binding.fragHomeBusinessCard.rippleColor = rippleColor
        binding.fragHomeEmployeesCard.rippleColor = rippleColor
        binding.fragHomeFleetCard.rippleColor = rippleColor
        binding.fragHomeFineCard.rippleColor = rippleColor
    }

    private fun getRippleColor(interactionEnabled: Boolean) =
        if (interactionEnabled) binding.fragHomeBusinessCard.rippleColor
        else ColorStateList.valueOf(Color.TRANSPARENT)



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
        setOnClickListeners()
    }

    // TODO(adicionar navegação para novos fragments a partir do click listener)

    private fun setOnClickListeners() {
        // Business Card Listener
        binding.fragHomeBusinessCard.setOnClickListener {
            if (!interactionEnabled()) return@setOnClickListener
            else it.popAndDisableInteraction()
        }

        // Employee Card Listener
        binding.fragHomeEmployeesCard.setOnClickListener {
            if (!interactionEnabled()) return@setOnClickListener
            else it.popAndDisableInteraction()
        }

        // Fleet Card Listener
        binding.fragHomeFleetCard.setOnClickListener {
            if (!interactionEnabled()) return@setOnClickListener
            else it.popAndDisableInteraction()
        }

        // Fine Card Listener
        binding.fragHomeFineCard.setOnClickListener {
            if (!interactionEnabled()) return@setOnClickListener
            else it.popAndDisableInteraction()
        }
    }

    private fun interactionEnabled() = viewModel.stateFlow.value.interactionEnabled

    private fun View.popAndDisableInteraction() {
        popClickEffect()
        viewModel.setInteraction(enabled = false)
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

}