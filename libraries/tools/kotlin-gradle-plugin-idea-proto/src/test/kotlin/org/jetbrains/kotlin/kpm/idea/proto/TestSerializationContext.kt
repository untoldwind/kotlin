/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

@file:Suppress("UNCHECKED_CAST")

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmExtrasSerializationExtension
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmExtrasSerializer
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializationLogger
import org.jetbrains.kotlin.tooling.core.Extras
import org.jetbrains.kotlin.tooling.core.Type

class TestLogger : IdeaKpmSerializationLogger {
    data class Report(val message: String? = null, val cause: Throwable? = null)

    private val _reports = mutableListOf<Report>()

    val reports get() = _reports.toList()

    override fun report(message: String?, cause: Throwable?) {
        _reports.add(Report(message, cause))
    }
}

class TestExtrasExtension : IdeaKpmExtrasSerializationExtension {
    override fun <T : Any> serializer(key: Extras.Key<T>): IdeaKpmExtrasSerializer<T>? = when (key.type) {
        Type<String>() -> StringExtrasSerializer as IdeaKpmExtrasSerializer<T>
        Type<Int>() -> IntExtrasSerializer as IdeaKpmExtrasSerializer<T>
        else -> null
    }
}
