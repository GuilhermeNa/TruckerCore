package com.example.truckercore.configs.app_constants

enum class Field(private val fieldName: String) {

    MASTER_UID("masterUid"),
    EMPLOYEE_ID("employeeId"),
    INITIAL_DATE("initialDate"),
    ODOMETER("odometer"),
    TRUCK_ID("truckId"),
    STATUS("status"),
    CUSTOMER_ID("customerId"),
    TRAVEL_ID("travelId"),
    LABEL_ID("labelId"),
    FREIGHT_ID("freightId"),
    PARENT_ID("parentId"),
    TYPE("type"),
    VACATION_ID("vacationId"),
    USER_ID("userId"),
    URL("url"),
    ID("id"),
    LAST_MODIFIER_ID("lastModifierId"),
    CREATION_DATE("creationDate"),
    LAST_UPDATE("lastUpdate"),
    IS_UPDATING("isUpdating"),
    NAME("name"),
    NUMBER("number"),
    EMISSION_DATE("emissionDate"),
    PERSISTENCE_STATUS("persistenceStatus");

    fun getName(): String = fieldName

}