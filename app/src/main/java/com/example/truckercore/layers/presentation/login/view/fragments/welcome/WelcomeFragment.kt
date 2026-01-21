package com.example.truckercore.layers.presentation.login.view.fragments.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.truckercore.core.my_lib.enums.Direction
import com.example.truckercore.core.my_lib.expressions.applySystemBarsInsets
import com.example.truckercore.core.my_lib.expressions.launchAndRepeatOnFragmentStartedLifeCycle
import com.example.truckercore.core.my_lib.expressions.navigateToDirection
import com.example.truckercore.core.my_lib.ui_components.FabComponent
import com.example.truckercore.databinding.FragmentWelcomeBinding
import com.example.truckercore.layers.presentation.base.abstractions.view._public.PublicLockedFragment
import com.example.truckercore.layers.presentation.login.view.fragments.welcome.view_pager.WelcomePagerAdapter
import com.example.truckercore.layers.presentation.login.view_model.welcome_fragment.WelcomeViewModel
import com.example.truckercore.layers.presentation.login.view_model.welcome_fragment.helpers.WelcomeFragmentEffect
import com.example.truckercore.layers.presentation.login.view_model.welcome_fragment.helpers.WelcomeFragmentEvent
import com.example.truckercore.layers.presentation.login.view_model.welcome_fragment.helpers.WelcomePagerData
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private val authFragmentDirection =
    WelcomeFragmentDirections.actionWelcomeFragmentToEmailAuthFragment()

private typealias SkipButtonClicked = WelcomeFragmentEvent.Click.SkipButton
private typealias LeftFabClicked = WelcomeFragmentEvent.Click.LeftFab
private typealias RightFabClicked = WelcomeFragmentEvent.Click.RightFab

/**
 * Fragment responsible for displaying the welcome/onboarding flow.
 *
 * It connects the UI with the [WelcomeViewModel], listens to user interactions,
 * and reacts to both [WelcomeFragmentState] and [WelcomeFragmentEffect] updates.
 *
 * The screen consists of:
 * - A [ViewPager2] showing multiple welcome pages.
 * - Left and right FABs for manual navigation.
 * - A "Skip" button to bypass onboarding.
 */
class WelcomeFragment : PublicLockedFragment() {

    // View Binding
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    // ViewModel and Helpers
    private val viewModel: WelcomeViewModel by viewModel()
    private val stateHandler = WelcomeFragmentUiStateHandler()

    // View Pager
    private var viewPager: ViewPager2? = null
    private var pagerAdapter: WelcomePagerAdapter? = null
    private val pagerListener = onPageChangeCallback()

    /**
     * View Pager Callback
     */
    private fun onPageChangeCallback(): ViewPager2.OnPageChangeCallback {
        return object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.onEvent(WelcomeFragmentEvent.PagerSwiped(position))
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // Lifecycle - onCreate()
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchAndRepeatOnFragmentStartedLifeCycle {
            setFragmentStateManager(savedInstanceState)
            setFragmentEffectManager()
        }
    }

    /**
     * Observes state updates emitted by the [WelcomeViewModel].
     * When a new [WelcomeFragmentState] is received, the UI components
     * such as the ViewPager and FABs are updated accordingly.
     *
     * @param sis The saved instance state used to detect recreation scenarios.
     */
    private fun CoroutineScope.setFragmentStateManager(sis: Bundle?) {
        launch {
            viewModel.stateFlow.collect { state ->
                handleViewPager(state.data)
                handleFab(sis, state.fab)
            }
        }
    }

    /**
     * Initializes and configures the [ViewPager2] with the given [data],
     * including adapter setup, tab layout binding, and position restoration.
     *
     * @param data The list of pager data items used to populate the ViewPager.
     */
    private fun handleViewPager(data: List<WelcomePagerData>) {
        if (viewPager == null) {
            pagerAdapter = WelcomePagerAdapter(data, this)
            viewPager = binding.fragWelcomePager
            viewPager?.run {
                adapter = pagerAdapter
                registerOnPageChangeCallback(pagerListener)

                // When created, default position is 0. When recreated, restore the previous position.
                setCurrentItem(viewModel.currentPagerPosition(), false)

                TabLayoutMediator(binding.fragWelcomeTabLayout, this) { _, _ -> }.attach()
            }
        }
    }

