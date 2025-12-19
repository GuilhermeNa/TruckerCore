package com.example.truckercore.core.my_lib.expressions

import com.example.truckercore.core.error.DataException
import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

private const val TO_DTO_NULL_ERROR_MSG =
    "Expected a non-null DocumentSnapshot, but received null. " +
            "This may indicate that the document reference is invalid or was not properly fetched."

private const val TO_DTO_MAP_ERROR_MSG = "Failed to map DocumentSnapshot." +
        " The snapshot exists but contains data incompatible with the expected DTO structure."

private const val TO_LIST_NULL_ERROR_MSG =
    "Expected a non-null QuerySnapshot, but received null. " +
            "This may be due to a failed query execution or an unexpected Firestore response."

/**
 * Safely converts a [DocumentSnapshot] to a DTO of type [T].
 *
 * @param clazz The class of the DTO.
 * @return The mapped DTO, or `null` if the document doesn't exist.
 * @throws DataException.Unknown If the snapshot is null.
 * @throws DataException.Mapping If the mapping fails.
 */
fun <T : BaseDto> DocumentSnapshot?.toDto(clazz: Class<T>): T? = when {
    this == null -> throw DataException.Unknown(TO_DTO_NULL_ERROR_MSG)
    !exists() -> null
    else -> toObject(clazz) ?: throw DataException.Mapping(TO_DTO_MAP_ERROR_MSG)
}

/**
 * Safely converts a [QuerySnapshot] to a list of DTOs of type [T].
 *
 * @param clazz The class of the DTO.
 * @return A list of mapped DTOs, or `null` if the snapshot is empty.
 * @throws DataException.Unknown If the snapshot is null.
 */
fun <T : BaseDto> QuerySnapshot?.toList(clazz: Class<T>): List<T>? = when {
    this == null -> throw DataException.Unknown(TO_LIST_NULL_ERROR_MSG)
    isEmpty -> null
    else -> mapNotNull { it.toDto(clazz) }
}