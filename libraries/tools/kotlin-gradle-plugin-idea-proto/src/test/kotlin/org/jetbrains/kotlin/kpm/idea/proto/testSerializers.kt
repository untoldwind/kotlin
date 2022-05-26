/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializer
import java.nio.ByteBuffer

object StringSerializer : IdeaKpmSerializer<String> {
    override fun serialize(value: String): ByteArray = value.encodeToByteArray()
    override fun deserialize(data: ByteArray): IdeaKpmSerializer.Deserialized<String> =
        IdeaKpmSerializer.Deserialized.Value(data.decodeToString())
}

object IntSerializer : IdeaKpmSerializer<Int> {
    override fun serialize(value: Int): ByteArray {
        return ByteBuffer.allocate(Int.SIZE_BYTES).putInt(value).array()
    }

    override fun deserialize(data: ByteArray): IdeaKpmSerializer.Deserialized<Int> {
        return IdeaKpmSerializer.Deserialized.Value(ByteBuffer.wrap(data).int)
    }
}
