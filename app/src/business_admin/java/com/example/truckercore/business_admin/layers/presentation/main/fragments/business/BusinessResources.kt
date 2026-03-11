package com.example.truckercore.business_admin.layers.presentation.main.fragments.business

import com.example.truckercore.R
import com.example.truckercore.layers.presentation.common.lists.recycler_grid.GridItem

class BusinessResources {

    val dataSet: Array<GridItem> = arrayOf(
        GridItem(
            iconResource = R.drawable.icon_settings,
            text = ANTT
        ),
        GridItem(
            iconResource = R.drawable.icon_settings,
            text = SOCIAL_CONTRACT
        )
    )

    private companion object {
        private const val ANTT = "ANTT"

        private const val SOCIAL_CONTRACT = "Contr. Social"

    }

}
