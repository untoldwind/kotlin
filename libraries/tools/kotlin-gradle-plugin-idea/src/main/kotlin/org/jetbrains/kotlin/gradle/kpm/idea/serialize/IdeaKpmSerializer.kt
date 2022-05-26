/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.kpm.idea.serialize

interface IdeaKpmSerializer<T : Any> {
    fun serialize(value: T): ByteArray
    fun deserialize(data: ByteArray): Deserialized<T>

    sealed class Deserialized<out T : Any> {
        val valueOrNull: T? get() = (this as? Value<T>)?.value
        val valueOrThrow: T
            get() = when (this) {
                is Value -> this.value
                is Failure<*> -> throw DeserializationFailureException(message, cause = null)
            }

        data class Value<T : Any>(val value: T) : Deserialized<T>()
        data class Failure<T : Any>(val message: String? = null, val cause: Throwable? = null) : Deserialized<Nothing>()
        class DeserializationFailureException(message: String?, cause: Throwable?) : Exception(message, cause)
    }
}
