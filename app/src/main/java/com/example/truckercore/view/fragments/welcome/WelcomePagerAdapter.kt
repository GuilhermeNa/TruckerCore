package com.example.truckercore.view.fragments.welcome

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.truckercore.view_model.welcome_fragment.WelcomePagerData

class WelcomePagerAdapter(
    private val pagerDataSet: List<WelcomePagerData>,
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = pagerDataSet.size

    override fun createFragment(position: Int): Fragment {
        val fragmentItem = WelcomeItemFragment
            .newInstance(pagerDataSet[position])
        return fragmentItem
    }

}