package com.example.truckercore.layers.presentation.base.handlers

import android.widget.Button
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.example.truckercore.core.my_lib.ui_components.TextInputComponent
import com.example.truckercore.core.my_lib.ui_components.ButtonComponent
import com.example.truckercore.core.my_lib.ui_components.TextComponent
import com.google.android.material.textfield.TextInputLayout
import java.lang.ref.WeakReference

/**
 * Base class responsible for handling UI state binding using [ViewBinding].
 *
 * A [StateHandler] provides a safe and reusable way to bind state-driven
 * components to Android views without holding strong references to the
 * view layer.
 *
 * @param T The type of [ViewBinding] associated with this handler.
 */
abstract class StateHandler<T : ViewBinding> {

    /**
     * Centralized binder responsible for connecting components to views.
     */
    private val binder = ViewBinder

    /**
     * Weak reference to the view binding.
     *
     * Using a [WeakReference] helps prevent memory leaks by allowing
     * the binding to be garbage-collected when the view is destroyed.
     */
    private var _binding: WeakReference<T>? = null

    /**
     * Non-nullable access to the current binding instance.
     *
     * @throws IllegalStateException if the binding was not initialized
     * or has already been collected.
     */
    protected val binding: T
        get() = requireNotNull(_binding?.get()) {
            "Binding was not set or has already been cleared."
        }

    /**
     * Initializes the handler with the provided [ViewBinding].
     *
     * This method must be called before accessing [binding].
     *
     * @param binding The view binding instance associated with the UI.
     */
    fun initialize(binding: T) {
        _binding = WeakReference(binding)
    }

    /**
     * Binds a [TextInputComponent] to a [TextInputLayout].
     *
     * @param component State component representing the input field
     * @param view Android [TextInputLayout] to bind to
     */
    protected fun bindInputLayout(
        component: TextInputComponent,
        view: TextInputLayout
    ) {
        binder.bindTextInput(component, view)
    }

    /**
     * Binds a [ButtonComponent] to a [Button].
     *
     * @param component State component representing the button
     * @param view Android [Button] to bind to
     */
    protected fun bindButton(
        component: ButtonComponent,
        view: Button
    ) {
        binder.bindButton(component, view)
    }

    /**
     * Binds a [TextComponent] to a [TextView].
     *
     * @param component State component representing dynamic text
     * @param view Android [TextView] to bind to
     */
    protected fun bindText(
        component: TextComponent,
        view: TextView
    ) {
        binder.bindText(component, view)
    }

    /**
     * Binds a static text value directly to a [TextView].
     *
     * @param text Text content to be displayed
     * @param view Android [TextView] to receive the text
     */
    protected fun bindText(
        text: String,
        view: TextView
    ) {
        binder.bindText(text, view)
    }

}