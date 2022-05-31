/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.kpm.idea.testFixtures

import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializationLogger

class TestIdeaKpmSerializationLogger : IdeaKpmSerializationLogger {
    data class Report(val message: String? = null, val cause: Throwable? = null)

    private val _reports = mutableListOf<Report>()

    val reports get() = _reports.toList()

    override fun report(message: String?, cause: Throwable?) {
        _reports.add(Report(message, cause))
    }
}

