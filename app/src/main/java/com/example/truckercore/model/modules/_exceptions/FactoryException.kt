package com.example.truckercore.model.modules._exceptions

class FactoryException(message: String? = null, cause: Throwable? = null) :
    Exception(message, cause) {

    companion object {
        fun getMessage(clazz: Class<*>): String {
            return "Error while creating an object in: $clazz"
        }
    }

}