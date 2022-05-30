/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmExtrasSerializer
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializationContext
import java.nio.ByteBuffer

object StringExtrasSerializer : IdeaKpmExtrasSerializer<String> {
    override fun serialize(context: IdeaKpmSerializationContext, value: String): ByteArray = value.encodeToByteArray()
    override fun deserialize(context: IdeaKpmSerializationContext, data: ByteArray) = data.decodeToString()
}

object IntExtrasSerializer : IdeaKpmExtrasSerializer<Int> {
    override fun serialize(context: IdeaKpmSerializationContext, value: Int): ByteArray {
        return ByteBuffer.allocate(Int.SIZE_BYTES).putInt(value).array()
    }

    override fun deserialize(context: IdeaKpmSerializationContext, data: ByteArray): Int {
        return ByteBuffer.wrap(data).int
    }
}
