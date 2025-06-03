package com.example.truckercore.model.modules._shared._contracts.factory

import com.example.truckercore._shared.expressions.getClassName
import com.example.truckercore.model.modules._shared.exceptions.FactoryException

interface Factory {

    fun handleError(e: Exception): Nothing {
        val message = "An error occurred while creating an object in ${getClassName()}."
        throw FactoryException(message, e)
    }

}