package com.example.truckercore.infrastructure.database.firebase.implementations

import com.example.truckercore.configs.database.Field
import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseQueryBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Query.Direction

internal class FirebaseQueryBuilderImpl(val firestore: FirebaseFirestore) :
    FirebaseQueryBuilder {

    private fun collection(collectionName: String) = firestore.collection(collectionName)

    override fun buildDocumentReferenceQuery(collectionName: String, id: String) =
        collection(collectionName).document(id)

    override fun buildMasterUidQuery(collectionName: String, masterUid: String) =
        collection(collectionName).whereEqualTo(Field.MASTER_UID, masterUid)

    override fun buildTruckIdQuery(collectionName: String, truckId: String) =
        collection(collectionName).whereEqualTo(Field.TRUCK_ID, truckId)

    override fun buildEmployeeIdQuery(collectionName: String, employeeId: String) =
        collection(collectionName).whereEqualTo(Field.EMPLOYEE_ID, employeeId)

    override fun buildTruckIdFilteredQuery(
        collectionName: String,
        truckId: String,
        filter: String,
        limit: Int,
        direction: Direction
    ) = collection(collectionName)
        .whereEqualTo(Field.TRUCK_ID, truckId)
        .orderBy(filter, direction)
        .limit(limit.toLong())

    override fun buildTruckIdAndStatusQuery(
        collectionName: String,
        truckId: String,
        statusList: List<String>
    ) = collection(collectionName)
        .whereEqualTo(Field.TRUCK_ID, truckId)
        .whereIn(Field.STATUS, statusList)

    override fun buildCustomerIdQuery(collectionName: String, customerId: String): Query =
        collection(collectionName).whereEqualTo(Field.CUSTOMER_ID, customerId)

    override fun buildTravelIdQuery(collectionName: String, travelId: String): Query =
        collection(collectionName).whereEqualTo(Field.TRAVEL_ID, travelId)

    override fun buildLabelIdQuery(collectionName: String, labelId: String): Query =
        collection(collectionName).whereEqualTo(Field.LABEL_ID, labelId)

    override fun buildFreightIdQuery(collectionName: String, freightId: String): Query =
        collection(collectionName).whereEqualTo(Field.FREIGHT_ID, freightId)

    override fun buildParentIdQuery(collectionName: String, parentId: String): Query =
        collection(collectionName).whereEqualTo(Field.PARENT_ID, parentId)

    override fun buildVacationIdQuery(collectionName: String, vacationId: String): Query =
        collection(collectionName).whereEqualTo(Field.VACATION_ID, vacationId)

    override fun buildUserIdQuery(collectionName: String, userId: String): Query =
        collection((collectionName)).whereEqualTo(Field.USER_ID, userId)

}