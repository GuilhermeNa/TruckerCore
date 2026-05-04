package com.example.truckercore.business_admin.layers.presentation.main.fragments.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.truckercore.business_admin.layers.presentation.main.fragments.business.content.BusinessResources
import com.example.truckercore.business_admin.layers.presentation.main.fragments.business.content.BusinessView
import com.example.truckercore.business_admin.layers.presentation.main.fragments.business.view_model.BusinessState
import com.example.truckercore.business_admin.layers.presentation.main.fragments.business.view_model.BusinessViewModel
import com.example.truckercore.core.my_lib.expressions.collectState
import com.example.truckercore.core.my_lib.expressions.navigateToDirection
import com.example.truckercore.databinding.FragmentBusinessBinding
import com.example.truckercore.layers.presentation.base.abstractions.view.private.PrivateFragment
import com.example.truckercore.layers.presentation.common.lists.recycler_grid.RecyclerGrid
import org.koin.androidx.viewmodel.ext.android.viewModel

private typealias LoadingState = BusinessState.Loading
private typealias ContentState = BusinessState.Content
private typealias FailureState = BusinessState.Failure

class BusinessFragment : PrivateFragment() {

    // Cleared in onDestroyView to prevent memory leaks.
    private var _binding: FragmentBusinessBinding? = null
    private val binding get() = _binding!!

    // ViewModel providing UI state and interaction control.
    private val viewModel: BusinessViewModel by viewModel()

    private val res: BusinessResources by lazy { BusinessResources() }

    //----------------------------------------------------------------------------------------------
    // on Create
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInitializing(savedInstanceState, viewModel::observeContent)
        collectState(viewModel.stateFlow, ::handleState)
    }

    private fun handleState(state: BusinessState) {
        when (state) {
            is LoadingState -> loadingState()
            is ContentState -> contentState(state.data)
            is FailureState -> failureState()
        }
    }

    private fun loadingState() {
        shimmerEnabled(true)
        notificationEnabled(false)
        fabEnabled(false)
        mainEnabled(false)
    }

    private fun failureState() {
        navigateToErrorActivity(requireActivity())
    }

    private fun contentState(data: BusinessView) {
        shimmerEnabled(false)
        mainEnabled(true)
        notificationEnabled(data.notificationEnabled)
        fabEnabled(data.fabEnabled)
        initRecycler()
        bindData(data)
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

    private fun notificationEnabled(enabled: Boolean) = with(binding) {
        if (enabled) {
            fragBusinessLayoutIncomplete.visibility = View.VISIBLE
            fragBusinessNotification.notificationTextView.text = res.notification
        } else {
            fragBusinessLayoutIncomplete.visibility = View.GONE
        }
    }

    private fun fabEnabled(enabled: Boolean) = with(binding) {
        if (enabled) {
            fragBusinessFab.visibility = View.VISIBLE
        } else {
            fragBusinessFab.visibility = View.INVISIBLE
        }
    }

    private fun mainEnabled(enable: Boolean) {
        if (enable) {
            binding.fragBusinessMain.visibility = View.VISIBLE
        } else {
            binding.fragBusinessMain.visibility = View.INVISIBLE
        }
    }

    private fun bindData(data: BusinessView) = with(data) {
        binding.fragBusinessName.text = name
        binding.fragBusinessCnpj.text = cnpj
        binding.fragBusinessOpening.text = opening
        binding.fragBusinessState.text = inscState
        binding.fragBusinessMunicipal.text = inscMunicipal
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
    // on View Created
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEditBusinessButtonListener()
        setFabClickListener()
    }

    private fun setEditBusinessButtonListener() {
        binding.fragBusinessButton.setOnClickListener {
            navigateToEditBusiness()
        }
    }

    private fun setFabClickListener() {
        binding.fragBusinessFab.setOnClickListener {
            navigateToEditBusiness()
        }
    }

    private fun navigateToEditBusiness() {
        val direction = BusinessFragmentDirections.actionNavBusinessToEditBusinessFragment()
        navigateToDirection(direction)
    }

    //----------------------------------------------------------------------------------------------
    // on Destroy View
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}