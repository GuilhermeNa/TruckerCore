package com.example.truckercore.shared.utils.parameters

import com.example.truckercore.configs.app_constants.Field
import com.example.truckercore.shared.enums.QueryType

data class QuerySettings(val field: Field, val type: QueryType, val value: Any)