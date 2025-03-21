package com.example.truckercore.view.fragments.auth_options

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.truckercore.R
import com.example.truckercore.databinding.FragmentAuthOptionsBinding

class AuthOptionsFragment : Fragment() {

    private var _binding : FragmentAuthOptionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthOptionsBinding.inflate(layoutInflater)
        return binding.root
    }



}