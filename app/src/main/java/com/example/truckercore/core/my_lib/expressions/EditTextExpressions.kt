package com.example.truckercore.core.my_lib.expressions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.truckercore.core.my_lib.files.HALF_SEC
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun EditText.textChanges(): Flow<String> = callbackFlow {
    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            trySend(s?.toString().orEmpty())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    addTextChangedListener(watcher)

    // valor inicial
    trySend(text.toString())

    awaitClose { removeTextChangedListener(watcher) }
}

@OptIn(FlowPreview::class)
fun EditText.onTextChange(
    scope: LifecycleCoroutineScope,
    onChanged: (String) -> Unit,
    debounceTime: Long = HALF_SEC
) {
    textChanges()
        .debounce(debounceTime)
        .onEach(onChanged)
        .launchIn(scope)
}

fun EditText.addCnpjMask(): TextWatcher {
    var isUpdating = false

    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (isUpdating) return

            val digits = s.toString().replace(Regex("\\D"), "")
            val formatted = StringBuilder()

            var i = 0
            for (c in "##.###.###/####-##") {
                if (c != '#' && digits.length > i) {
                    formatted.append(c)
                } else {
                    if (i < digits.length) {
                        formatted.append(digits[i])
                        i++
                    }
                }
            }

            isUpdating = true
            setText(formatted.toString())
            setSelection(formatted.length)
            isUpdating = false
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    addTextChangedListener(watcher)
    return watcher
}

fun EditText.addDateMask(): TextWatcher {
    var isUpdating = false

    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (isUpdating) return

            val digits = s.toString().replace(Regex("\\D"), "")
            val formatted = StringBuilder()

            var i = 0
            for (c in "##/##/##") {
                if (c != '#' && digits.length > i) {
                    formatted.append(c)
                } else {
                    if (i < digits.length) {
                        formatted.append(digits[i])
                        i++
                    }
                }
            }

            isUpdating = true
            setText(formatted.toString())
            setSelection(formatted.length)
            isUpdating = false
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    addTextChangedListener(watcher)
    return watcher
}