package com.example.truckercore.business_admin.layers.presentation.main.fragments.fine

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.truckercore.R

class FineFragment : Fragment() {

    companion object {
        fun newInstance() = FineFragment()
    }

    private val viewModel: FineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_fine, container, false)
    }
}