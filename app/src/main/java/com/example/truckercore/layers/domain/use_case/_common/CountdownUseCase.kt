package com.example.truckercore.layers.domain.use_case._common

import com.example.truckercore.core.my_lib.files.ONE_SEC
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CountdownUseCase {

    operator fun invoke(start: Int): Flow<Int> = flow {
        for (i in start downTo 0) {
            emit(i)
            delay(ONE_SEC)
        }
    }

}