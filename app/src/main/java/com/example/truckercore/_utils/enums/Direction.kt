package com.example.truckercore._utils.enums

/**
 * A sealed class representing the direction of navigation or page scrolling.
 * It encapsulates two possible directions: `Forward` and `Back`.
 *
 * Each direction has an associated integer value:
 * `+1` for forward movement and `-1` for backward movement.
 * This allows direct usage in pagination logic, list traversal,
 * or step-based navigation without needing to define the value programmatically.
 *
 * Sealed classes enable exhaustive `when` statements for safer and more maintainable code.
 */
enum class Direction(val value: Int) {

    /**
     * Represents the "Forward" direction with a value of +1.
     * Typically used to move to the next item, page, or step.
     */
    Forward(+1),

    /**
     * Represents the "Back" direction with a value of -1.
     * Typically used to move to the previous item, page, or step.
     */
    Back(-1)

}