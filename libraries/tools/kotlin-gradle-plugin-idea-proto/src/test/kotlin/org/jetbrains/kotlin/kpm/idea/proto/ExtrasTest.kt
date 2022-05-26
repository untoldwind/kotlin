/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializationContext
import org.jetbrains.kotlin.tooling.core.extrasKeyOf
import org.jetbrains.kotlin.tooling.core.mutableExtrasOf
import org.jetbrains.kotlin.tooling.core.toExtras
import org.jetbrains.kotlin.tooling.core.withValue
import kotlin.test.Test
import kotlin.test.assertEquals

class ExtrasTest : IdeaKpmSerializationContext by TestSerializationContext {

    class Ignored

    @Suppress("unchecked_cast")
    @Test
    fun `serialize - deserialize - sample 0`() {

        val extras = mutableExtrasOf(
            extrasKeyOf<String>() withValue "myValue",
            extrasKeyOf<String>("a") withValue "myValueA",
            extrasKeyOf<Int>() withValue 2411,
            extrasKeyOf<Ignored>() withValue Ignored()
        )

        val data = ProtoIdeaKpmExtras(extras).toByteArray()
        val deserialized = Extras(ProtoIdeaKpmExtras.parseFrom(data))

        assertEquals(
            extras.filter { (_, value) -> value !is Ignored }.toExtras(), deserialized
        )
    }
}
