/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmBinaryCoordinatesImpl
import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmResolvedBinaryDependencyImpl
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializationContext
import org.jetbrains.kotlin.tooling.core.emptyExtras
import org.jetbrains.kotlin.tooling.core.extrasKeyOf
import org.jetbrains.kotlin.tooling.core.extrasOf
import org.jetbrains.kotlin.tooling.core.withValue
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class ResolvedBinaryDependencyTest : IdeaKpmSerializationContext by TestSerializationContext {

    @Test
    fun `serialize - deserialize - sample 0`() = testDeserializedEquals(
        IdeaKpmResolvedBinaryDependencyImpl(
            null, binaryType = "binaryType", binaryFile = File("bin"), emptyExtras()
        )
    )

    @Test
    fun `serialize - deserialize - sample 1`() = testDeserializedEquals(
        IdeaKpmResolvedBinaryDependencyImpl(
            coordinates = IdeaKpmBinaryCoordinatesImpl(
                group = "group",
                module = "module",
                version = "version",
                kotlinModuleName = null,
                kotlinFragmentName = null
            ),
            binaryType = "binaryType",
            binaryFile = File("bin"),
            extras = extrasOf(extrasKeyOf<Int>() withValue 2411)
        )
    )

    private fun testDeserializedEquals(value: IdeaKpmResolvedBinaryDependencyImpl) {
        assertEquals(
            value.copy(binaryFile = value.binaryFile.absoluteFile),
            IdeaKpmResolvedBinaryDependency(value.toByteArray(this))
        )
    }
}
