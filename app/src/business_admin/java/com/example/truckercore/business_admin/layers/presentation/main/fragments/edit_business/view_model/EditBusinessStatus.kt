package com.example.truckercore.business_admin.layers.presentation.main.fragments.edit_business.view_model

sealed class EditBusinessStatus {

    object Loading : EditBusinessStatus()

    object Failure : EditBusinessStatus()

    object  Loaded : EditBusinessStatus()

}