package com.example.truckercore.core.my_lib.expressions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.job

fun CoroutineScope.cancelJob() = this.coroutineContext.job.cancel()