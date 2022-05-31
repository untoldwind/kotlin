/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.kpm.idea.testFixtures

import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmExtrasSerializationExtension
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmExtrasSerializer
import org.jetbrains.kotlin.tooling.core.Extras
import org.jetbrains.kotlin.tooling.core.Type

@Suppress("UNCHECKED_CAST")
class TestIdeaKpmExtrasSerializationExtension : IdeaKpmExtrasSerializationExtension {
    override fun <T : Any> serializer(key: Extras.Key<T>): IdeaKpmExtrasSerializer<T>? = when (key.type) {
        Type<String>() -> TestIdeaKpmStringExtrasSerializer as IdeaKpmExtrasSerializer<T>
        Type<Int>() -> TestIdeaKpmIntExtrasSerializer as IdeaKpmExtrasSerializer<T>
        else -> null
    }
}
