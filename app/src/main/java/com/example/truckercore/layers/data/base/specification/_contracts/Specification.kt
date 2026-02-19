package com.example.truckercore.layers.data.base.specification._contracts

import com.example.truckercore.core.config.collections.AppCollection
import com.example.truckercore.core.config.collections.CollectionResolver
import com.example.truckercore.core.error.InfraException
import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.data.base.filter.collection.SearchFilters
import com.example.truckercore.layers.domain.base.contracts.entity.ID

/**
 * Defines the structure for querying data in a type-safe and declarative way.
 *
 * A [Specification] encapsulates all the necessary information to query or observe
 * data from a specific backend, including collection name, optional entity ID, and
 * a list of filters. It acts as the contract between the application and the data
 * source, abstracting backend-specific query logic.
 *
 * This interface is generic over [T], which represents the DTO type being queried.
 *
 * ### Example (Firestore):
 * ```kotlin
 * data class GetUserByIdSpec(
 *      val userId: UserID? = null,
 *      val name: String? = null,
 *      val age: Int? = null
 * ) : Specification<UserDto> {
 *     override val dtoClass = UserDto::class.java
 *     override val entityId = userId
 *     override val collection = Collection("users")
 * }
 * ```
 *
 * @param T The DTO type that will be returned from the data source.
 * @property dtoClass The class reference for the DTO type.
 * @property entityId The optional identifier for unique entity lookups.
 * @property id Returns the [entityId] value or throws [SpecificationException] if it's null.
 * @property collection The target collection where the entity/entities are stored.
 * @property collection Convenience property to get the name of the collection.
 * @see Filter
 * @see SpecificationException
 */
interface Specification<T : BaseDto> {

    val entityId: ID?

    val dtoClass: Class<T>

    val collection: AppCollection get() = CollectionResolver(dtoClass)

    val isSearchById get() = entityId != null

    /**
     * Builds a dynamic list of [Filter] to apply in multi-entity queries.
     *
     * Implementations of this function typically evaluate optional parameters and build
     * filtering conditions based on the values that are not null. This allows for flexible
     * and type-safe querying, without exposing backend-specific logic.
     *
     * ### Example:
     * ```kotlin
     * override fun getFilters(): List<Filter> {
     *     val filters = mutableListOf<Filter>()
     *     companyId?.let { filters.add(WhereEqual(Field.COMPANY_ID, it.value)) }
     *     category?.let { filters.add(WhereEqual(Field.CATEGORY, it)) }
     *     return filters
     * }
     * ```
     * @see Filter
     * @return A list of filters to apply to the query. Returns an empty list if no filtering is required.
     */
    fun getFilter(): SearchFilters

    fun validate() {
        if (entityId == null && getFilter().isEmpty) throw InfraException.Specification(
            "The specification should contain at least one search parameter."
        )
    }

}