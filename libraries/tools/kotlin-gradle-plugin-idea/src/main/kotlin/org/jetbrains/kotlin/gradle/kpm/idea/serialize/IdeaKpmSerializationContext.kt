/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.kpm.idea.serialize

import org.jetbrains.kotlin.tooling.core.Type

interface IdeaKpmSerializationContext {
    fun <T : Any> serializer(type: Type<T>): IdeaKpmSerializer<T>?
    fun <T : Any> onDeserializationFailure(failure: IdeaKpmSerializer.Deserialized.Failure<T>): T?
}

inline fun <reified T : Any> IdeaKpmSerializationContext.serializer(): IdeaKpmSerializer<T>? {
    return serializer(Type())
}
