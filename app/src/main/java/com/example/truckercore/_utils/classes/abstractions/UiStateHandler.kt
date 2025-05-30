package com.example.truckercore._utils.classes.abstractions

import android.widget.Button
import androidx.viewbinding.ViewBinding
import com.example.truckercore._utils.classes.ui_component.ButtonComponent
import com.example.truckercore._utils.classes.ui_component.TextInputComponent
import com.example.truckercore._utils.classes.ui_component.ViewBinder
import com.google.android.material.textfield.TextInputLayout
import java.lang.ref.WeakReference

abstract class UiStateHandler<T : ViewBinding> {

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