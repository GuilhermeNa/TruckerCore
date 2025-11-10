package com.example.truckercore.layers.presentation.nav_login.fragments.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.truckercore.core.my_lib.enums.Direction
import com.example.truckercore.core.my_lib.expressions.launchOnFragmentLifecycle
import com.example.truckercore.core.my_lib.expressions.navigateToDirection
import com.example.truckercore.core.my_lib.ui_components.Fab
import com.example.truckercore.databinding.FragmentWelcomeBinding
import com.example.truckercore.layers.presentation.base.abstractions._public.PublicLockedFragment
import com.example.truckercore.layers.presentation.nav_login.fragments.welcome.view_pager.WelcomePagerAdapter
import com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.WelcomePagerData
import com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.WelcomeViewModel
import com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.effect.WelcomeFragmentEffect
import com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.event.WelcomeFragmentEvent
import com.example.truckercore.presentation.nav_login.fragments.welcome.WelcomeFragmentDirections
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private val authFragmentDirection =
    WelcomeFragmentDirections.actionWelcomeFragmentToEmailAuthFragment()

private typealias JumpButtonClicked = WelcomeFragmentEvent.Click.JumpButton
private typealias LeftFabClicked = WelcomeFragmentEvent.Click.LeftFab
private typealias RightFabClicked = WelcomeFragmentEvent.Click.RightFab

/**
 * WelcomeFragment is a Fragment that represents the welcome screen of the application.
 * It handles displaying a ViewPager with different welcome pages, managing FAB buttons,
 * and interacting with the ViewModel to manage UI states and events.
 */
class WelcomeFragment : PublicLockedFragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WelcomeViewModel by viewModel()
    private val stateHandler = WelcomeFragmentUiStateHandlerII()

    private var viewPager: ViewPager2? = null
    private var pagerAdapter: WelcomePagerAdapter? = null
    private val pagerListener = onPageChangeCallback()

    //----------------------------------------------------------------------------------------------
    // Init()
    //----------------------------------------------------------------------------------------------
    private fun onPageChangeCallback(): ViewPager2.OnPageChangeCallback {
        return object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.onEvent(WelcomeFragmentEvent.PagerChanged(position))
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // onCreate()
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchOnFragmentLifecycle {
            setFragmentStateManager(savedInstanceState)
            setFragmentEffectManager()
        }
    }

    /**
     * Sets up the fragment's state manager by collecting fragment state changes.
     */
    private fun CoroutineScope.setFragmentStateManager(sis: Bundle?) {
        this.launch {
            viewModel.stateFlow.collect { state ->
                handleViewPager(state.data)
                handleFab(sis, state.fabState)
            }
        }
    }

    /**
     * Configures the ViewPager with data and sets up the pager listener.
     */
    private fun handleViewPager(data: List<WelcomePagerData>) {
        if (viewPager == null) {
            pagerAdapter = WelcomePagerAdapter(data, this)
            viewPager = binding.fragWelcomePager
            viewPager?.run {
                adapter = pagerAdapter
                registerOnPageChangeCallback(pagerListener)
                // Get actual pager position. If is creating pager the pos will be 0 by default.
                // If is recreating the view, it will get last saved position
                setCurrentItem(viewModel.currentPagerPosition(), false)
                TabLayoutMediator(binding.fragWelcomeTabLayout, this) { _, _ -> }.attach()
            }
        }
    }

    private fun handleFab(savedInstanceState: Bundle?, fabComponent: Fab) {
        if (!fabComponent.isVisible()) stateHandler.animateFabOut()
        else onFragmentUiState(
            savedInstanceState = savedInstanceState,
            recreating = stateHandler::animateFabIn,
            resumed = stateHandler::showFab
        )
    }

    private suspend fun setFragmentEffectManager() {
        viewModel.effectFlow.collect { effect ->
            when (effect) {
                WelcomeFragmentEffect.Navigation.ToEmailAuth ->
                    navigateToAuthFragment()

                WelcomeFragmentEffect.Navigation.ToNotification ->
                    navigateToErrorActivity(requireActivity())

                WelcomeFragmentEffect.Pagination.Back -> paginateViewPager(Direction.Back)

                WelcomeFragmentEffect.Pagination.Forward -> paginateViewPager(Direction.Forward)
            }
        }
    }

    /**
     * Changes the ViewPager's current item based on the direction (forward or back).
     */
    private fun paginateViewPager(direction: Direction) {
        viewPager?.apply {
            setCurrentItem(currentItem + direction.value, true)
        }
    }

    //----------------------------------------------------------------------------------------------
    // onCreateView()
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(layoutInflater)
        stateHandler.initialize(binding)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // onViewCreated()
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTopButtonListener()
        setLeftFabListener()
        setRightFabListener()
    }

    /**
     * Sets up the listener for the jump button click event.
     */
    private fun setTopButtonListener() {
        binding.fragWelcomeJumpButton.setOnClickListener {
            viewModel.onEvent(JumpButtonClicked)
        }
    }

    /**
     * Sets up the listener for the left FAB click event.
     */
    private fun setLeftFabListener() {
        binding.fragWelcomeLeftFab.setOnClickListener {
            viewModel.onEvent(LeftFabClicked)
        }
    }

    /**
     * Sets up the listener for the right FAB click event.
     */
    private fun setRightFabListener() {
        binding.fragWelcomeRightFab.setOnClickListener {
            viewModel.onEvent(RightFabClicked)
        }
    }

    private fun navigateToAuthFragment() {
        viewModel.markFirstAccessCompleteOnPreferences()
        navigateToDirection(authFragmentDirection)
    }

    //----------------------------------------------------------------------------------------------
    // onDestroyView()
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        removeViewPagerReference()
        _binding = null
    }

    /**
     * Removes the ViewPager reference to avoid memory leaks.
     */
    private fun removeViewPagerReference() {
        viewPager?.unregisterOnPageChangeCallback(pagerListener)
        pagerAdapter = null
        viewPager = null
    }

}