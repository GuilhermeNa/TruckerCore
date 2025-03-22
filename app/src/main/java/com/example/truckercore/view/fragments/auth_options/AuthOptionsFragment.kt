package com.example.truckercore.view.fragments.auth_options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.truckercore.R
import com.example.truckercore.databinding.FragmentAuthOptionsBinding
import com.example.truckercore.view.expressions.loadGif

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragAuthOptionsImage.loadGif(R.drawable.gif_locked, requireContext())

        binding.fragAuthOptionsButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}