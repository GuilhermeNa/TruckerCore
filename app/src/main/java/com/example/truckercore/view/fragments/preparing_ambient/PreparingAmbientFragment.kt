package com.example.truckercore.view.fragments.preparing_ambient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.truckercore.databinding.FragmentPreparingAmbientBinding

/**
 * A simple [Fragment] subclass.
 * Use the [PreparingAmbientFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PreparingAmbientFragment : Fragment() {

    private var _binding: FragmentPreparingAmbientBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreparingAmbientBinding.inflate(layoutInflater)
        return binding.root
    }

}