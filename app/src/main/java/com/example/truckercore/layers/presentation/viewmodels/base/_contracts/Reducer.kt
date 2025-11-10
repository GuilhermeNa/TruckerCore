package com.example.truckercore.layers.presentation.viewmodels.base._contracts

import com.example.truckercore.layers.presentation.viewmodels.base._base.reducer.ReducerResult

interface Reducer<E: Event, S: State, F: Effect> {

    fun reduce(state: S, event: E): ReducerResult<S, F>

}

