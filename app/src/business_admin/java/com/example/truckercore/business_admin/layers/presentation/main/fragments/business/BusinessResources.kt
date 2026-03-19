package com.example.truckercore.business_admin.layers.presentation.main.fragments.business

import com.example.truckercore.R
import com.example.truckercore.core.my_lib.expressions.span
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

    val notification = NOTIFICATION.span(TXT_TO_EDIT, size = 15, bold = true)

    private companion object {

        private const val NOTIFICATION = "Você ainda não completou o cadastro da sua empresa." +
                " Finalize-o para ter acesso a todas as funcionalidades."

        private const val TXT_TO_EDIT = "cadastro da sua empresa"

        private const val ANTT = "ANTT"

        private const val SOCIAL_CONTRACT = "Contr. Social"

    }

}
