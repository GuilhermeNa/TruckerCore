package com.example.truckercore.business_admin.layers.presentation.main.fragments.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.truckercore.business_admin.layers.presentation.main.activity.view_model.MainViewModel
import com.example.truckercore.business_admin.layers.presentation.main.fragments.business.view_model.BusinessStatus
import com.example.truckercore.business_admin.layers.presentation.main.fragments.business.view_model.BusinessViewModel
import com.example.truckercore.databinding.FragmentBusinessBinding
import com.example.truckercore.layers.presentation.base.abstractions.view.private.PrivateFragment
import com.example.truckercore.layers.presentation.common.lists.recycler_grid.RecyclerGrid
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BusinessFragment : PrivateFragment() {

    // Cleared in onDestroyView to prevent memory leaks.
    private var _binding: FragmentBusinessBinding? = null
    private val binding get() = _binding!!

    // ViewModel providing UI state and interaction control.
    private val viewModel: BusinessViewModel by viewModel()
    private val actViewModel: MainViewModel by activityViewModel()

    private val res: BusinessResources by lazy { BusinessResources() }

    //----------------------------------------------------------------------------------------------
    // on Create
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityStateManager()
        setStateManager()
    }

    private fun setActivityStateManager() {
        // onInitializing() { }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                actViewModel.sessionFLow.collect {

                }
            }
        }
    }

    private fun setStateManager() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { state ->
                    when (state.status) {
                        BusinessStatus.Loading -> setLoadingStatus()
                        BusinessStatus.Incomplete -> setIncompleteStatus()
                        BusinessStatus.Complete -> setCompleteStatus()
                    }
                }
            }
        }
    }

    private fun setLoadingStatus() {
        shimmerEnabled(true)
        incompleteEnabled(false)
        mainEnabled(false)
    }

    private fun setIncompleteStatus() {
        shimmerEnabled(false)
        incompleteEnabled(true)
        mainEnabled(false)
    }

    private fun setCompleteStatus() {
        shimmerEnabled(false)
        incompleteEnabled(false)
        mainEnabled(true)
    }

    private fun shimmerEnabled(enable: Boolean) {
        if (enable) {
            binding.fragBusinessLayoutShimmer.startShimmer()
            binding.fragBusinessLayoutShimmer.visibility = View.VISIBLE
        } else {
            binding.fragBusinessLayoutShimmer.stopShimmer()
            binding.fragBusinessLayoutShimmer.visibility = View.GONE
        }
    }

    private fun incompleteEnabled(enable: Boolean) {
        if (enable) {
            binding.fragBusinessLayoutIncomplete.visibility = View.VISIBLE

            binding.fragBusinessNotification.notificationTextView.text =
                viewModel.notification

        } else {
            binding.fragBusinessLayoutIncomplete.visibility = View.GONE
        }
    }

    private fun mainEnabled(enable: Boolean) {
        if (enable) {
            binding.fragBusinessMain.visibility = View.VISIBLE
            initRecycler()
            bindData()

        } else {
            binding.fragBusinessMain.visibility = View.INVISIBLE
        }
    }

    private fun bindData() {
        binding.fragBusinessName.text = viewModel.getName()
        binding.fragBusinessCnpj.text = viewModel.getCnpj()
        binding.fragBusinessOpening.text = viewModel.getOpening()
        binding.fragBusinessMunicipal.text = viewModel.getMunicipalInsc()
        binding.fragBusinessState.text = viewModel.getStateInsc()
    }

    private fun initRecycler() {
        val dataSet = res.dataSet
        val adapter = RecyclerGrid(dataSet)
        val view = binding.fragBusinessRecycler
        view.recyclerGrid.adapter = adapter
        view.recyclerGrid.clipToPadding = false
        view.recyclerGrid.setPadding(0, 0, 0, 80)
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