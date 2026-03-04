package com.example.truckercore.layers.domain.base.others

@JvmInline
value class Cnpj(val value: String) {

    init {
        validate()
    }

    private fun validate() {

    }

}