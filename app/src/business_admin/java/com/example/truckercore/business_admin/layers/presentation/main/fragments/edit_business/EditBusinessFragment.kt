package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model.EditBusinessEffect
import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model.EditBusinessState
import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model.EditBusinessView
import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model.EditBusinessViewModel
import com.example.truckercore.core.my_lib.expressions.addCnpjMask
import com.example.truckercore.core.my_lib.expressions.addDateMask
import com.example.truckercore.core.my_lib.expressions.collectEffect
import com.example.truckercore.core.my_lib.expressions.collectState
import com.example.truckercore.databinding.FragmentEditBusinessBinding
import com.example.truckercore.layers.presentation.base.abstractions.view.private.PrivateFragment
import com.example.truckercore.layers.presentation.base.managers.SaveMenuManager
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
        setEffectManager()
    }

    private fun setStateManager() {
        collectState(viewModel.stateFlow) { state ->
            when (state) {
                EditBusinessState.Failure -> {
                    navigateToErrorActivity(requireActivity())
                }

                EditBusinessState.Loading -> {
                    enableShimmer(true)
                    enableEditText(false)
                }

                is EditBusinessState.Loaded -> {
                    enableShimmer(false)
                    enableEditText(true)

                    if(state is EditBusinessState.Loaded.Waiting){
                        menuManager.disableMenu()
                    } else {
                        menuManager.enableMenu()
                    }
                }
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

    //--------------------
    private fun setEffectManager() {
        collectEffect(viewModel.effectFlow) { effect ->
            if (effect is EditBusinessEffect.BindData) {
                bindContent(effect.data)
            }
        }
    }

    private fun bindContent(data: EditBusinessView) = binding.run {
        with(data) {
            fragEditBusinessName.setText(name)
            fragEditBusinessCnpj.setText(cnpj)
            fragEditBusinessState.setText(stateReg)
            fragEditBusinessMunicipal.setText(municipalReg)
            fragEditBusinessOpening.setText(opening)
        }
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

    private fun setEditTextListeners() = binding.run {
        // Name
        fragEditBusinessName.addTextChangedListener { text ->
            viewModel.validateName(text.toString())
        }

        // CNpj
        fragEditBusinessCnpj.addTextChangedListener { text ->
            viewModel.validateCnpj(text.toString())
        }

        // State
        fragEditBusinessState.addTextChangedListener { text ->
            viewModel.validateState(text.toString())
        }

        // Municipal
        fragEditBusinessMunicipal.addTextChangedListener { text ->
            viewModel.validateMunicipal(text.toString())
        }

        // Opening
        fragEditBusinessOpening.addTextChangedListener { text ->
            viewModel.validateOpening(text.toString())
        }
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

