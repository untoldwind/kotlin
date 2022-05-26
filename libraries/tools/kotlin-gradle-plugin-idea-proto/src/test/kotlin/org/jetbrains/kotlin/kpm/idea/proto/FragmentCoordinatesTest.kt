/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmFragmentCoordinatesImpl
import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmModuleCoordinatesImpl
import org.junit.Test
import kotlin.test.assertEquals

class FragmentCoordinatesTest {

    @Test
    fun `serialize - deserialize - sample 0`() = testDeserializedEquals(
        IdeaKpmFragmentCoordinatesImpl(
            module = IdeaKpmModuleCoordinatesImpl(
                buildId = "buildId",
                projectPath = "projectPath",
                projectName = "projectName",
                moduleName = "moduleName",
                moduleClassifier = null
            ),
            fragmentName = "myFragmentName"
        )
    )

    private fun testDeserializedEquals(coordinates: IdeaKpmFragmentCoordinatesImpl) {
        assertEquals(
            coordinates, IdeaKpmFragmentCoordinates(coordinates.toByteArray())
        )
    }
}
