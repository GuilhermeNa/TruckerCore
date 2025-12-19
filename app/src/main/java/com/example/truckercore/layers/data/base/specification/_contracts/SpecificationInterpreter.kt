package com.example.truckercore.layers.data.base.specification._contracts

import com.example.truckercore.core.config.collections.AppCollection
import com.example.truckercore.layers.data.base.filter.collection.SearchFilters
import com.example.truckercore.layers.domain.base.contracts.entity.ID

/**
 * Interface responsible for translating domain-level [Specification] objects into
 * backend-specific query or document wrappers ([ApiSpecificationWrapper]).
 *
 * Implementations of this interface encapsulate the logic for converting high-level
 * specifications into concrete API calls or database queries, allowing the
 * [DataSource] layer to remain agnostic of the underlying data source.
 */
interface SpecificationInterpreter {

    /**
     * Generates an [ApiSpecificationWrapper] to fetch a single entity by its unique identifier.
     *
     * @param id The unique identifier of the entity.
     * @param collection The collection or table where the entity resides.
     * @return An [ApiSpecificationWrapper] that can be used by the data source to retrieve the entity.
     */
    fun byId(id: ID, collection: AppCollection): ApiSpecificationWrapper

    /**
     * Generates an [ApiSpecificationWrapper] to fetch entities matching the given filters.
     *
     * @param searchFilter The filter criteria to apply when querying the collection.
     * @param collection The collection or table to search in.
     * @return An [ApiSpecificationWrapper] that can be used by the data source to execute the query.
     */
    fun byFilter(searchFilter: SearchFilters, collection: AppCollection): ApiSpecificationWrapper

}
