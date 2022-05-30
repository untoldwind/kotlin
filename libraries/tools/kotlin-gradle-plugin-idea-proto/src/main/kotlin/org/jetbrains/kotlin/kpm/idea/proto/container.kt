/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

@file:Suppress("FunctionName")

package org.jetbrains.kotlin.kpm.idea.proto

import com.google.protobuf.ByteString
import com.google.protobuf.InvalidProtocolBufferException
import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmProject
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializationContext
import java.io.InputStream
import java.io.OutputStream
import java.nio.ByteBuffer

fun IdeaKpmSerializationContext.IdeaKpmProject(data: ByteArray): IdeaKpmProject? {
    return IdeaKpmProject(data) { ProtoIdeaKpmContainer.parseFrom(data) }
}

fun IdeaKpmSerializationContext.IdeaKpmProject(data: ByteBuffer): IdeaKpmProject? {
    return IdeaKpmProject(data) { ProtoIdeaKpmContainer.parseFrom(data) }
}

fun IdeaKpmSerializationContext.IdeaKpmProject(data: ByteString): IdeaKpmProject? {
    return IdeaKpmProject(data) { ProtoIdeaKpmContainer.parseFrom(data) }
}

fun IdeaKpmSerializationContext.IdeaKpmProject(stream: InputStream): IdeaKpmProject? {
    return IdeaKpmProject(stream) { ProtoIdeaKpmContainer.parseFrom(stream) }
}

internal fun <T> IdeaKpmSerializationContext.IdeaKpmProject(data: T, proto: (T) -> ProtoIdeaKpmContainer): IdeaKpmProject? {
    val container = try {
        proto(data)
    } catch (e: InvalidProtocolBufferException) {
        logger.report("Failed to deserialize IdeaKpmProject", e)
        return null
    }

    return IdeaKpmProject(container)
}

fun IdeaKpmProject.toByteArray(context: IdeaKpmSerializationContext): ByteArray {
    return context.ProtoIdeaKpmContainer(this).toByteArray()
}

fun IdeaKpmProject.toByteString(context: IdeaKpmSerializationContext): ByteString {
    return context.ProtoIdeaKpmContainer(this).toByteString()
}

fun IdeaKpmProject.writeTo(context: IdeaKpmSerializationContext, output: OutputStream) {
    context.ProtoIdeaKpmContainer(this).writeDelimitedTo(output)
}

internal fun IdeaKpmSerializationContext.ProtoIdeaKpmContainer(project: IdeaKpmProject): ProtoIdeaKpmContainer {
    return protoIdeaKpmContainer {
        schemaVersionMajor = ProtoIdeaKpmSchema.versionMajor
        schemaVersionMinor = ProtoIdeaKpmSchema.versionMinor
        schemaVersionPatch = ProtoIdeaKpmSchema.versionPatch
        schemaInfos.addAll(ProtoIdeaKpmSchema.infos)
        this.project = ProtoIdeaKpmProject(project)
    }
}

internal fun IdeaKpmSerializationContext.IdeaKpmProject(proto: ProtoIdeaKpmContainer): IdeaKpmProject? {
    if (!proto.hasSchemaVersionMajor()) {
        logger.report("Missing 'schema_version_major'", Throwable())
        return null
    }

    if (!proto.hasSchemaVersionMinor()) {
        logger.report("Missing 'schema_version_minor'", Throwable())
        return null
    }

    if (proto.schemaVersionMajor > ProtoIdeaKpmSchema.versionMajor) {
        logger.report(
            "Incompatible IdeaKpmProto* version. Received major version ${proto.schemaVersionMajor}. " +
                    "Supported version ${ProtoIdeaKpmSchema.versionMajor}", Throwable()
        )


        val relevantInfos = proto.schemaInfosList.filter { info ->
            info.sinceSchemaVersionMajor > ProtoIdeaKpmSchema.versionMajor
        }

        relevantInfos.forEach { info ->
            logger.report(
                "Since: ${info.sinceSchemaVersionMajor}.${info.sinceSchemaVersionMinor}.${info.sinceSchemaVersionPatch}: ${info.message}"
            )
        }


        return null
    }

    return IdeaKpmProject(proto.project)
}
