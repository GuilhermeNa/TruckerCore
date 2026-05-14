package com.example.truckercore.business_admin.layers.presentation.main.fragments.employees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.truckercore.business_admin.layers.presentation.main.fragments.employees.adapter.EmployeesAdapter
import com.example.truckercore.business_admin.layers.presentation.main.fragments.employees.content.EmployeesView
import com.example.truckercore.business_admin.layers.presentation.main.fragments.employees.content.EmployeesViewItem
import com.example.truckercore.business_admin.layers.presentation.main.fragments.employees.view_model.EmployeesState
import com.example.truckercore.business_admin.layers.presentation.main.fragments.employees.view_model.EmployeesViewModel
import com.example.truckercore.core.my_lib.expressions.collectState
import com.example.truckercore.core.my_lib.expressions.navigateToDirection
import com.example.truckercore.databinding.FragmentEmployeesBinding
import com.example.truckercore.layers.domain.base.contracts.ID
import com.example.truckercore.layers.presentation.base.abstractions.view.private.PrivateFragment
import com.example.truckercore.layers.presentation.common.dialogs.LoadingDialog

class EmployeesFragment : PrivateFragment() {

    private var _binding: FragmentEmployeesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EmployeesViewModel by viewModels()
    private var dialog: LoadingDialog? = null

    private var adapter: EmployeesAdapter? = null

    //----------------------------------------------------------------------------------------------
    // on Create
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInitializing(savedInstanceState, viewModel::observeContent)
        collectState(viewModel.stateFlow, ::handleState)
    }

    private fun handleState(state: EmployeesState) {
        when (state) {
            EmployeesState.Loading -> enableDialog(true)
            is EmployeesState.Content -> showContent(state.data)
            EmployeesState.Failure -> navigateToErrorActivity(requireActivity())
        }
    }

    private fun enableDialog(enabled: Boolean) {
        if (enabled) {
            dialog = LoadingDialog(requireContext())
            dialog?.show()
        } else {
            dialog?.dismiss()
        }
    }

    private fun showContent(data: EmployeesView) {
        enableDialog(false)
        adapter?.update(data.byNameAsc)
    }

    //----------------------------------------------------------------------------------------------
    // on Create View
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmployeesBinding.inflate(inflater, container, false)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // on View Created
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initFabListener()
    }

    private fun initRecycler() {
        adapter = EmployeesAdapter(::adapterListener)

        binding.fragEmployeesRecycler.recyclerLine.apply {
            this.adapter = adapter
            clipToPadding = false
            setPadding(0, 0, 0, 80)
        }

    }

    private fun adapterListener(item: EmployeesViewItem) {
        navigateToDirection()
    }

    private fun initFabListener() {
        binding.fragEmployeesFab.setOnClickListener {

            navigateToDirection()
        }
    }

    //----------------------------------------------------------------------------------------------
    // on Destroy View
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        dialog = null
        _binding = null
    }

}