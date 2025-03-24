package com.example.truckercore.view.fragments.phone_auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.truckercore.databinding.FragmentPhoneAuthBinding
import com.example.truckercore.view_model.view_models.phone_auth.PhoneAuthFragmentViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [PhoneAuthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhoneAuthFragment : Fragment() {

    // Binding -------------------------------------------------------------------------------------
    private var _binding: FragmentPhoneAuthBinding? = null
    private val binding get() = _binding!!

    // ViewModel -----------------------------------------------------------------------------------
    private val viewModel: PhoneAuthFragmentViewModel by viewModels()

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
        _binding = FragmentPhoneAuthBinding.inflate(layoutInflater)
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