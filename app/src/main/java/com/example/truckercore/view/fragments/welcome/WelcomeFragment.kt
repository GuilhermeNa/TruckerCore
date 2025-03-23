package com.example.truckercore.view.fragments.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.truckercore.databinding.FragmentWelcomeBinding
import com.example.truckercore.view.expressions.getFlavor
import com.example.truckercore.view.expressions.navigateTo
import com.example.truckercore.view_model.states.FragState.Error
import com.example.truckercore.view_model.states.FragState.Initial
import com.example.truckercore.view_model.states.FragState.Loaded
import com.example.truckercore.view_model.welcome_fragment.FabState
import com.example.truckercore.view_model.welcome_fragment.WelcomeFragmentEvent.LeftFabCLicked
import com.example.truckercore.view_model.welcome_fragment.WelcomeFragmentEvent.RightFabClicked
import com.example.truckercore.view_model.welcome_fragment.WelcomeFragmentEvent.TopButtonClicked
import com.example.truckercore.view_model.welcome_fragment.WelcomeFragmentViewModel
import com.example.truckercore.view_model.welcome_fragment.WelcomePagerData
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val FORWARD = +1
private const val BACKWARD = -1

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

    //----------------------------------------------------------------------------------------------
    // On Create
    //----------------------------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setFragmentStateManager()
                setFragmentEventsManager()
            }
        }
    }

    private suspend fun setFragmentStateManager() {
        viewModel.fragmentState.collect { state ->
            when (state) {
                is Initial -> handleInitialState()
                is Loaded -> handleLoadedState(state.data)
                is Error -> handleErrorState()
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
        viewPager.run {
            adapter = pagerAdapter
            registerOnPageChangeCallback(pagerListener)
            setCurrentItem(viewModel.pagerPos, false)
        }
        TabLayoutMediator(binding.fragWelcomeTabLayout, viewPager) { _, _ -> }.attach()
    }

    private fun handleErrorState() {

    }

    private suspend fun setFragmentEventsManager() {
        viewModel.fragmentEvent.collect { event ->
            when (event) {
                LeftFabCLicked -> handleLeftFabClicked()
                RightFabClicked -> handleRightFabClicked()
                TopButtonClicked -> navigateToAuthOptionsFragment()
            }
        }
    }

    private fun handleLeftFabClicked() {
        paginateViewPager(BACKWARD)
    }

    private fun handleRightFabClicked() {
        when (viewModel.rightFabState) {
            FabState.Navigate -> navigateToAuthOptionsFragment()
            FabState.Paginate -> paginateViewPager(FORWARD)
        }
    }

    private fun paginateViewPager(direction: Int) {
        viewPager.setCurrentItem(viewPager.currentItem + direction, true)
    }

    private fun navigateToAuthOptionsFragment() {
        val direction = WelcomeFragmentDirections.actionWelcomeFragmentToAuthOptionsFragment()
        navigateTo(direction)
    }

    /*    private suspend fun setFragmentEventsManager() {
        viewModel.leftFabState.collect { state ->
            val fab = binding.fragWelcomeLeftFab
            when (state) {
                ViewState.Enabled -> fab.slideInBottom()
                else -> fab.slideOutBottom(INVISIBLE)
            }
        }
    }*/

    //----------------------------------------------------------------------------------------------
    // On Create View
    //----------------------------------------------------------------------------------------------

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
        setRightFabListener()
        setLeftFabListener()
        setButtonListener()
    }

    private fun setRightFabListener() {
        binding.fragWelcomeRightFab.setOnClickListener {
            viewModel.setEvent(RightFabClicked)
        }
    }

    private fun setLeftFabListener() {
        binding.fragWelcomeLeftFab.setOnClickListener {
            viewModel.setEvent(LeftFabCLicked)
        }
    }

    private fun setButtonListener() {
        binding.fragWelcomeJumpButton.setOnClickListener {
            viewModel.setEvent(TopButtonClicked)
        }
    }

    //----------------------------------------------------------------------------------------------
    // On Destroy View
    //----------------------------------------------------------------------------------------------

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}