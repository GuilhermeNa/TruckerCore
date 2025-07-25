package com.example.truckercore._shared.expressions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

fun ViewModel.launch(block: suspend () -> Unit) {
    viewModelScope.launch {
        block()
    }
}