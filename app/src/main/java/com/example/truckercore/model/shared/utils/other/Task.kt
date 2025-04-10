package com.example.truckercore.model.shared.utils.other

class Task<T>(
    val result: T? = null,
    val isSuccess: Boolean = false,
    val exception: Exception? = null
) {


}