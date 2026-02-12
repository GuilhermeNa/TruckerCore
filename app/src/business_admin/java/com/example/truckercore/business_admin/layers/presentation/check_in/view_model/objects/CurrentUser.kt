package com.example.truckercore.business_admin.layers.presentation.check_in.view_model.objects

import com.example.truckercore.core.my_lib.classes.Email
import com.example.truckercore.core.my_lib.classes.Name
import com.example.truckercore.layers.domain.base.ids.UID

data class CurrentUser(
    val uid: UID,
    val name: Name,
    val email: Email
)
