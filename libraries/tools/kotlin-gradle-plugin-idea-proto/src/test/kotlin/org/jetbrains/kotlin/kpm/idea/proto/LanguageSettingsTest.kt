/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmLanguageSettingsImpl
import org.jetbrains.kotlin.kpm.idea.proto.IdeaKpmLanguageSettings
import org.jetbrains.kotlin.kpm.idea.proto.ProtoIdeaKpmLanguageSettings
import org.jetbrains.kotlin.kpm.idea.proto.toByteArray
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class LanguageSettingsTest {

    @Test
    fun `serialize - deserialize - sample 0`() = testDeserializedEquals(
        IdeaKpmLanguageSettingsImpl(
            languageVersion = "1.3",
            apiVersion = "1.4",
            isProgressiveMode = false,
            enabledLanguageFeatures = setOf("some.feature.1"),
            optInAnnotationsInUse = setOf("some.opt.in", "some.other.opt.in"),
            compilerPluginArguments = listOf("my.argument"),
            compilerPluginClasspath = listOf(File("classpath")),
            freeCompilerArgs = listOf("free.compiler.arg.1", "free.compiler.arg.2")
        )
    )

    @Test
    fun `serialize - deserialize - sample 1`() = testDeserializedEquals(
        IdeaKpmLanguageSettingsImpl(
            languageVersion = null,
            apiVersion = "1.7",
            isProgressiveMode = true,
            enabledLanguageFeatures = emptySet(),
            optInAnnotationsInUse = emptySet(),
            compilerPluginArguments = emptyList(),
            compilerPluginClasspath = emptyList(),
            freeCompilerArgs = emptyList()
        )
    )

    private fun testDeserializedEquals(settings: IdeaKpmLanguageSettingsImpl) {
        assertEquals(
            settings.normalized(), IdeaKpmLanguageSettings(settings.toByteArray())
        )
    }

    private fun IdeaKpmLanguageSettingsImpl.normalized(): IdeaKpmLanguageSettingsImpl {
        return copy(
            compilerPluginClasspath = compilerPluginClasspath.map { it.absoluteFile }
        )
    }
}
