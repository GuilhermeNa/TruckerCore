package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model.EditBusinessStatus
import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model.EditBusinessView
import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model.EditBusinessViewModel
import com.example.truckercore.core.my_lib.expressions.addCnpjMask
import com.example.truckercore.core.my_lib.expressions.addDateMask
import com.example.truckercore.core.my_lib.expressions.onTextChange
import com.example.truckercore.databinding.FragmentEditBusinessBinding
import com.example.truckercore.layers.presentation.base.abstractions.view.private.PrivateFragment
import com.example.truckercore.layers.presentation.base.managers.SaveMenuManager
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditBusinessFragment : PrivateFragment() {

    // Cleared in onDestroyView to prevent memory leaks.
    private var _binding: FragmentEditBusinessBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditBusinessViewModel by viewModel()

    private var cnpjMask: TextWatcher? = null
    private var dateMask: TextWatcher? = null

    private val menuManager: SaveMenuManager by lazy {
        SaveMenuManager(
            invalidate = requireActivity()::invalidateOptionsMenu,
            onSave = ::save
        )
    }

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

    private fun bindContent(editBusinessView: EditBusinessView) {
        with(binding) {
            editBusinessView.run {
                updateEditText(
                    Pair(fragEditBusinessName, name),
                    Pair(fragEditBusinessCnpj, cnpj),
                    Pair(fragEditBusinessState, stateReg),
                    Pair(fragEditBusinessMunicipal, municipalReg),
                    Pair(fragEditBusinessOpening, opening)
                )
            }
        }
    }

    private fun updateEditText(vararg pairArr: Pair<TextInputEditText, String>) {
        pairArr.forEach { pair ->
            val editText = pair.first
            val content = pair.second

            val current = editText.text.toString()
            if (current != content) {
                editText.setText(content)
            }
        }
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
        setEditTextMasks()
        setEditTextListeners()
        setMenuProvider()
    }

    private fun setEditTextMasks() = with(binding) {
        cnpjMask = fragEditBusinessCnpj.addCnpjMask()
        dateMask = fragEditBusinessOpening.addDateMask()
    }

    private fun setEditTextListeners() = with(binding) {
        fragEditBusinessName.onTextChange(lifecycleScope, viewModel::updateName)
        fragEditBusinessCnpj.onTextChange(lifecycleScope, viewModel::updateCnpj)
        fragEditBusinessState.onTextChange(lifecycleScope, viewModel::updateState)
        fragEditBusinessMunicipal.onTextChange(lifecycleScope, viewModel::updateMunicipal)
        fragEditBusinessOpening.onTextChange(lifecycleScope, viewModel::updateOpening)
    }

    private fun setMenuProvider() {
        requireActivity().addMenuProvider(menuManager.provider(), viewLifecycleOwner)
    }

    fun save() {
        EditBusinessView(
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
        binding.fragEditBusinessCnpj.removeTextChangedListener(cnpjMask)
        binding.fragEditBusinessOpening.removeTextChangedListener(dateMask)
        _binding = null
    }

}