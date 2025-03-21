package com.example.truckercore.view_model.welcome_fragment

sealed class FabState {
    data object Paginate: FabState()
    data object Navigate: FabState()
}