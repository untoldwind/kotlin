/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmModuleCoordinates
import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmModuleCoordinatesImpl

fun ProtoIdeaKpmModuleCoordinates(coordinates: IdeaKpmModuleCoordinates): ProtoIdeaKpmModuleCoordinates {
    return protoIdeaKpmModuleCoordinates {
        buildId = coordinates.buildId
        projectPath = coordinates.projectPath
        projectName = coordinates.projectName
        moduleName = coordinates.moduleName
        coordinates.moduleClassifier?.let { moduleClassifier = it }
    }
}

fun IdeaKpmModuleCoordinates(proto: ProtoIdeaKpmModuleCoordinates): IdeaKpmModuleCoordinates {
    return IdeaKpmModuleCoordinatesImpl(
        buildId = proto.buildId,
        projectPath = proto.projectPath,
        projectName = proto.projectName,
        moduleName = proto.moduleName,
        moduleClassifier = if (proto.hasModuleClassifier()) proto.moduleClassifier else null
    )
}

fun IdeaKpmModuleCoordinates(data: ByteArray): IdeaKpmModuleCoordinates {
    return IdeaKpmModuleCoordinates(ProtoIdeaKpmModuleCoordinates.parseFrom(data))
}

fun IdeaKpmModuleCoordinates.toByteArray(): ByteArray {
    return ProtoIdeaKpmModuleCoordinates(this).toByteArray()
}
