package com.example.truckercore.shared.utils.sealeds

import com.example.truckercore.shared.utils.parameters.QueryData

sealed class SearchCriteria {
    data class ById(val id: String) : SearchCriteria()
    data class ByQuery(val data: List<QueryData>) : SearchCriteria()
}