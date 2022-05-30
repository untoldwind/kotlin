/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import com.google.protobuf.InvalidProtocolBufferException
import org.jetbrains.kotlin.gradle.kpm.idea.IdeaKpmProject
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializationContext

fun IdeaKpmSerializationContext.ProtoIdeaKpmContainer(project: IdeaKpmProject): ProtoIdeaKpmContainer {
    return protoIdeaKpmContainer {
        schemaVersionMajor = ProtoIdeaKpmSchema.versionMajor
        schemaVersionMinor = ProtoIdeaKpmSchema.versionMinor
        schemaVersionPatch = ProtoIdeaKpmSchema.versionPatch
        schemaInfos.addAll(ProtoIdeaKpmSchema.infos)
        this.project = ProtoIdeaKpmProject(project)
    }
}

fun IdeaKpmSerializationContext.IdeaKpmProject(proto: ProtoIdeaKpmContainer): IdeaKpmProject? {
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

fun IdeaKpmSerializationContext.IdeaKpmProject(data: ByteArray): IdeaKpmProject? {
    val container = try {
        ProtoIdeaKpmContainer.parseFrom(data)
    } catch (e: InvalidProtocolBufferException) {
        logger.report("Failed to deserialize IdeaKpmProject", e)
        return null
    }

    return IdeaKpmProject(container)
}

fun IdeaKpmProject.toByteArray(context: IdeaKpmSerializationContext): ByteArray {
    return context.ProtoIdeaKpmContainer(this).toByteArray()
}
