package com.example.truckercore.business_admin.layers.presentation.main.fragments.employees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.truckercore.R
import com.example.truckercore.business_admin.layers.presentation.main.fragments.employees.view_model.EmployeesState
import com.example.truckercore.business_admin.layers.presentation.main.fragments.employees.view_model.EmployeesViewModel
import com.example.truckercore.core.my_lib.expressions.collectState
import com.example.truckercore.databinding.FragmentBusinessBinding
import com.example.truckercore.databinding.FragmentEmployeeBinding
import com.example.truckercore.databinding.FragmentEmployeesBinding
import com.example.truckercore.layers.presentation.base.abstractions.view.private.PrivateFragment

class EmployeesFragment : PrivateFragment() {

    private var _binding: FragmentEmployeesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EmployeesViewModel by viewModels()

    //----------------------------------------------------------------------------------------------
    // on Create
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInitializing(savedInstanceState, viewModel::observeContent)
        collectState(viewModel.stateFlow, ::handleState)
    }

    private fun handleState(state: EmployeesState) {
        TODO("Not yet implemented")
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

    }

    //----------------------------------------------------------------------------------------------
    // on Destroy View
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}