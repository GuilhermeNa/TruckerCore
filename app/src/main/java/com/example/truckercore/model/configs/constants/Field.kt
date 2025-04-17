package com.example.truckercore.model.configs.constants

/**
 * Enum class representing various fields in the system.
 * Each constant corresponds to a specific field name used within the system.
 */
enum class Field(private val fieldName: String) {
    ID("id"),
    COMPANY_ID("companyId"),
    CATEGORY("category"),


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
    PARENT("parent"),
    PARENT_ID("parentId"),
    TYPE("type"),
    VACATION_ID("vacationId"),
    USER_ID("userId"),
    PLATE("plate"),
    EXERCISE("exercise"),
    BRAND("brand"),
    COLOR("color"),
    URL("url"),
    LAST_MODIFIER_ID("lastModifierId"),
    CREATION_DATE("creationDate"),
    LAST_UPDATE("lastUpdate"),
    IS_UPDATING("isUpdating"),
    NAME("name"),
    EMAIL("email"),
    NUMBER("number"),
    EMISSION_DATE("emissionDate"),
    EXPIRATION_DATE("expirationDate"),
    KEYS("keys"),
    LEVEL("level"),
    IS_VIP("isVip"),
    VIP_START("vipStart"),
    VIP_END("vipEnd"),
    PERSON_FLAG("personFlag"),
    PERMISSIONS("permissions"),
    PERSISTENCE_STATUS("persistenceStatus"),
    EMPLOYEE_STATUS("employeeStatus"),
    AUTHORIZED_USER_IDS("authorizedUserIds"),
    IS_ACTIVE("isActive"),
    NOTIFICATION_DATE("notificationDate"),
    TITLE("title"),
    MESSAGE("message"),
    IS_READ("isRead"),
    ;

    /**
     * Gets the name of the field.
     *
     * @return The name of the field.
     */
    fun getName(): String = fieldName

}