package com.example.truckercore.view.fragments.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.BundleCompat
import androidx.fragment.app.Fragment
import com.example.truckercore.databinding.FragmentWelcomeItemBinding
import com.example.truckercore.view.expressions.loadGif
import com.example.truckercore.view_model.view_models.welcome_fragment.WelcomePagerData

/**
 * A fragment that represents a single item in the [WelcomeFragment] screen of the application.
 * It displays a GIF, a title, and a message as provided by the [WelcomePagerData] passed to it.
 */
class WelcomeItemFragment : Fragment() {

    // Binding -------------------------------------------------------------------------------------
    private var _binding: FragmentWelcomeItemBinding? = null
    private val binding get() = _binding!!

    // Data passed to the fragment, containing information about the image, title, and message
    private var data: WelcomePagerData? = null

    //----------------------------------------------------------------------------------------------
    // onCreate()
    //----------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            data = BundleCompat.getParcelable(bundle, ARG_PAGER_DATA, WelcomePagerData::class.java)
        }
    }

    //----------------------------------------------------------------------------------------------
    // onCreateView()
    //----------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeItemBinding.inflate(layoutInflater)
        return binding.root
    }

    //----------------------------------------------------------------------------------------------
    // On View Created
    //----------------------------------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data?.let {
            // Load the GIF, set the title, and message
            binding.fragWelcomeItemImage.loadGif(it.res, requireContext())
            binding.fragWelcomeItemTitle.text = it.title
            binding.fragWelcomeItemMessage.text = it.message
        }
    }

    companion object {

        private const val ARG_PAGER_DATA = "param1"

        /**
         * A static method that creates a new instance of `WelcomeItemFragment` with the given `WelcomePagerData`.
         * This method sets the necessary arguments for the fragment.
         *
         * @param pagerData The `WelcomePagerData` to be passed to the fragment.
         * @return A new instance of `WelcomeItemFragment`.
         */
        @JvmStatic
        fun newInstance(pagerData: WelcomePagerData) =
            WelcomeItemFragment().apply {
                arguments = Bundle().apply {
                    // Pass the pager data as an argument to the fragment
                    putParcelable(ARG_PAGER_DATA, pagerData)
                }
            }

    }

}