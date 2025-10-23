package com.example.truckercore.layers.domain.base.contracts.others

import com.example.truckercore.core.my_lib.classes.Url
import com.example.truckercore.layers.domain.base.contracts.entity.Entity

interface Document: Entity {

    val url: Url

}