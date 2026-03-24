package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model.EditBusinessState
import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model.EditBusinessViewModel
import com.example.truckercore.databinding.FragmentEditBusinessBinding
import com.example.truckercore.layers.presentation.base.abstractions.view.private.PrivateFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditBusinessFragment : PrivateFragment() {

    // Cleared in onDestroyView to prevent memory leaks.
    private var _binding: FragmentEditBusinessBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditBusinessViewModel by viewModel()

    //----------------------------------------------------------------------------------------------
    // on Create
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { state ->
                    when (state) {
                        EditBusinessState.Failure -> {
                            navigateToErrorActivity(requireActivity())
                        }

                        EditBusinessState.Loading -> {
                            enableShimmer(true)
                            enableEditText(false)
                        }

                        is EditBusinessState.Loaded -> {
                            enableShimmer(false)
                            enableEditText(true)
                        }
                    }
                }
            }
        }
    }

    private fun enableShimmer(enabled: Boolean) {
        val pair: Pair<Boolean, Int> = when (enabled) {
            true -> Pair(true, View.VISIBLE)
            false -> Pair(false, View.GONE)
        }

        binding.fragEditBusinessLayoutShimmer.layoutShimmer.isEnabled = pair.first
        binding.fragEditBusinessLayoutShimmer.layoutShimmer.visibility = pair.second
    }

    private fun enableEditText(enabled: Boolean) {
        val value = when(enabled) {
            true -> View.VISIBLE
            false -> View.GONE
        }

        binding.fragEditBusinessLayoutText.visibility = value
    }

    //----------------------------------------------------------------------------------------------
    // on Create View
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBusinessBinding.inflate(inflater, container, false)
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