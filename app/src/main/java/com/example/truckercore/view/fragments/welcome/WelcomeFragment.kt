package com.example.truckercore.view.fragments.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.truckercore.R
import com.example.truckercore._utils.enums.Direction
import com.example.truckercore.databinding.FragmentWelcomeBinding
import com.example.truckercore.view.activities.NotificationActivity
import com.example.truckercore._utils.expressions.navigateToDirection
import com.example.truckercore._utils.expressions.slideInBottom
import com.example.truckercore._utils.expressions.slideOutBottom
import com.example.truckercore.view.fragments._base.CloseAppFragment
import com.example.truckercore.view_model.states.WelcomeFragState.Error
import com.example.truckercore.view_model.states.WelcomeFragState.Initial
import com.example.truckercore.view_model.states.WelcomeFragState.Stage
import com.example.truckercore.view_model.states.WelcomeFragState.Success
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomeFragEvent.LeftFabCLicked
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomeFragEvent.RightFabClicked
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomeFragEvent.TopButtonClicked
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomeFragmentViewModel
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomePagerData
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * WelcomeFragment is a Fragment that represents the welcome screen of the application.
 * It handles displaying a ViewPager with different welcome pages, managing FAB buttons,
 * and interacting with the ViewModel to manage UI states and events.
 */
class WelcomeFragment : CloseAppFragment() {

    // Binding --------------------------------------------------------------
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    // ViewModel && Args ---------------------------------------------------------------------------
    private val viewModel: WelcomeFragmentViewModel by viewModel()

    // ViewPager -----------------------------------------------------------------------------------
    private var viewPager: ViewPager2? = null
    private var pagerAdapter: WelcomePagerAdapter? = null
    private val pagerListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            viewModel.notifyPagerChanged(position)
        }
    }

    //----------------------------------------------------------------------------------------------
    // onCreate()
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Launch coroutines to collect fragment states and events.
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setFragmentStateManager()
                setFragmentEventsManager()
            }
        }
    }

    /**
     * Sets up the fragment's state manager by collecting fragment state changes.
     */
    private fun CoroutineScope.setFragmentStateManager() {
        this.launch {
            viewModel.fragmentState.collect { state ->
                when (state) {
                    is Initial -> Unit
                    is Success -> handleSuccessState(state)
                    is Error -> handleErrorState(state)
                }
            }
        }
    }

    /**
     * Handles the success state, managing the ViewPager and FAB visibility.
     */
    private fun handleSuccessState(state: Success) {
        handleViewPager(state.data)
        handleLeftFab(state.uiStage)
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
                setCurrentItem(viewModel.pagerPos, false)
                TabLayoutMediator(binding.fragWelcomeTabLayout, this) { _, _ -> }.attach()
            }
        }
    }

    /**
     * Controls the visibility of the left FAB based on the current fragment stage.
     */
    private fun handleLeftFab(stage: Stage): Unit =
        with(binding.fragWelcomeLeftFab) {
            if (stage == Stage.UserInFirsPage)
                slideOutBottom(INVISIBLE, duration = 200)
            else {
                slideInBottom()
            }
        }

    /**
     * Handles errors by showing a notification activity and finishing the current activity.
     */
    private fun handleErrorState(state: Error) {
       /* val intent = NotificationActivity.newInstance(
            context = requireContext(),
            gifRes = R.drawable.gif_error,
            errorHeader = state.type.getFieldName(),
            errorBody = state.message
        )
        startActivity(intent)
        requireActivity().finish()*/
    }

    /**
     * Sets up event from the ViewModel to handle user interactions.
     */
    private suspend fun setFragmentEventsManager() {
        viewModel.fragmentEvent.collect { event ->
            when (event) {
                LeftFabCLicked -> paginateViewPager(Direction.Back)
                RightFabClicked -> handleRightFabClicked()
                TopButtonClicked -> navigateToAuthOptionsFragment()
            }
        }
    }

    /**
     * Changes the ViewPager's current item based on the direction (forward or back).
     */
    private fun paginateViewPager(direction: Direction) {
        viewPager?.run {
            setCurrentItem(currentItem + direction.value, true)
        }
    }

    /**
     * Handles the right FAB click event.
     * If on the last page, navigates to the authentication options.
     * Otherwise, it advances the ViewPager.
     */
    private suspend fun handleRightFabClicked() {
        if (viewModel.getUiStage() == Stage.UserInLastPage) {
            navigateToAuthOptionsFragment()
        } else {
            paginateViewPager(Direction.Forward)
        }
    }

    /**
     * Navigates to the `AuthOptionsFragment` by updating the app's access status and performing a navigation action.
     *
     * This function marks that the user has already accessed the app by saving the status in `PreferenceDataStore`.
     * After updating the access status, it creates a navigation direction to the `AuthOptionsFragment` and performs the navigation.
     */
    private suspend fun navigateToAuthOptionsFragment() {
        // Mark the app as accessed in the shared preferences or data store.
        //PreferenceDataStore.getInstance().setAppAlreadyAccessed(requireContext())

        // Navigate to destination direction.
        val direction = WelcomeFragmentDirections.actionWelcomeFragmentToEmailAuthFragment()
        navigateToDirection(direction)
    }

    //----------------------------------------------------------------------------------------------
    // onCreateView()
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(layoutInflater)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // onViewCreated()
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRightFabListener()
        setLeftFabListener()
        setButtonListener()
    }

    /**
     * Sets up the listener for the right FAB click event.
     */
    private fun setRightFabListener() {
        binding.fragWelcomeRightFab.setOnClickListener {
            viewModel.setEvent(RightFabClicked)
        }
    }

    /**
     * Sets up the listener for the left FAB click event.
     */
    private fun setLeftFabListener() {
        binding.fragWelcomeLeftFab.setOnClickListener {
            viewModel.setEvent(LeftFabCLicked)
        }
    }

    /**
     * Sets up the listener for the jump button click event.
     */
    private fun setButtonListener() {
        binding.fragWelcomeJumpButton.setOnClickListener {
            viewModel.setEvent(TopButtonClicked)
        }
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