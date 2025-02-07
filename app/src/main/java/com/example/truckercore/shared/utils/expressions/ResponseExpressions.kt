package com.example.truckercore.shared.utils.expressions

import com.example.truckercore.shared.utils.sealeds.Response

fun Response<Any>.checkIsErrorOrEmpty(): Boolean =
    this is Response.Error || this is Response.Empty

fun Response<Any>.checkIsEmpty(): Boolean =
    this is Response.Empty

fun Response<Any>.checkIsError(): Boolean =
    this is Response.Error