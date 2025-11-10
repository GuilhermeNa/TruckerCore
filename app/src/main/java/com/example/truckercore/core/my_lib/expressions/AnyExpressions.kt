package com.example.truckercore.core.my_lib.expressions

fun Any.getClassName(): String = this.javaClass.simpleName

val Any.getTag: String get() = this.javaClass.simpleName