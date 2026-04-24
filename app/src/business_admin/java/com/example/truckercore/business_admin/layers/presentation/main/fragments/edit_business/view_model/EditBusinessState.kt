package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model

import com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.data.EditBusinessView
import com.example.truckercore.layers.presentation.base.contracts.State

sealed class EditBusinessState : State {

    data object Failure : EditBusinessState()

    data object Loading : EditBusinessState()

    data class Loaded(
        val data: EditBusinessView,
        val ready: Boolean,
        val saving: Boolean = false,
        val saved: Boolean = false
    ) : EditBusinessState()

    fun failure() = Failure

    fun loaded(rData: EditBusinessView) = Loaded(data = rData, ready = true)

    fun textChange(validationMap: Map<Int, String?>, fieldsAreValid: Boolean): Loaded {
        if (this !is Loaded) throw IllegalStateException()

        val newData = data.update(validationMap)

        return copy(data = newData, ready = fieldsAreValid)
    }

    fun saving(): Loaded {
        if (this !is Loaded)
            throw IllegalStateException()
        return copy(saving = true)
    }

    fun saved(): Loaded {
        if (this !is Loaded)
            throw IllegalStateException()
        return copy(saving = false, saved = true)
    }

}