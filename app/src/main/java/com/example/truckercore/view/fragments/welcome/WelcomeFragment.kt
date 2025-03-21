package com.example.truckercore.view.fragments.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.truckercore.databinding.FragmentWelcomeBinding
import com.example.truckercore.view.expressions.collectOnStarted
import com.example.truckercore.view.expressions.getFlavor
import com.example.truckercore.view_model.states.FragState.Error
import com.example.truckercore.view_model.states.FragState.Initial
import com.example.truckercore.view_model.states.FragState.Loaded
import com.example.truckercore.view_model.welcome_fragment.WelcomeFragmentViewModel
import com.example.truckercore.view_model.welcome_fragment.WelcomePagerData
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WelcomeFragmentViewModel by viewModel()
    private lateinit var pagerAdapter: WelcomePagerAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(layoutInflater)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // On View Created
    //----------------------------------------------------------------------------------------------

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectOnStarted(viewModel.fragmentState) { state ->
            when (state) {
                is Initial -> runViewModel()
                is Loaded -> showView(state.data)
                is Error -> navigateToNotificationActivity()
            }
        }
        setRightFabListener()
        setLeftFabListener()
        setButtonListener()
    }

    private fun setRightFabListener() {

    }

    private fun setLeftFabListener() {

    }

    private fun setButtonListener() {

    }

    private fun runViewModel() {
        val flavor = requireContext().getFlavor()
        viewModel.run(flavor)
    }

    private fun showView(data: List<WelcomePagerData>) {
        pagerAdapter = WelcomePagerAdapter(data, this)
        viewPager = binding.fragWelcomePager
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.fragWelcomeTabLayout, viewPager) { tab, position ->

        }.attach()
    }

    private fun navigateToNotificationActivity() {
        TODO("Not yet implemented")
    }

    //----------------------------------------------------------------------------------------------
    // On Destroy View
    //----------------------------------------------------------------------------------------------

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}