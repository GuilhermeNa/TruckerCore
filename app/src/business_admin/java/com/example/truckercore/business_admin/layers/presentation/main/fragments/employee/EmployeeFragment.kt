package com.example.truckercore.business_admin.layers.presentation.main.fragments.employee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.truckercore.business_admin.layers.presentation.main.fragments.employee.view_model.EmployeeViewModel
import com.example.truckercore.databinding.FragmentEmployeeBinding
import com.example.truckercore.layers.presentation.base.abstractions.view.private.PrivateFragment

class EmployeeFragment : PrivateFragment() {

    private var _binding: FragmentEmployeeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EmployeeViewModel by viewModels()

    //----------------------------------------------------------------------------------------------
    // on Create
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    //----------------------------------------------------------------------------------------------
    // on Create View
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmployeeBinding.inflate(inflater, container, false)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // on Destroy View
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}