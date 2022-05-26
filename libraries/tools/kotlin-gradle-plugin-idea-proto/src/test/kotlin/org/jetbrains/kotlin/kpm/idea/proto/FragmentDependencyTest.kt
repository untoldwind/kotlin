/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmFragmentCoordinatesImpl
import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmFragmentDependency
import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmFragmentDependencyImpl
import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmModuleCoordinatesImpl
import org.junit.Test
import kotlin.test.assertEquals

class FragmentDependencyTest {

    private val coordinates = IdeaKpmFragmentCoordinatesImpl(
        module = IdeaKpmModuleCoordinatesImpl(
            buildId = "buildId",
            projectPath = "projectPath",
            projectName = "projectName",
            moduleName = "moduleName",
            moduleClassifier = "moduleClassifier"
        ),
        fragmentName = "fragmentName"
    )

    @Test
    fun `serialize - deserialize - sample 0`() = testDeserializedEquals(
        IdeaKpmFragmentDependencyImpl(
            type = IdeaKpmFragmentDependency.Type.Regular,
            coordinates
        )
    )

    @Test
    fun `serialize - deserialize - sample 1`() = testDeserializedEquals(
        IdeaKpmFragmentDependencyImpl(
            type = IdeaKpmFragmentDependency.Type.Refines,
            coordinates
        )
    )


    @Test
    fun `serialize - deserialize - sample 2`() = testDeserializedEquals(
        IdeaKpmFragmentDependencyImpl(
            type = IdeaKpmFragmentDependency.Type.Friend,
            coordinates
        )
    )

    private fun testDeserializedEquals(value: IdeaKpmFragmentDependencyImpl) {
        assertEquals(value, IdeaKpmFragmentDependency(value.toByteArray()))
    }
}
