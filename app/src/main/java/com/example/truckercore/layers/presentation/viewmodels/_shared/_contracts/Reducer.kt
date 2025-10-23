package com.example.truckercore.layers.presentation.viewmodels._shared._contracts

import com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult

interface Reducer<E: com.example.truckercore.presentation.viewmodels._shared._contracts.Event, S: com.example.truckercore.presentation.viewmodels._shared._contracts.State, F: com.example.truckercore.presentation.viewmodels._shared._contracts.Effect> {

    fun reduce(state: S, event: E): com.example.truckercore.presentation.viewmodels._shared._base.reducer.ReducerResult<S, F>

}

