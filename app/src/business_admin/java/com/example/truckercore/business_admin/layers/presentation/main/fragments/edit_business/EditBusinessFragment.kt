package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.data.EditBusinessView
import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model.EditBusinessState
import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model.EditBusinessViewModel
import com.example.truckercore.core.my_lib.expressions.addCnpjMask
import com.example.truckercore.core.my_lib.expressions.addDateMask
import com.example.truckercore.core.my_lib.expressions.collectState
import com.example.truckercore.core.my_lib.expressions.popBackstack
import com.example.truckercore.core.my_lib.expressions.showSuccessSnackbar
import com.example.truckercore.databinding.FragmentEditBusinessBinding
import com.example.truckercore.layers.presentation.base.abstractions.view.private.PrivateFragment
import com.example.truckercore.layers.presentation.base.managers.SaveMenuManager
import com.example.truckercore.layers.presentation.common.dialogs.LoadingDialog
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

private typealias LoadingState = EditBusinessState.Loading
private typealias LoadedState = EditBusinessState.Loaded
private typealias FailureState = EditBusinessState.Failure

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

    private var dialog: LoadingDialog? = null

    //----------------------------------------------------------------------------------------------
    // on Create
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInitializing(savedInstanceState, viewModel::fetchCompany)
        collectState(viewModel.stateFlow, ::handleState)
    }

    private fun handleState(state: EditBusinessState) {
        when (state) {
            is LoadingState -> loadingState()
            is LoadedState -> loadedState(state)
            is FailureState -> navigateToErrorActivity(requireActivity())
        }
    }

    private fun loadingState() {
        enableShimmer(true)
        enableEditText(false)
    }

    private fun loadedState(state: LoadedState) {
        handleSaved(state.saved)
        enableShimmer(false)
        enableEditText(true)
        enableMenu(state.ready)
        enableDialog(state.saving)
        bindContent(state.data)
    }

    private fun handleSaved(saved: Boolean) {
        if (saved) {
            showSuccessSnackbar("Salvo com sucesso")
            popBackstack()
        }
    }

    private fun enableDialog(saving: Boolean) {
        if (dialog == null) {
            dialog = LoadingDialog(requireContext())
        }

        if (saving) {
            dialog?.show()
        } else {
            dialog?.dismiss()
        }
    }

    private fun bindContent(data: EditBusinessView) {
        binding.run {
            with(data) {
                setError(fragEditBusinessNameLayout, nameError)
                setError(fragEditBusinessCnpjLayout, cnpjError)
                setError(fragEditBusinessStateLayout, stateRegError)
                setError(fragEditBusinessMunicipalLayout, municipalRegError)
                setError(fragEditBusinessOpeningLayout, openingError)

                // Bind data only if is first bind
                if (viewModel.isViewInit) return

                fragEditBusinessName.setText(name)
                fragEditBusinessCnpj.setText(cnpj)
                fragEditBusinessState.setText(stateReg)
                fragEditBusinessMunicipal.setText(municipalReg)
                fragEditBusinessOpening.setText(opening)
            }
        }

        viewModel.markAsInitialized()
    }

    private fun setError(layout: TextInputLayout, text: String?) {
        layout.error = null
        layout.isErrorEnabled = false

        if (!text.isNullOrEmpty()) {
            layout.error = text
        }

    }

    private fun enableMenu(enabled: Boolean) {
        if (enabled) menuManager.enableMenu()
        else menuManager.disableMenu()
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

    private fun setEditTextListeners() = binding.run {
        // Name
        fragEditBusinessName.addTextChangedListener { text ->
            viewModel.updateName(text.toString())
        }

        // CNpj
        fragEditBusinessCnpj.addTextChangedListener { text ->
            viewModel.updateCnpj(text.toString())
        }

        // State
        fragEditBusinessState.addTextChangedListener { text ->
            viewModel.updateState(text.toString())
        }

        // Municipal
        fragEditBusinessMunicipal.addTextChangedListener { text ->
            viewModel.updateMunicipal(text.toString())
        }

        // Opening
        fragEditBusinessOpening.addTextChangedListener { text ->
            viewModel.updateOpening(text.toString())
        }
    }

    private fun setMenuProvider() {
        requireActivity().addMenuProvider(menuManager.provider(), viewLifecycleOwner)
    }

    fun save() {
        viewModel.saveCompany(
            EditBusinessView(
                name = binding.fragEditBusinessName.text.toString(),
                cnpj = binding.fragEditBusinessCnpj.text.toString(),
                stateReg = binding.fragEditBusinessState.text.toString(),
                municipalReg = binding.fragEditBusinessMunicipal.text.toString(),
                opening = binding.fragEditBusinessOpening.text.toString()
            )
        )
    }

    //----------------------------------------------------------------------------------------------
    // on Destroy View
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        dialog = null
        binding.fragEditBusinessCnpj.removeTextChangedListener(cnpjMask)
        binding.fragEditBusinessOpening.removeTextChangedListener(dateMask)
        _binding = null
    }

}

