package com.example.truckercore.model.shared.utils.expressions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.job

fun CoroutineScope.cancelJob() = this.coroutineContext.job.cancel()