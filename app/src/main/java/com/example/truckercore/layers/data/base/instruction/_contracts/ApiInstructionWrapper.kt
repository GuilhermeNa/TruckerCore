package com.example.truckercore.layers.data.base.instruction._contracts

/**
 * Marker interface for instructions that wrap API-specific operations.
 *
 * Classes implementing this interface represent executable instructions bound to a specific
 * external API (e.g., Firebase, REST services, SDKs).
 *
 * In the application architecture, domain-level [Instruction] instances are first created
 * in an API-agnostic format. These instructions are then converted into one or more
 * [ApiInstructionWrapper] implementations, which adapt the instruction to the concrete
 * API and execution model required by the target platform.
 *
 * @see Instruction
 */
interface ApiInstructionWrapper