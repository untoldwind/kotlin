/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmCompilationOutputImpl
import org.jetbrains.kotlin.gradle.kpm.idea.testFixtures.TestInstances
import kotlin.test.Test
import kotlin.test.assertEquals

class CompilationOutputTest {

    @Test
    fun `serialize - deserialize - sample 0`() {
        assertDeserializedEquals(TestInstances.simpleCompilationOutput)
    }

    @Test
    fun `serialize - deserialize - sample 1`() {
        assertDeserializedEquals(
            IdeaKpmCompilationOutputImpl(
                classesDirs = emptySet(),
                resourcesDir = null
            )
        )
    }

    private fun assertDeserializedEquals(value: IdeaKpmCompilationOutputImpl) {
        assertEquals(value, IdeaKpmCompilationOutput(value.toByteArray()))
    }
}
