package com.example.truckercore.model.configs.build

/**
 * Represents the available product flavors of the application.
 *
 * Each enum value corresponds to a distinct app variant with its own
 * behavior, UI, or business logic. Used to differentiate execution
 * paths based on the active flavor.
 */
enum class Flavor(val description: String) {
    /** Flavor for individual users (e.g., general customers). */
    INDIVIDUAL("Trucker"),

    /** Flavor for administrators or company managers. */
    ADMIN("Trucker Empresa"),

    /** Flavor for professional drivers or delivery personnel. */
    DRIVER("Trucker Motorista");
}