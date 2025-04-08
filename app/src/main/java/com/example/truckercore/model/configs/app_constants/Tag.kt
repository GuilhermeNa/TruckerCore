package com.example.truckercore.model.configs.app_constants

/**
 * Enum class representing different tags used for logging or categorization.
 * Each constant corresponds to a specific tag name used to classify log messages.
 */
enum class Tag(private val tagName: String) {
    DEBUG("tag_debug"),
    ERROR("tag_error"),
    INFO("tag_info"),
    WARN("tag_warn");

    /**
     * Gets the name of the tag.
     *
     * @return The name of the tag.
     */
    fun getName(): String = tagName

}