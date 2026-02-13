package com.example.truckercore.core.config.collections

import com.example.truckercore.core.config.collections.CollectionResolver.invoke
import com.example.truckercore.core.my_lib.expressions.getTag
import com.example.truckercore.infra.logger.AppLogger
import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.layers.data.base.dto.impl.AccessDto
import com.example.truckercore.layers.data.base.dto.impl.AdminDto
import com.example.truckercore.layers.data.base.dto.impl.CompanyDto
import com.example.truckercore.layers.data.base.dto.impl.UserDto
import com.example.truckercore.layers.domain.base.contracts.entity.ID
import com.example.truckercore.layers.domain.base.ids.CompanyID
import com.example.truckercore.layers.domain.base.ids.UserID

/**
 * Resolves the appropriate [AppCollection] based on different
 * application-level representations such as DTOs, IDs, or class types.
 *
 * This resolver is used by the backend to determine the correct
 * data collection to be used by APIs when persisting or retrieving
 * application data.
 *
 * It provides multiple [invoke] operator overloads to allow
 * flexible resolution depending on the available context.
 *
 * @see AppCollection
 */
object CollectionResolver {

    /**
     * Resolves the collection based on a DTO instance.
     *
     * @param dto The data transfer object used to infer the collection.
     * @return The corresponding [AppCollection].
     * @throws IllegalArgumentException If the DTO type is not supported.
     */
    operator fun invoke(dto: BaseDto): AppCollection {
        return when (dto) {
            is CompanyDto -> AppCollection.COMPANY
            is UserDto -> AppCollection.USER
            is AccessDto -> AppCollection.ACCESS
            is AdminDto -> AppCollection.ADMIN
            else -> dto.handleError()
        }
    }

    private fun BaseDto.handleError(): Nothing {
        val message = "Unsupported DTO type: ${this::class.simpleName}"
        throw IllegalArgumentException(message)
    }

    /**
     * Resolves the collection based on an entity identifier.
     *
     * @param id The identifier used to infer the collection.
     * @return The corresponding [AppCollection].
     * @throws IllegalArgumentException If the ID type is not supported.
     */
    operator fun invoke(id: ID): AppCollection {
        return when (id) {
            is CompanyID -> AppCollection.COMPANY
            is UserID -> AppCollection.USER
            else -> id.handleError()
        }
    }

    private fun ID.handleError(): Nothing {
        val message = "Unsupported ID type: ${this::class.simpleName}"
        throw IllegalArgumentException(message)
    }

    /**
     * Resolves the collection based on a DTO class reference.
     *
     * This is useful in scenarios where only the class type is available,
     * such as generic services or reflection-based operations.
     *
     * @param clazz The DTO class used to infer the collection.
     * @return The corresponding [AppCollection].
     * @throws IllegalArgumentException If the class type is not supported.
     */
    operator fun <T : BaseDto> invoke(clazz: Class<T>): AppCollection {
        return when (clazz) {
            CompanyDto::class.java -> AppCollection.COMPANY
            UserDto::class.java -> AppCollection.USER
            else -> clazz.handleError()
        }
    }

    private fun <T : BaseDto> Class<T>.handleError(): Nothing {
        val message = "Unsupported Class type: ${this::class.simpleName}"
        throw IllegalArgumentException(message)
    }


}