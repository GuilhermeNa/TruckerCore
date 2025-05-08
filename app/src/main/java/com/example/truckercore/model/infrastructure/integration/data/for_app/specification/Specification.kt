package com.example.truckercore.model.infrastructure.integration.data.for_app.specification

import com.example.truckercore.model.configs.constants.Collection
import com.example.truckercore.model.infrastructure.integration.data.for_app.specification.exceptions.SpecificationException
import com.example.truckercore.model.modules._contracts.ID
import com.example.truckercore.model.infrastructure.integration.data.for_app.contracts.BaseDto

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
 * @property collectionName Convenience property to get the name of the collection.
 * @see Filter
 * @see SpecificationException
 */
interface Specification<T : BaseDto> {

    /**
     * The class of the Data Transfer Object this specification expects.
     */
    val dtoClass: Class<T>

    /**
     * Optional unique identifier of the entity to retrieve.
     * Used in single-entity queries.
     */
    val entityId: ID?

    /**
     * Non-null version of [entityId], throws [SpecificationException] if null.
     * Useful for forcing the presence of an ID in ID-based lookups.
     */
    val id: String
        get() = entityId?.value ?: throw SpecificationException(
            "The specification does not contain a valid entity ID: $this"
        )

    /**
     * The collection where the data is located.
     */
    val collection: Collection
    val collectionName: String get() = collection.name

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
    fun getFilters(): List<Filter>

}