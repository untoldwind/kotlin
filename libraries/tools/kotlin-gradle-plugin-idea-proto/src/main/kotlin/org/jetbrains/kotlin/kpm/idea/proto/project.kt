/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmProject
import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmProjectImpl
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializationContext
import java.io.File

fun IdeaKpmSerializationContext.ProtoIdeaKpmProject(project: IdeaKpmProject): ProtoIdeaKpmProject {
    return protoIdeaKpmProject {
        gradlePluginVersion = project.gradlePluginVersion
        coreLibrariesVersion = project.coreLibrariesVersion
        project.explicitApiModeCliOption?.let { explicitApiModeCliOption = it }
        kotlinNativeHome = project.kotlinNativeHome.absolutePath
        modules.addAll(project.modules.map { ProtoIdeaKpmModule(it) })
    }
}

fun IdeaKpmSerializationContext.IdeaKpmProject(proto: ProtoIdeaKpmProject): IdeaKpmProject {
    return IdeaKpmProjectImpl(
        gradlePluginVersion = proto.gradlePluginVersion,
        coreLibrariesVersion = proto.coreLibrariesVersion,
        explicitApiModeCliOption = if (proto.hasExplicitApiModeCliOption()) proto.explicitApiModeCliOption else null,
        kotlinNativeHome = File(proto.kotlinNativeHome),
        modules = proto.modulesList.map { IdeaKpmModule(it) }
    )
}

fun IdeaKpmSerializationContext.IdeaKpmProject(data: ByteArray): IdeaKpmProject {
    return IdeaKpmProject(ProtoIdeaKpmProject.parseFrom(data))
}

fun IdeaKpmProject.toByteArray(context: IdeaKpmSerializationContext): ByteArray {
    return context.ProtoIdeaKpmProject(this).toByteArray()
}
