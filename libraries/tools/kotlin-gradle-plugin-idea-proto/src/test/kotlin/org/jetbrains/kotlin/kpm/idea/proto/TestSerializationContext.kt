/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializationContext
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializer
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializer.Deserialized.Failure
import org.jetbrains.kotlin.tooling.core.Type

@Suppress("unchecked_cast")
object TestSerializationContext : IdeaKpmSerializationContext {
    override fun <T : Any> serializer(type: Type<T>): IdeaKpmSerializer<T>? = when (type) {
        Type<String>() -> StringSerializer as IdeaKpmSerializer<T>
        Type<Int>() -> IntSerializer as IdeaKpmSerializer<T>
        else -> null
    }

    override fun <T : Any> onDeserializationFailure(failure: Failure<T>): T? = null
}
