/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmFragmentDependency
import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmFragmentDependencyImpl
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializationContext

fun IdeaKpmSerializationContext.ProtoIdeaKpmFragmentDependency(dependency: IdeaKpmFragmentDependency): ProtoIdeaKpmFragmentDependency {
    return protoIdeaKpmFragmentDependency {
        type = when (dependency.type) {
            IdeaKpmFragmentDependency.Type.Regular -> ProtoIdeaKpmFragmentDependency.Type.REGULAR
            IdeaKpmFragmentDependency.Type.Friend -> ProtoIdeaKpmFragmentDependency.Type.FRIEND
            IdeaKpmFragmentDependency.Type.Refines -> ProtoIdeaKpmFragmentDependency.Type.REFINES
        }

        coordinates = ProtoIdeaKpmFragmentCoordinates(dependency.coordinates)
        extras = ProtoIdeaKpmExtras(dependency.extras)
    }
}

fun IdeaKpmSerializationContext.IdeaKpmFragmentDependency(proto: ProtoIdeaKpmFragmentDependency): IdeaKpmFragmentDependency {
    return IdeaKpmFragmentDependencyImpl(
        type = when (proto.type) {
            ProtoIdeaKpmFragmentDependency.Type.REGULAR -> IdeaKpmFragmentDependency.Type.Regular
            ProtoIdeaKpmFragmentDependency.Type.FRIEND -> IdeaKpmFragmentDependency.Type.Friend
            ProtoIdeaKpmFragmentDependency.Type.REFINES -> IdeaKpmFragmentDependency.Type.Refines
            else -> IdeaKpmFragmentDependency.Type.Regular
        },
        coordinates = IdeaKpmFragmentCoordinates(proto.coordinates),
        extras = Extras(proto.extras)
    )
}

fun IdeaKpmSerializationContext.IdeaKpmFragmentDependency(data: ByteArray): IdeaKpmFragmentDependency {
    return IdeaKpmFragmentDependency(ProtoIdeaKpmFragmentDependency.parseFrom(data))
}

fun IdeaKpmFragmentDependency.toByteArray(context: IdeaKpmSerializationContext): ByteArray {
    return context.ProtoIdeaKpmFragmentDependency(this).toByteArray()
}
