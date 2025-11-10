package com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.state

import com.example.truckercore.core.my_lib.ui_components.Fab
import com.example.truckercore.layers.presentation.viewmodels.base._contracts.State
import com.example.truckercore.layers.presentation.viewmodels.view_models.welcome_fragment.WelcomePagerData
import java.security.InvalidParameterException

data class WelcomeFragmentState(
    val pagerPos: Int = INITIAL_POS,
    val fabState: Fab = Fab(),
    val data: List<WelcomePagerData>
) : State {

    fun isLastPage() = pagerPos == (data.size - 1)

    fun isFirstPage() = pagerPos == INITIAL_POS

    fun updatePage(newPos: Int): WelcomeFragmentState = copy(pagerPos = newPos)

    fun paginateBack(): WelcomeFragmentState {
        if (isFirstPage()) throw InvalidParameterException(
            "Cannot paginate back: already on the first page."
        )
        val newPos = pagerPos - 1
        return copy(pagerPos = newPos)
    }

    fun paginateForward(): WelcomeFragmentState {
        if (isLastPage()) throw InvalidParameterException(
            "Cannot paginate forward: already on the last page."
        )
        val newPos = pagerPos + 1
        return copy(pagerPos = newPos)
    }

    private companion object {
        private const val INITIAL_POS = 0
    }

}