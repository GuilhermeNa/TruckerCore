package com.example.truckercore.layers.presentation.nav_login.fragments.welcome

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.truckercore.domain.view_models.welcome_fragment.WelcomePagerData

/**
 * Adapter for the ViewPager2 component that manages the fragments in the [WelcomeFragment].
 * This adapter takes a list of [WelcomePagerData] and creates a corresponding fragment for each data item.
 * Each fragment displays an image, title, and message as provided by the `WelcomePagerData`.
 *
 * @param pagerDataSet The list of `WelcomePagerData` that contains the data to be displayed.
 * @param fragment The fragment that is associated with the `FragmentStateAdapter` (usually the parent fragment).
 */
class WelcomePagerAdapter(
    private val pagerDataSet: List<WelcomePagerData>,
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    /**
     * @return The number of items in the pager data set.
     */
    override fun getItemCount(): Int = pagerDataSet.size

    /**
     * Creates a new fragment for the given position.
     * This method returns a new instance of `WelcomeItemFragment` populated with the data from the `pagerDataSet`.
     *
     * @param position The position of the item to create the fragment for.
     * @return A new instance of `WelcomeItemFragment` with the data corresponding to the position.
     */
    override fun createFragment(position: Int): Fragment {
        // Create a new instance of `WelcomeItemFragment` with the data for the current position.
        val fragmentItem = WelcomeItemFragment
            .newInstance(pagerDataSet[position])
        return fragmentItem
    }

}