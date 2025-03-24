package com.example.truckercore.view.sealeds

/**
 * A sealed class representing the direction for navigation or page scrolling.
 * It encapsulates two possible navigation directions: `Forward` and `Back`.
 * Sealed classes allow for a limited and controlled set of subclasses, providing exhaustiveness checking in `when` expressions.
 *
 * This class can be used to determine the direction of pagination, navigation, or movement in a specific UI component.
 */
sealed class Direction {

    /**
     * Represents the "Forward" direction, typically used to navigate to the next item or page.
     * This object can be used in cases where you want to define navigation going forward,
     * such as swiping to the next page or moving to the next step in a flow.
     */
    data object Forward : Direction()

    /**
     * Represents the "Back" direction, typically used to navigate to the previous item or page.
     * This object can be used in cases where you want to define navigation going backward,
     * such as swiping to the previous page or going back to the previous step in a flow.
     */
    data object Back : Direction()

}