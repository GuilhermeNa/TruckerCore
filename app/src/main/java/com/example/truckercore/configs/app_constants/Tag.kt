package com.example.truckercore.configs.app_constants

enum class Tag(private val tagName: String) {
    DEBUG("tag_debug"),
    ERROR("tag_error");

    fun getName(): String = tagName

}