/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

@file:Suppress("FunctionName")

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.*
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializationContext
import org.jetbrains.kotlin.kpm.idea.proto.ProtoIdeaKpmDependency.DependencyCase

fun IdeaKpmSerializationContext.ProtoIdeaKpmDependency(dependency: IdeaKpmDependency): ProtoIdeaKpmDependency {
    return protoIdeaKpmDependency {
        when (dependency) {
            is IdeaKpmResolvedBinaryDependency -> resolvedBinaryDependency = ProtoIdeaKpmResolvedBinaryDependency(dependency)
            is IdeaKpmUnresolvedBinaryDependency -> unresolvedBinaryDependency = ProtoIdeaKpmUnresolvedBinaryDependency(dependency)
            is IdeaKpmFragmentDependency -> fragmentDependency = ProtoIdeaKpmFragmentDependency(dependency)
        }
    }
}

fun IdeaKpmSerializationContext.IdeaKpmDependency(proto: ProtoIdeaKpmDependency): IdeaKpmDependency? {
    return when (proto.dependencyCase) {
        DependencyCase.UNRESOLVED_BINARY_DEPENDENCY -> IdeaKpmUnresolvedBinaryDependency(proto.unresolvedBinaryDependency)
        DependencyCase.RESOLVED_BINARY_DEPENDENCY -> IdeaKpmResolvedBinaryDependency(proto.resolvedBinaryDependency)
        DependencyCase.FRAGMENT_DEPENDENCY -> IdeaKpmFragmentDependency(proto.fragmentDependency)
        DependencyCase.DEPENDENCY_NOT_SET, null -> null
    }
}
