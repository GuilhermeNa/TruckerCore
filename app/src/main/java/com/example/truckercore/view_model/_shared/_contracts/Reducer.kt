package com.example.truckercore.view_model._shared._contracts

import com.example.truckercore.view_model._shared._base.reducer.ReducerResult

interface Reducer<E: Event, S: State, F: Effect> {

    fun reduce(state: S, event: E): ReducerResult<S, F>

}

