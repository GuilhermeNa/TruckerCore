package com.example.truckercore.business_admin.layers.presentation.main.fragments.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.truckercore.databinding.FragmentBusinessBinding
import com.example.truckercore.layers.presentation.base.abstractions.view.private.PrivateFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BusinessFragment : PrivateFragment() {

    // Cleared in onDestroyView to prevent memory leaks.
    private var _binding: FragmentBusinessBinding? = null
    private val binding get() = _binding!!

    // ViewModel providing UI state and interaction control.
    private val viewModel: BusinessViewModel by viewModel()

    //----------------------------------------------------------------------------------------------
    // on Create
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //----------------------------------------------------------------------------------------------
    // on Create View
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentBusinessBinding.inflate(inflater, container, false)
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