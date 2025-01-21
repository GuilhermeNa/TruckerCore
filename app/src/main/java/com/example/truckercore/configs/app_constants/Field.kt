package com.example.truckercore.configs.app_constants

enum class Field(private val fieldName: String) {

    BUSINESS_CENTRAL_ID("centralId"),
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
    EMAIL("email"),
    NUMBER("number"),
    EMISSION_DATE("emissionDate"),
    LEVEL("level"),
    PERMISSIONS("permissions"),
    PERSISTENCE_STATUS("persistenceStatus"),
    EMPLOYEE_STATUS("employeeStatus");


    fun getName(): String = fieldName

}