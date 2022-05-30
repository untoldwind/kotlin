/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmVariantImpl
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializationContext
import org.jetbrains.kotlin.gradle.kpm.idea.testFixtures.TestInstances
import kotlin.test.Test
import kotlin.test.assertEquals

class VariantTest : IdeaKpmSerializationContext by TestSerializationContext {

    @Test
    fun `serialize - deserialize - sample 0`() {
        assertDeserializedEquals(TestInstances.simpleVariant)
    }

    @Test
    fun `serialize - deserialize - sample 1`() {
        assertDeserializedEquals(TestInstances.variantWithExtras)
    }

    private fun assertDeserializedEquals(value: IdeaKpmVariantImpl) {
        assertEquals(value, IdeaKpmVariant(value.toByteArray(this)))
    }
}
