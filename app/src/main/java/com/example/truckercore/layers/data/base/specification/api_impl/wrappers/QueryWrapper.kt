package com.example.truckercore.layers.data.base.specification.api_impl.wrappers

import com.example.truckercore.layers.data.base.specification._contracts.ApiSpecificationWrapper
import com.google.firebase.firestore.Query

class QueryWrapper(val query: Query) : ApiSpecificationWrapper