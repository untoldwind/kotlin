/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmModule
import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmModuleImpl
import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmVariant
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializationContext

fun IdeaKpmSerializationContext.ProtoIdeaKpmModule(module: IdeaKpmModule): ProtoIdeaKpmModule {
    return protoIdeaKpmModule {
        coordinates = ProtoIdeaKpmModuleCoordinates(module.coordinates)
        fragments.addAll(module.fragments.filter { it !is IdeaKpmVariant }.map { ProtoIdeaKpmFragment(it) })
        variants.addAll(module.fragments.filterIsInstance<IdeaKpmVariant>().map { ProtoIdeaKpmVariant(it) })
    }
}

fun IdeaKpmSerializationContext.IdeaKpmModule(proto: ProtoIdeaKpmModule): IdeaKpmModule {
    return IdeaKpmModuleImpl(
        coordinates = IdeaKpmModuleCoordinates(proto.coordinates),
        fragments = proto.fragmentsList.map { IdeaKpmFragment(it) } + proto.variantsList.map { IdeaKpmVariant(it) }
    )
}

fun IdeaKpmSerializationContext.IdeaKpmModule(data: ByteArray): IdeaKpmModule {
    return IdeaKpmModule(ProtoIdeaKpmModule.parseFrom(data))
}

fun IdeaKpmModule.toByteArray(context: IdeaKpmSerializationContext): ByteArray {
    return context.ProtoIdeaKpmModule(this).toByteArray()
}
