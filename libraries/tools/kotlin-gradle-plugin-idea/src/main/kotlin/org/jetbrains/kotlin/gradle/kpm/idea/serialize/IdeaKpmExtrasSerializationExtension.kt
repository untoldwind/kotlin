/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.kpm.idea.serialize

import org.jetbrains.kotlin.tooling.core.Extras

interface IdeaKpmExtrasSerializationExtension {
    fun <T : Any> serializer(key: Extras.Key<T>): IdeaKpmExtrasSerializer<T>?
}
