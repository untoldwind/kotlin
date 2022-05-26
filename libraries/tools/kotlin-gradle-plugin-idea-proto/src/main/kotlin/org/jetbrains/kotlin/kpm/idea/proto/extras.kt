/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import com.google.protobuf.ByteString
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializationContext
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializer
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializer.Deserialized
import org.jetbrains.kotlin.tooling.core.Extras
import org.jetbrains.kotlin.tooling.core.toExtras
import org.jetbrains.kotlin.tooling.core.withValue

@Suppress("unchecked_cast")
fun IdeaKpmSerializationContext.ProtoIdeaKpmExtras(extras: Extras): ProtoIdeaKpmExtras {
    return protoIdeaKpmExtras {
        extras.entries.forEach { (key, value) ->
            val serializer = serializer(key.type) ?: return@forEach
            serializer as IdeaKpmSerializer<Any>
            values.put(key.stableString, ByteString.copyFrom(serializer.serialize(value)))
        }
    }
}

@Suppress("unchecked_cast")
fun IdeaKpmSerializationContext.Extras(proto: ProtoIdeaKpmExtras): Extras {
    return proto.valuesMap.entries.mapNotNull { (keyString, value) ->
        val key = Extras.Key.fromString(keyString) as Extras.Key<Any>
        val serializer = serializer(key.type) ?: return@mapNotNull null
        val deserialized = when (val result = serializer.deserialize(value.toByteArray())) {
            is Deserialized.Failure<*> -> onDeserializationFailure(result) ?: return@mapNotNull null
            is Deserialized.Value -> result.value
        }

        key withValue deserialized
    }.toExtras()
}
