/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmFragmentCoordinates
import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmFragmentCoordinatesImpl

fun ProtoIdeaKpmFragmentCoordinates(coordinates: IdeaKpmFragmentCoordinates): ProtoIdeaKpmFragmentCoordinates {
    return protoIdeaKpmFragmentCoordinates {
        module = ProtoIdeaKpmModuleCoordinates(coordinates.module)
        fragmentName = coordinates.fragmentName
    }
}

fun IdeaKpmFragmentCoordinates(proto: ProtoIdeaKpmFragmentCoordinates): IdeaKpmFragmentCoordinates {
    return IdeaKpmFragmentCoordinatesImpl(
        module = IdeaKpmModuleCoordinates(proto.module),
        fragmentName = proto.fragmentName
    )
}

fun IdeaKpmFragmentCoordinates(data: ByteArray): IdeaKpmFragmentCoordinates {
    return IdeaKpmFragmentCoordinates(ProtoIdeaKpmFragmentCoordinates.parseFrom(data))
}

fun IdeaKpmFragmentCoordinates.toByteArray(): ByteArray {
    return ProtoIdeaKpmFragmentCoordinates(this).toByteArray()
}
