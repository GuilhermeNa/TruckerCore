package com.example.truckercore.business_admin.layers.presentation.main.fragments.fleet

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.truckercore.R

class FleetFragment : Fragment() {

    companion object {
        fun newInstance() = FleetFragment()
    }

    private val viewModel: FleetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_fleet, container, false)
    }
}