    /**
     * Handles the FAB animation and visibility state based on the provided [fab] data.
     *
     * @param savedInstanceState The fragment's saved instance state.
     * @param fab The current [FabComponent] representing FAB visibility and state.
     */
    private fun handleFab(savedInstanceState: Bundle?, fab: FabComponent) {
        if (!fab.isVisible()) {
            stateHandler.animateFabOut()
        } else {
            onFragmentUiState(
                instanceState = savedInstanceState,
                resumed = stateHandler::animateFabIn,
                recreating = stateHandler::showFab
            )
        }
    }

    /**
     * Observes one-time [WelcomeFragmentEffect]s emitted by the [WelcomeViewModel]
     * and performs side effects such as navigation or pager animations.
     */
    private suspend fun setFragmentEffectManager() {
        viewModel.effectFlow.collect { effect ->
            when (effect) {
                WelcomeFragmentEffect.Navigation.ToEmailAuth ->
                    navigateToAuthFragment()

                WelcomeFragmentEffect.Navigation.ToNotification ->
                    navigateToErrorActivity(requireActivity())

                WelcomeFragmentEffect.Pagination.Back ->
                    paginateViewPager(Direction.Back)

                WelcomeFragmentEffect.Pagination.Forward ->
                    paginateViewPager(Direction.Forward)
            }
        }
    }

    /**
     * Moves the ViewPager one page forward or backward depending on the provided [direction].
     *
     * @param direction Indicates whether to move forward or backward in the pager.
     */
    private fun paginateViewPager(direction: Direction) {
        viewPager?.apply {
            setCurrentItem(currentItem + direction.value, true)
        }
    }

    //----------------------------------------------------------------------------------------------
    // Lifecycle - onCreateView()
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(layoutInflater)
        stateHandler.initialize(binding)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // Lifecycle - onViewCreated()
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTopButtonListener()
        setLeftFabListener()
        setRightFabListener()
        view.applySystemBarsInsets()
    }

    /**
     * Sets up the listener for the "Skip" button click event.
     * When clicked, it triggers the [SkipButtonClicked] event.
     */
    private fun setTopButtonListener() {
        binding.fragWelcomeJumpButton.setOnClickListener {
            viewModel.onEvent(SkipButtonClicked)
        }
    }

    /**
     * Sets up the listener for the left FAB click event.
     * When clicked, it triggers the [LeftFabClicked] event.
     */
    private fun setLeftFabListener() {
        binding.fragWelcomeLeftFab.setOnClickListener {
            viewModel.onEvent(LeftFabClicked)
        }
    }

    /**
     * Sets up the listener for the right FAB click event.
     * When clicked, it triggers the [RightFabClicked] event.
     */
    private fun setRightFabListener() {
        binding.fragWelcomeRightFab.setOnClickListener {
            viewModel.onEvent(RightFabClicked)
        }
    }

    /**
     * Navigates to the authentication fragment, marking the first access as complete
     * before performing the navigation action.
     */
    private fun navigateToAuthFragment() {
        viewModel.markFirstAccessCompleteOnPreferences()
        navigateToDirection(authFragmentDirection)
    }

    //----------------------------------------------------------------------------------------------
    // Lifecycle - onDestroyView()
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        removeViewPagerReference()
        _binding = null
    }

    /**
     * Clears references to the ViewPager and its adapter to avoid memory leaks.
     */
    private fun removeViewPagerReference() {
        viewPager?.unregisterOnPageChangeCallback(pagerListener)
        pagerAdapter = null
        viewPager = null
    }

}