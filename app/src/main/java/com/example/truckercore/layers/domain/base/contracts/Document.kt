package com.example.truckercore.layers.domain.base.contracts

import com.example.truckercore.core.my_lib.classes.Url

interface Document : Entity, DateRange {

    val url: Url

}