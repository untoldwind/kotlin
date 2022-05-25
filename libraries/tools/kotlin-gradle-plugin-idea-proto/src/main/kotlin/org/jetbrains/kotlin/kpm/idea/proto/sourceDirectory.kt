/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmSourceDirectory
import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmSourceDirectoryImpl
import java.io.File

/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */


fun ProtoIdeaKpmSourceDirectory(sourceDirectory: IdeaKpmSourceDirectory): ProtoIdeaKpmSourceDirectory {
    return protoIdeaKpmSourceDirectory {
        absolutePath = sourceDirectory.file.absolutePath
    }
}

fun IdeaKpmSourceDirectory(proto: ProtoIdeaKpmSourceDirectory): IdeaKpmSourceDirectory {
    return IdeaKpmSourceDirectoryImpl(
        file = File(proto.absolutePath)
    )
}

fun IdeaKpmSourceDirectory(data: ByteArray): IdeaKpmSourceDirectory {
    return IdeaKpmSourceDirectory(ProtoIdeaKpmSourceDirectory.parseFrom(data))
}

fun IdeaKpmSourceDirectory.toByteArray(): ByteArray {
    return ProtoIdeaKpmSourceDirectory(this).toByteArray()
}
