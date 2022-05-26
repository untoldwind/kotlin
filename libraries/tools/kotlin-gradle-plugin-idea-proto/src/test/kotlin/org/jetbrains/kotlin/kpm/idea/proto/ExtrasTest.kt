/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializationContext
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializer
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializer.Deserialized.Failure
import org.jetbrains.kotlin.tooling.core.*
import org.junit.Test
import kotlin.test.assertEquals

class ExtrasTest {

    class Ignored

    @Suppress("unchecked_cast")
    @Test
    fun `serialize - deserialize - sample 0`() {
        val context = object : IdeaKpmSerializationContext {
            override fun <T : Any> serializer(type: Type<T>): IdeaKpmSerializer<T>? = when (type) {
                Type<String>() -> StringSerializer as IdeaKpmSerializer<T>
                Type<Int>() -> IntSerializer as IdeaKpmSerializer<T>
                else -> null
            }

            override fun <T : Any> onDeserializationFailure(failure: Failure<T>): T? = null
        }

        val extras = mutableExtrasOf(
            extrasKeyOf<String>() withValue "myValue",
            extrasKeyOf<String>("a") withValue "myValueA",
            extrasKeyOf<Int>() withValue 2411,
            extrasKeyOf<Ignored>() withValue Ignored()
        )

        val data = context.ProtoIdeaKpmExtras(extras).toByteArray()
        val deserialized = context.Extras(ProtoIdeaKpmExtras.parseFrom(data))

        assertEquals(
            extras.filter { (_, value) -> value !is Ignored }.toExtras(), deserialized
        )
    }
}
