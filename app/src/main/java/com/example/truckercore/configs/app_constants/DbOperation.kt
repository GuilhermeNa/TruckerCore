package com.example.truckercore.configs.app_constants

enum class DbOperation(private val _name: String) {
    CREATE("create"),
    UPDATE("update"),
    DELETE("delete");

    fun getName(): String = _name

}