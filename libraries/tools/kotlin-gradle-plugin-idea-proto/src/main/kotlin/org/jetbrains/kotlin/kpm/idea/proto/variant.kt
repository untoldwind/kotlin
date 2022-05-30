/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmVariant
import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmVariantImpl
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializationContext


fun IdeaKpmSerializationContext.ProtoIdeaKpmVariant(variant: IdeaKpmVariant): ProtoIdeaKpmVariant {
    return protoIdeaKpmVariant {
        fragment = ProtoIdeaKpmFragment(variant)
        variantAttributes.putAll(variant.variantAttributes)
        platform = ProtoIdeaKpmPlatform(variant.platform)
        compilationOutput = ProtoIdeaKpmCompilationOutput(variant.compilationOutputs)
    }
}

fun IdeaKpmSerializationContext.IdeaKpmVariant(proto: ProtoIdeaKpmVariant): IdeaKpmVariant {
    return IdeaKpmVariantImpl(
        fragment = IdeaKpmFragment(proto.fragment),
        platform = IdeaKpmPlatform(proto.platform),
        variantAttributes = proto.variantAttributesMap.toMap(),
        compilationOutputs = IdeaKpmCompilationOutput(proto.compilationOutput)
    )
}

fun IdeaKpmSerializationContext.IdeaKpmVariant(data: ByteArray): IdeaKpmVariant {
    return IdeaKpmVariant(ProtoIdeaKpmVariant.parseFrom(data))
}

fun IdeaKpmVariant.toByteArray(context: IdeaKpmSerializationContext): ByteArray {
    return context.ProtoIdeaKpmVariant(this).toByteArray()
}
