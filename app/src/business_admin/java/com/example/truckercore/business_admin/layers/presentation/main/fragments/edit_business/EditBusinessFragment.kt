package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model.CompanyView
import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model.EditBusinessState
import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model.EditBusinessStatus
import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model.EditBusinessViewModel
import com.example.truckercore.databinding.FragmentEditBusinessBinding
import com.example.truckercore.layers.presentation.base.abstractions.view.private.PrivateFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditBusinessFragment : PrivateFragment() {

    // Cleared in onDestroyView to prevent memory leaks.
    private var _binding: FragmentEditBusinessBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditBusinessViewModel by viewModel()

    //----------------------------------------------------------------------------------------------
    // on Create
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInitializing(savedInstanceState, viewModel::fetchCompany)
        setStateManager()
    }

    private fun setStateManager() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { state ->
                    when (state.status) {
                        EditBusinessStatus.Failure -> {
                            navigateToErrorActivity(requireActivity())
                        }

                        EditBusinessStatus.Loading -> {
                            enableShimmer(true)
                            enableEditText(false)
                        }

                        is EditBusinessStatus.Loaded -> {
                            enableShimmer(false)
                            enableEditText(true)
                            bindContent(state.companyView)
                        }
                    }
                }
            }
        }
    }

    private fun bindContent(companyView: CompanyView) {
        binding.fragEditBusinessName.setText(companyView.name)
        binding.fragEditBusinessCnpj.setText(companyView.cnpj)
        binding.fragEditBusinessState.setText(companyView.stateReg)
        binding.fragEditBusinessMunicipal.setText(companyView.municipalReg)
        binding.fragEditBusinessOpening.setText(companyView.opening)
    }

    private fun enableShimmer(enabled: Boolean) {
        val pair: Pair<Boolean, Int> = when (enabled) {
            true -> Pair(true, View.VISIBLE)
            false -> Pair(false, View.GONE)
        }

        binding.fragEditBusinessLayoutShimmer.layoutShimmer.isEnabled = pair.first
        binding.fragEditBusinessLayoutShimmer.layoutShimmer.visibility = pair.second
    }

    private fun enableEditText(enabled: Boolean) {
        val value = when (enabled) {
            true -> View.VISIBLE
            false -> View.GONE
        }

        binding.fragEditBusinessLayoutText.visibility = value
    }

    //----------------------------------------------------------------------------------------------
    // on Create View
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBusinessBinding.inflate(inflater, container, false)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // on View Created
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEditTextListeners()
    }

    private fun setEditTextListeners() {
        // Name
        binding.fragEditBusinessName.doAfterTextChanged { text ->
            viewModel.updateName(text.toString())
        }

        // Cnpj
        binding.fragEditBusinessCnpj.doAfterTextChanged { text ->
            viewModel.updateCnpj(text.toString())
        }

        // State
        binding.fragEditBusinessState.doAfterTextChanged { text ->
            viewModel.updateState(text.toString())
        }

        // Municipal
        binding.fragEditBusinessMunicipal.doAfterTextChanged { text ->
            viewModel.updateMunicipal(text.toString())
        }

        // Opening
        binding.fragEditBusinessOpening.doAfterTextChanged { text ->
            viewModel.updateOpening(text.toString())
        }
    }

    fun save() {
        CompanyView(
            name = binding.fragEditBusinessName.text.toString(),
            cnpj = binding.fragEditBusinessCnpj.text.toString(),
            stateReg = binding.fragEditBusinessState.text.toString(),
            municipalReg = binding.fragEditBusinessMunicipal.text.toString(),
            opening = binding.fragEditBusinessOpening.text.toString()
        )
    }

    //----------------------------------------------------------------------------------------------
    // on Destroy View
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}