/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.kpm.idea.serialize

import java.io.PrintStream

interface IdeaKpmSerializationLogger {
    fun report(message: String? = null, cause: Throwable? = null)

    object None : IdeaKpmSerializationLogger {
        override fun report(message: String?, cause: Throwable?) = Unit
    }

    data class Console(val out: PrintStream = System.err) : IdeaKpmSerializationLogger {
        override fun report(message: String?, cause: Throwable?) {
            if (message == null && cause == null) return
            out.println("[KPM][Serialization]: $message")
            cause?.printStackTrace(out)
        }
    }
}
