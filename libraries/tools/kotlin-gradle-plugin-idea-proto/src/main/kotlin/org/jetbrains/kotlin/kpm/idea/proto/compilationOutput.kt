/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmCompilationOutput
import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmCompilationOutputImpl
import java.io.File

fun ProtoIdeaKpmCompilationOutput(output: IdeaKpmCompilationOutput): ProtoIdeaKpmCompilationOutput {
    return protoIdeaKpmCompilationOutput {
        classesDirs.addAll(output.classesDirs.map { it.absolutePath })
        output.resourcesDir?.absolutePath?.let { this.resourcesDir = it }
    }
}


fun IdeaKpmCompilationOutput(proto: ProtoIdeaKpmCompilationOutput): IdeaKpmCompilationOutput {
    return IdeaKpmCompilationOutputImpl(
        classesDirs = proto.classesDirsList.map { File(it) }.toSet(),
        resourcesDir = if (proto.hasResourcesDir()) File(proto.resourcesDir) else null
    )
}

fun IdeaKpmCompilationOutput(data: ByteArray): IdeaKpmCompilationOutput {
    return IdeaKpmCompilationOutput(ProtoIdeaKpmCompilationOutput.parseFrom(data))
}

fun IdeaKpmCompilationOutput.toByteArray(): ByteArray {
    return ProtoIdeaKpmCompilationOutput(this).toByteArray()
}
