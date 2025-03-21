package com.example.truckercore.view.fragments.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.BundleCompat
import androidx.fragment.app.Fragment
import com.example.truckercore.databinding.FragmentWelcomeItemBinding
import com.example.truckercore.view.expressions.loadGif
import com.example.truckercore.view_model.welcome_fragment.WelcomePagerData

private const val ARG_PAGER_DATA = "param1"

class WelcomeItemFragment : Fragment() {

    private var _binding: FragmentWelcomeItemBinding? = null
    private val binding get() = _binding!!

    private var data: WelcomePagerData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            data = BundleCompat.getParcelable(bundle, ARG_PAGER_DATA, WelcomePagerData::class.java)
        }
    }

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
        bindView()
    }

    private fun bindView() {
        data?.let {
            binding.fragWelcomeItemImage.loadGif(it.res, requireContext())
            binding.fragWelcomeItemTitle.text = it.title
            binding.fragWelcomeItemMessage.text = it.message
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(pagerData: WelcomePagerData) =
            WelcomeItemFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PAGER_DATA, pagerData)
                }
            }
    }
}