package com.example.truckercore.view._shared._base.handlers

import android.widget.Button
import androidx.viewbinding.ViewBinding
import com.example.truckercore.view_model._shared.components.ButtonComponent
import com.example.truckercore.view_model._shared.components.TextInputComponent
import com.example.truckercore.view._shared.helpers.ViewBinder
import com.google.android.material.textfield.TextInputLayout
import java.lang.ref.WeakReference

abstract class StateHandler<T : ViewBinding> {

    private val binder = ViewBinder

    private var _binding: WeakReference<T>? = null

    protected fun getBinding() = requireNotNull(_binding?.get()) {
        "Binding was not set or already collected."
    }

    fun initialize(binding: T) {
        _binding = WeakReference(binding)
    }

    protected fun bindInputLayout(component: TextInputComponent, view: TextInputLayout) {
        binder.bindTextInput(component, view)
    }

    protected fun bindButton(component: ButtonComponent, view: Button) {
        binder.bindButton(component, view)
    }

}