/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmUnresolvedBinaryDependency
import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmUnresolvedBinaryDependencyImpl
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializationContext

fun IdeaKpmSerializationContext.ProtoIdeaKpmUnresolvedBinaryDependency(
    dependency: IdeaKpmUnresolvedBinaryDependency
): ProtoIdeaKpmUnresolvedBinaryDependency {
    return protoIdeaKpmUnresolvedBinaryDependency {
        extras = ProtoIdeaKpmExtras(dependency.extras)
        dependency.cause?.let { cause = it }
        dependency.coordinates?.let { coordinates = ProtoIdeaKpmBinaryCoordinates(it) }
    }
}

fun IdeaKpmSerializationContext.IdeaKpmUnresolvedBinaryDependency(proto: ProtoIdeaKpmUnresolvedBinaryDependency): IdeaKpmUnresolvedBinaryDependency {
    return IdeaKpmUnresolvedBinaryDependencyImpl(
        cause = if (proto.hasCause()) proto.cause else null,
        coordinates = if (proto.hasCoordinates()) IdeaKpmBinaryCoordinates(proto.coordinates) else null,
        extras = Extras(proto.extras)
    )
}

fun IdeaKpmSerializationContext.IdeaKpmUnresolvedBinaryDependency(data: ByteArray): IdeaKpmUnresolvedBinaryDependency {
    return IdeaKpmUnresolvedBinaryDependency(ProtoIdeaKpmUnresolvedBinaryDependency.parseFrom(data))
}

fun IdeaKpmUnresolvedBinaryDependency.toByteArray(context: IdeaKpmSerializationContext): ByteArray {
    return context.ProtoIdeaKpmUnresolvedBinaryDependency(this).toByteArray()
}
