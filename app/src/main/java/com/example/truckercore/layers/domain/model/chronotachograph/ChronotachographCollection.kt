package com.example.truckercore.layers.domain.model.chronotachograph

import com.example.truckercore.layers.domain.model.crlv.Crlv

class ChronotachographCollection(private val dataSet: MutableSet<Crlv> = mutableSetOf()) {

    val all: Set<Crlv> get() = dataSet.toSet()


}