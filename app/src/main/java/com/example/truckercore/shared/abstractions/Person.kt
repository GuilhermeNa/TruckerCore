package com.example.truckercore.shared.abstractions

import com.example.truckercore.shared.interfaces.Entity
import com.example.truckercore.shared.interfaces.PersonI

/**
 * Represents an abstract base class for a person entity within the system.
 * This class extends from [Entity] and implements [PersonI], providing a foundation for concrete
 * person types to be defined in the system.
 *
 * The [Person] class serves as a general blueprint for any specific types of people or users in the system
 * (such as customers, employees, etc.), ensuring that common properties and behaviors are shared.
 *
 * This abstract class enforces the structure required for entities that represent people in the system
 * while allowing specific subclasses to define more specialized attributes or behavior.
 */
abstract class Person : Entity, PersonI