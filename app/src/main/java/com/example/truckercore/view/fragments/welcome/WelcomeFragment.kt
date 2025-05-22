package com.example.truckercore.view.fragments.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.truckercore._utils.classes.ButtonState
import com.example.truckercore._utils.enums.Direction
import com.example.truckercore._utils.expressions.doIfResumed
import com.example.truckercore._utils.expressions.doIfResumedOrElse
import com.example.truckercore._utils.expressions.getClassName
import com.example.truckercore._utils.expressions.logState
import com.example.truckercore._utils.expressions.navigateToActivity
import com.example.truckercore._utils.expressions.navigateToDirection
import com.example.truckercore.databinding.FragmentWelcomeBinding
import com.example.truckercore.model.logger.AppLogger
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore.view.fragments._base.CloseAppFragment
import com.example.truckercore.view.ui_error.UiError
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomeEvent
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomePagerData
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * WelcomeFragment is a Fragment that represents the welcome screen of the application.
 * It handles displaying a ViewPager with different welcome pages, managing FAB buttons,
 * and interacting with the ViewModel to manage UI states and events.
 */
class WelcomeFragment : CloseAppFragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    private var stateHandler: WelcomeUiStateHandler? = null

    private val viewModel: WelcomeViewModel by viewModel()

    private var viewPager: ViewPager2? = null
    private var pagerAdapter: WelcomePagerAdapter? = null
    private val pagerListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            viewModel.onEvent(WelcomeEvent.PagerChanged(position))
        }
    }

    //----------------------------------------------------------------------------------------------
    // onCreate()
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setFragmentStateManager()
            }
        }
    }

    /**
     * Sets up the fragment's state manager by collecting fragment state changes.
     */
    private fun CoroutineScope.setFragmentStateManager() {
        this.launch {
            viewModel.state.collect { state ->
                logState(this@WelcomeFragment, state)

                handleUiErrorIfHas(state.uiError)
                handleViewPager(state.data)
                handleLeftFab(state.fabState)
            }
        }
    }

    private fun handleUiErrorIfHas(uiError: UiError.Critical?) {
        uiError?.let {
            val intent = NotificationActivity.newInstance(
                context = requireContext(),
                title = it.title,
                message = it.message
            )
            navigateToActivity(intent, true)
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
                setCurrentItem(viewModel.pagerPos(), false)
                TabLayoutMediator(binding.fragWelcomeTabLayout, this) { _, _ -> }.attach()
            }
        }
    }

    /**
     * Controls the visibility of the left FAB based on the current fragment stage.
     */
    private fun handleLeftFab(fabState: ButtonState) {
        when (fabState.isEnabled) {
            true -> doIfResumedOrElse(
                resumed = { stateHandler?.animateLeftFabIn() },
                orElse = { stateHandler?.showLeftFab() }
            )

            false -> doIfResumed { stateHandler?.animateLeftFabOut() }
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
        stateHandler = WelcomeUiStateHandler(binding.fragWelcomeLeftFab)
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
            navigateToEmailAuth()
        }
    }

    /**
     * Sets up the listener for the left FAB click event.
     */
    private fun setLeftFabListener() {
        binding.fragWelcomeLeftFab.setOnClickListener {
            paginateViewPager(Direction.Back)
        }
    }

    /**
     * Sets up the listener for the right FAB click event.
     */
    private fun setRightFabListener() {
        binding.fragWelcomeRightFab.setOnClickListener {
            when (viewModel.isLastPage()) {
                true -> navigateToEmailAuth()
                false -> paginateViewPager(Direction.Forward)
            }
        }
    }

    private fun navigateToEmailAuth() {
        //PreferenceDataStore.getInstance().setAppAlreadyAccessed(requireContext())

        val direction = WelcomeFragmentDirections
            .actionWelcomeFragmentToEmailAuthFragment()
        navigateToDirection(direction)
    }

    /**
     * Changes the ViewPager's current item based on the direction (forward or back).
     */
    private fun paginateViewPager(direction: Direction) {
        viewPager?.run {
            setCurrentItem(currentItem + direction.value, true)
        }
    }

    //----------------------------------------------------------------------------------------------
    // onDestroyView()
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        removeViewPagerReference()
        stateHandler = null
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