/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmFragmentDependency
import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmFragmentDependencyImpl

fun ProtoIdeaKpmFragmentDependency(dependency: IdeaKpmFragmentDependency): ProtoIdeaKpmFragmentDependency {
    return protoIdeaKpmFragmentDependency {
        type = when (dependency.type) {
            IdeaKpmFragmentDependency.Type.Regular -> ProtoIdeaKpmFragmentDependency.Type.REGULAR
            IdeaKpmFragmentDependency.Type.Friend -> ProtoIdeaKpmFragmentDependency.Type.FRIEND
            IdeaKpmFragmentDependency.Type.Refines -> ProtoIdeaKpmFragmentDependency.Type.REFINES
        }

        coordinates = ProtoIdeaKpmFragmentCoordinates(dependency.coordinates)
    }
}

fun IdeaKpmFragmentDependency(proto: ProtoIdeaKpmFragmentDependency): IdeaKpmFragmentDependency {
    return IdeaKpmFragmentDependencyImpl(
        type = when (proto.type) {
            ProtoIdeaKpmFragmentDependency.Type.REGULAR -> IdeaKpmFragmentDependency.Type.Regular
            ProtoIdeaKpmFragmentDependency.Type.FRIEND -> IdeaKpmFragmentDependency.Type.Friend
            ProtoIdeaKpmFragmentDependency.Type.REFINES -> IdeaKpmFragmentDependency.Type.Refines
            else -> IdeaKpmFragmentDependency.Type.Regular
        },
        coordinates = IdeaKpmFragmentCoordinates(proto.coordinates)
    )
}

fun IdeaKpmFragmentDependency(data: ByteArray): IdeaKpmFragmentDependency {
    return IdeaKpmFragmentDependency(ProtoIdeaKpmFragmentDependency.parseFrom(data))
}

fun IdeaKpmFragmentDependency.toByteArray(): ByteArray {
    return ProtoIdeaKpmFragmentDependency(this).toByteArray()
}
