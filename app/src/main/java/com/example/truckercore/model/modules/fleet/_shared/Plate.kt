package com.example.truckercore.model.modules.fleet._shared

@JvmInline
value class Plate(val value: String) {

    init {
        if(value.isBlank()) throw PlateException(
            "Plate value must not be blank."
        )
    }

}

class PlateException(message: String? = null): Exception(message)