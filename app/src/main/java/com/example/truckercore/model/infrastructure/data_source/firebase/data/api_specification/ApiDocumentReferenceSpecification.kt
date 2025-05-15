package com.example.truckercore.model.infrastructure.data_source.firebase.data.api_specification

import com.example.truckercore.model.infrastructure.integration.data.for_api.data.contracts.ApiSpecification
import com.google.firebase.firestore.DocumentReference

class ApiDocumentReferenceSpecification(val document: DocumentReference) : ApiSpecification