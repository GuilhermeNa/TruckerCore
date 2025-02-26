package com.example.truckercore.shared.utils.parameters

import com.example.truckercore.modules.user.entity.User

/**
 * An interface representing the common parameters required for a search.
 *
 * This interface defines the essential properties that are required for performing a search operation:
 * the user making the search request and a flag indicating whether the results should be streamed.
 * This interface can be extended by specific search parameter classes that add more search criteria,
 * such as `QueryParameters` or `DocumentParameters`, while still adhering to the core search structure.
 *
 * @property user The user making the search request.
 * @property shouldStream A boolean flag indicating if the search result should be streamed.
 */
interface SearchParameters {
    val user: User
    val shouldStream: Boolean
}