package com.example.truckercore.layers.data.data_source.data.expressions

import com.example.truckercore.core.error.DataException
import com.example.truckercore.layers.data.base.dto.contracts.BaseDto
import com.example.truckercore.to_delete.errors.mapping.MappingException
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

/**
 * Safely converts a [DocumentSnapshot] to a DTO of type [T].
 *
 * If the document does not exist, returns `null`. If the snapshot is null or mapping fails,
 * a [DataSourceException] is thrown.
 *
 * @param clazz The class of the DTO.
 * @return The mapped DTO, or `null` if the document doesn't exist.
 * @throws InvalidDataException If the snapshot is null.
 * @throws MappingException If the mapping fails.
 */
fun <T : BaseDto> DocumentSnapshot?.toDto(clazz: Class<T>): T? =
    if (this == null) throw DataException.Unknown(
        message = "Expected a non-null DocumentSnapshot, but received null. " +
                "This may indicate that the document reference is invalid or was not properly fetched."
    ) else if (!exists()) null
    else toObject(clazz) ?: throw DataException.Mapping(
        message = "Failed to map DocumentSnapshot to ${clazz.simpleName}." +
                " The snapshot exists but contains data incompatible with the expected DTO structure."
    )

/**
 * Safely converts a [QuerySnapshot] to a list of DTOs of type [T].
 *
 * If the snapshot is empty, returns `null`. If the snapshot is null, throws an [InvalidDataException].
 *
 * @param clazz The class of the DTO.
 * @return A list of mapped DTOs, or `null` if the snapshot is empty.
 * @throws InvalidDataException If the snapshot is null.
 */
fun <T : BaseDto> QuerySnapshot?.toList(clazz: Class<T>): List<T>? =
    if (this == null) throw DataException.Unknown(
        message = "Expected a non-null QuerySnapshot, but received null. " +
                "This may be due to a failed query execution or an unexpected Firestore response."
    ) else if (isEmpty) null
    else mapNotNull { it.toDto(clazz) }
