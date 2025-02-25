package com.example.truckercore.shared.enums

/**
 * Enum class that represents the different types of query operations that can be used
 * when interacting with a database or query system.
 * It provides options to specify conditions for queries, such as equality or inclusion.
 *
 */
enum class QueryType {
    WHERE_EQUALS,
    WHERE_IN;
}