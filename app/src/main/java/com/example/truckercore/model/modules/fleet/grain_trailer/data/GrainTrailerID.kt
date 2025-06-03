package com.example.truckercore.model.modules.fleet.grain_trailer.data

import com.example.truckercore.model.modules._shared._contracts.entity.ID

@JvmInline
value class GrainTrailerID(override val value: String): ID {

    init {
        validate()
    }

    companion object {
        fun generate() = GrainTrailerID(ID.generate())
    }

}