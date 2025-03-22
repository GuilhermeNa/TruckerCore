package com.example.truckercore.view.fragments.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.truckercore.databinding.FragmentWelcomeBinding
import com.example.truckercore.view.expressions.collectOnStarted
import com.example.truckercore.view.expressions.getFlavor
import com.example.truckercore.view.expressions.navigateTo
import com.example.truckercore.view.expressions.slideInBottom
import com.example.truckercore.view.expressions.slideOutBottom
import com.example.truckercore.view_model.states.FragState.Error
import com.example.truckercore.view_model.states.FragState.Initial
import com.example.truckercore.view_model.states.FragState.Loaded
import com.example.truckercore.view_model.welcome_fragment.FabState
import com.example.truckercore.view_model.welcome_fragment.ViewState
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
    private val pagerListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            viewModel.notifyPagerChanged(position)
        }
    }

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
        setFragmentStateManager()
        setRightFabManager()
        setLeftFabManager()
        setButtonManager()
    }

    private fun setFragmentStateManager() {
        collectOnStarted(viewModel.fragmentState) { state ->
            when (state) {
                is Initial -> handleInitialState()
                is Loaded -> handleLoadedState(state.data)
                is Error -> navigateToNotificationActivity()
            }
        }
    }

    private fun handleInitialState() {
        val flavor = requireContext().getFlavor()
        viewModel.run(flavor)
    }

    private fun handleLoadedState(data: List<WelcomePagerData>) {
        pagerAdapter = WelcomePagerAdapter(data, this)
        viewPager = binding.fragWelcomePager
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.fragWelcomeTabLayout, viewPager) { _, _ -> }.attach()
        viewPager.registerOnPageChangeCallback(pagerListener)
    }

    private fun navigateToNotificationActivity() {

    }

    private fun setLeftFabManager(): Unit = with(binding.fragWelcomeLeftFab) {
        collectOnStarted(viewModel.leftFabState) { state ->
            when (state) {
                ViewState.Enabled -> slideInBottom()
                else -> slideOutBottom(INVISIBLE)
            }
        }

        setOnClickListener {
            viewPager.setCurrentItem(viewPager.currentItem - 1, true)
        }

    }

    private fun setRightFabManager() {
        binding.fragWelcomeRightFab.setOnClickListener {
            when (viewModel.rightFabState) {
                FabState.Navigate -> navigateToAuthOptionsFragment()
                FabState.Paginate -> viewPager.setCurrentItem(viewPager.currentItem + 1, true)
            }
        }
    }

    private fun setButtonManager() {
        binding.fragWelcomeJumpButton.setOnClickListener {
            navigateToAuthOptionsFragment()
        }
    }

    private fun navigateToAuthOptionsFragment() {
        val direction = WelcomeFragmentDirections.actionWelcomeFragmentToAuthOptionsFragment()
        navigateTo(direction)
    }

    //----------------------------------------------------------------------------------------------
    // On Destroy View
    //----------------------------------------------------------------------------------------------

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}