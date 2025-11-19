package com.example.truckercore.layers.presentation.base.handlers

import android.widget.Button
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.example.truckercore.layers.presentation.base.objects.ViewBinder
import com.example.truckercore.layers.presentation.base.components.ButtonComponent
import com.example.truckercore.layers.presentation.base.components.TextComponent
import com.example.truckercore.core.my_lib.ui_components.TextInputComponent
import com.google.android.material.textfield.TextInputLayout
import java.lang.ref.WeakReference

abstract class StateHandler<T : ViewBinding> {

    private val binder = ViewBinder

    private var _binding: WeakReference<T>? = null

    protected val binding
        get() = requireNotNull(_binding?.get()) {
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

    protected fun bindText(component: TextComponent, view: TextView) {
        binder.bindText(component, view)
    }

}