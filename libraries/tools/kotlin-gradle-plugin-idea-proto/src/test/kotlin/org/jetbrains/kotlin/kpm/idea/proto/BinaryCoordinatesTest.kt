/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmBinaryCoordinates
import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmBinaryCoordinatesImpl
import org.jetbrains.kotlin.kpm.idea.proto.IdeaKpmBinaryCoordinates
import org.jetbrains.kotlin.kpm.idea.proto.toByteArray
import org.junit.Test
import kotlin.test.assertEquals

class BinaryCoordinatesTest {

    @Test
    fun `serialize - deserialize - sample 0`() = testDeserializedEquals(
        IdeaKpmBinaryCoordinatesImpl(
            group = "myGroup",
            module = "myModule",
            version = "myVersion",
            kotlinModuleName = null,
            kotlinFragmentName = null
        )
    )

    @Test
    fun `serialize - deserialize - sample 1`() = testDeserializedEquals(
        IdeaKpmBinaryCoordinatesImpl(
            group = "myGroup",
            module = "myModule",
            version = "myVersion",
            kotlinModuleName = "myModuleName",
            kotlinFragmentName = null
        )
    )

    @Test
    fun `serialize - deserialize - sample 2`() = testDeserializedEquals(
        IdeaKpmBinaryCoordinatesImpl(
            group = "myGroup",
            module = "myModule",
            version = "myVersion",
            kotlinModuleName = null,
            kotlinFragmentName = "myFragmentName"
        )
    )

    private fun testDeserializedEquals(settings: IdeaKpmBinaryCoordinates) {
        assertEquals(
            settings, IdeaKpmBinaryCoordinates(settings.toByteArray())
        )
    }
}
