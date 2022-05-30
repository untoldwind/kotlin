/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializationContext
import org.jetbrains.kotlin.gradle.kpm.idea.testFixtures.TestInstances
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ContainerTest : IdeaKpmSerializationContext {
    override val logger = TestLogger()
    override val extras = TestExtrasExtension()

    @Test
    fun `deserialize - with too high major version - returns null`() {
        val data = protoIdeaKpmContainer {
            schemaVersionMajor = ProtoIdeaKpmSchema.versionMajor + 1
            schemaVersionMinor = ProtoIdeaKpmSchema.versionMinor
            schemaVersionPatch = ProtoIdeaKpmSchema.versionPatch
        }.toByteArray()

        assertTrue(logger.reports.isEmpty(), "Expected no reports in logger")
        assertNull(IdeaKpmProject(data))
        assertTrue(logger.reports.isNotEmpty(), "Expected at least one report in logger")
    }

    @Test
    fun `deserialize - with lower major version - returns object`() {
        val data = protoIdeaKpmContainer {
            schemaVersionMajor = ProtoIdeaKpmSchema.versionMajor - 1
            schemaVersionMinor = ProtoIdeaKpmSchema.versionMinor
            schemaVersionPatch = ProtoIdeaKpmSchema.versionPatch
            project = ProtoIdeaKpmProject(TestInstances.simpleProject)
        }.toByteArray()

        assertEquals(TestInstances.simpleProject, IdeaKpmProject(data))
    }
}
