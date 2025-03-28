package com.example.truckercore.business_admin.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.truckercore.databinding.FragmentEmailAuthBinding
import com.example.truckercore.view_model.view_models.email_auth.EmailAuthFragmentViewModel

class EmailAuthFragment : Fragment() {

    // Binding -------------------------------------------------------------------------------------
    private var _binding: FragmentEmailAuthBinding? = null
    private val binding get() = _binding!!

    // ViewModel -----------------------------------------------------------------------------------
    private val viewModel: EmailAuthFragmentViewModel by viewModels()

    //----------------------------------------------------------------------------------------------
    // onCreate()
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    //----------------------------------------------------------------------------------------------
    // onCreateView()
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailAuthBinding.inflate(layoutInflater)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // onViewCreated()
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    //----------------------------------------------------------------------------------------------
    // onViewDestroyed()
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}