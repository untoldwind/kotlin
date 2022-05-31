/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.kpm.idea.serialize

interface IdeaKpmSerializationContext {
    val extras: IdeaKpmExtrasSerializationExtension
    val logger: IdeaKpmSerializationLogger

    object Empty : IdeaKpmSerializationContext {
        override val extras: IdeaKpmExtrasSerializationExtension = IdeaKpmExtrasSerializationExtension.Empty
        override val logger: IdeaKpmSerializationLogger = IdeaKpmSerializationLogger.None
    }
}
