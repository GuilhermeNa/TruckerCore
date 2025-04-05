package com.example.truckercore.view.fragments.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.truckercore.view.expressions.getBackPressCallback
import com.example.truckercore.view.helpers.ExitAppManager

abstract class CloseAppFragment : Fragment() {

    // BackPressed ---------------------------------------------------------------------------------
    private val exitManager by lazy { ExitAppManager() }
    private val backPressCallback by lazy { getBackPressCallback(exitManager) }

    //----------------------------------------------------------------------------------------------
    // onViewCreated()
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackPressedCallback()
    }

    /**
     * Sets a callback to handle the back press event in the fragment's lifecycle.
     *
     * This method adds a custom back press callback to the `OnBackPressedDispatcher` of the
     * current activity, ensuring that the back press event is intercepted and handled by
     * the provided `backPressCallback`. The callback is associated with the `viewLifecycleOwner`,
     * meaning it will be automatically removed when the fragment's view is destroyed.
     */
    private fun setBackPressedCallback() {
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, backPressCallback)
    }

    //----------------------------------------------------------------------------------------------
    // onDestroyView()
    //----------------------------------------------------------------------------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        exitManager.cancelCoroutines()
    }

}