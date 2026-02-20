package com.example.truckercore.business_admin.layers.presentation.main.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore.business_admin.layers.presentation.main.fragments.home.view_model.HomeViewModel
import com.example.truckercore.core.my_lib.expressions.popClickEffect
import com.example.truckercore.databinding.FragmentHomeBinding
import com.example.truckercore.layers.presentation.base.abstractions.view.private.PrivateLockedFragment
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

                    // Bind description text
                    binding.fragHomeBusinessDescription.text = state.businessSpannable
                    binding.fragHomeEmployeesDescription.text = state.employeesSpannable
                    binding.fragHomeFleetDescription.text = state.fleetSpannable
                    binding.fragHomeFineDescription.text = state.fineSpannable

                    // Enable or Disable View interaction
                    state.interactionEnabled.run {
                      //  binding.fragHomeBusinessDescription.isClickable = this
                        binding.fragHomeBusinessDescription.foreground = null

                      //  binding.fragHomeEmployeesDescription.isClickable = this
                        binding.fragHomeEmployeesDescription.foreground = null

                       // binding.fragHomeFleetDescription.isClickable = this
                        binding.fragHomeFleetDescription.foreground = null

                       // binding.fragHomeFineDescription.isClickable = this
                        binding.fragHomeFineDescription.foreground = null
                    }

                }
            }
        }
    }

    private fun interactionEnabled() = viewModel.stateFlow.value.interactionEnabled

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

    private fun setOnClickListeners() {
        // Business Card Listener
        binding.fragHomeBusinessCard.setOnClickListener {
            //if(!interactionEnabled()) return@setOnClickListener

            it.popClickEffect()
            Toast.makeText(requireContext(), "Empresa", Toast.LENGTH_SHORT).show()
            viewModel.setInteraction(enabled = false)
        }

        // Employee Card Listener
        binding.fragHomeEmployeesCard.setOnClickListener {
          //  if(!interactionEnabled()) return@setOnClickListener

            it.popClickEffect()
            Toast.makeText(requireContext(), "Funcionários", Toast.LENGTH_SHORT).show()
            viewModel.setInteraction(enabled = false)
        }

        // Fleet Card Listener
        binding.fragHomeFleetCard.setOnClickListener {
         //   if(!interactionEnabled()) return@setOnClickListener

            it.popClickEffect()
            Toast.makeText(requireContext(), "Frota", Toast.LENGTH_SHORT).show()
            viewModel.setInteraction(enabled = false)
        }

        // Fine Card Listener
        binding.fragHomeFineCard.setOnClickListener {
          //  if(!interactionEnabled()) return@setOnClickListener

            it.popClickEffect()
            Toast.makeText(requireContext(), "Infrações", Toast.LENGTH_SHORT).show()
            viewModel.setInteraction(enabled = false)
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
}