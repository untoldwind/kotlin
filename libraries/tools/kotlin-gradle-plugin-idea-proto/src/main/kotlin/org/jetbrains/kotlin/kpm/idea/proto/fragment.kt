/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmFragment
import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmFragmentImpl
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializationContext

fun IdeaKpmSerializationContext.ProtoIdeaKpmFragment(fragment: IdeaKpmFragment): ProtoIdeaKpmFragment {
    return protoIdeaKpmFragment {
        coordinates = ProtoIdeaKpmFragmentCoordinates(fragment.coordinates)
        platforms.addAll(fragment.platforms.map { ProtoIdeaKpmPlatform(it) })
        languageSettings = ProtoIdeaKpmLanguageSettings(fragment.languageSettings)
        dependencies.addAll(fragment.dependencies.map { ProtoIdeaKpmDependency(it) })
        sourceDirectories.addAll(fragment.sourceDirectories.map { ProtoIdeaKpmSourceDirectory(it) })
        extras = ProtoIdeaKpmExtras(fragment.extras)
    }
}

fun IdeaKpmSerializationContext.IdeaKpmFragment(proto: ProtoIdeaKpmFragment): IdeaKpmFragment {
    return IdeaKpmFragmentImpl(
        coordinates = IdeaKpmFragmentCoordinates(proto.coordinates),
        platforms = proto.platformsList.map { IdeaKpmPlatform(it) }.toSet(),
        languageSettings = IdeaKpmLanguageSettings(proto.languageSettings),
        dependencies = proto.dependenciesList.mapNotNull { IdeaKpmDependency(it) },
        sourceDirectories = proto.sourceDirectoriesList.mapNotNull { IdeaKpmSourceDirectory(it) },
        extras = Extras(proto.extras)
    )
}

fun IdeaKpmSerializationContext.IdeaKpmFragment(data: ByteArray): IdeaKpmFragment {
    return IdeaKpmFragment(ProtoIdeaKpmFragment.parseFrom(data))
}

fun IdeaKpmFragment.toByteArray(context: IdeaKpmSerializationContext): ByteArray {
    return context.ProtoIdeaKpmFragment(this).toByteArray()
}
