package com.example.truckercore.shared.exceptions

/**
 * Custom exception class that represents errors related to objects that are not approved.
 *
 * This exception is thrown when an operation is attempted on an object that has not been approved
 * by an admin user.
 *
 * @param message An optional error message providing details about the specific issue. Defaults to null.
 */
class NotApprovedObjectException(message: String? = null): Exception(message)