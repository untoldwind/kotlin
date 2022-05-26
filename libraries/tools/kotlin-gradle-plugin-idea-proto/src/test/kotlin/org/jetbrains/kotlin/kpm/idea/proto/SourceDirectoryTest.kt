/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmSourceDirectoryImpl
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class SourceDirectoryTest {

    @Test
    fun `serialize - deserialize - sample 0`() = testDeserializedEquals(
        IdeaKpmSourceDirectoryImpl(File("myFile"))
    )

    @Test
    fun `serialize - deserialize - sample 1`() = testDeserializedEquals(
        IdeaKpmSourceDirectoryImpl(File("myFile").absoluteFile)
    )

    private fun testDeserializedEquals(sourceDirectory: IdeaKpmSourceDirectoryImpl) {
        assertEquals(
            sourceDirectory.copy(file = sourceDirectory.file.absoluteFile),
            IdeaKpmSourceDirectory(sourceDirectory.toByteArray())
        )
    }
}
