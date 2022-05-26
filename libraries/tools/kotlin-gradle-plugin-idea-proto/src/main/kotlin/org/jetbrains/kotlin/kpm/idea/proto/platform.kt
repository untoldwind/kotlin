/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.kpm.idea.proto

import org.jetbrains.kotlin.gradle.kpm.idea.*
import org.jetbrains.kotlin.gradle.kpm.idea.serialize.IdeaKpmSerializationContext

fun IdeaKpmSerializationContext.ProtoIdeaKpmPlatform(platform: IdeaKpmPlatform): ProtoIdeaKpmPlatform {
    return protoIdeaKpmPlatform {
        when (platform) {
            is IdeaKpmJsPlatformImpl -> js = ProtoIdeaKpmJsPlatform(platform)
            is IdeaKpmJvmPlatformImpl -> jvm = ProtoIdeaKpmJvmPlatform(platform)
            is IdeaKpmNativePlatformImpl -> native = ProtoIdeaKpmNativePlatform(platform)
            is IdeaKpmUnknownPlatformImpl -> unknown = ProtoIdeaKpmUnknownPlatform(platform)
            is IdeaKpmWasmPlatformImpl -> wasm = ProtoIdeaKpmWasmPlatform(platform)
        }
    }
}

fun IdeaKpmSerializationContext.IdeaKpmPlatform(proto: ProtoIdeaKpmPlatform): IdeaKpmPlatform {
    return when (proto.platformCase) {
        ProtoIdeaKpmPlatform.PlatformCase.JVM -> IdeaKpmJvmPlatform(proto.jvm)
        ProtoIdeaKpmPlatform.PlatformCase.NATIVE -> IdeaKpmNativePlatform(proto.native)
        ProtoIdeaKpmPlatform.PlatformCase.JS -> IdeaKpmJsPlatform(proto.js)
        ProtoIdeaKpmPlatform.PlatformCase.WASM -> IdeaKpmWasmPlatform(proto.wasm)
        ProtoIdeaKpmPlatform.PlatformCase.UNKNOWN -> IdeaKpmUnknownPlatform(proto.unknown)
        ProtoIdeaKpmPlatform.PlatformCase.PLATFORM_NOT_SET, null -> IdeaKpmUnknownPlatformImpl()
    }
}

/* Jvm */

fun IdeaKpmSerializationContext.ProtoIdeaKpmJvmPlatform(platform: IdeaKpmJvmPlatform): ProtoIdeaKpmJvmPlatform {
    return protoIdeaKpmJvmPlatform {
        if (platform.extras.isNotEmpty()) extras = ProtoIdeaKpmExtras(platform.extras)
        jvmTarget = platform.jvmTarget
    }
}

fun IdeaKpmSerializationContext.IdeaKpmJvmPlatform(proto: ProtoIdeaKpmJvmPlatform): IdeaKpmJvmPlatform {
    return IdeaKpmJvmPlatformImpl(
        jvmTarget = proto.jvmTarget,
        extras = Extras(proto.extras)
    )
}

fun IdeaKpmSerializationContext.IdeaKpmJvmPlatform(data: ByteArray): IdeaKpmJvmPlatform {
    return IdeaKpmJvmPlatform(ProtoIdeaKpmJvmPlatform.parseFrom(data))
}

fun IdeaKpmJvmPlatform.toByteArray(context: IdeaKpmSerializationContext): ByteArray {
    return context.ProtoIdeaKpmJvmPlatform(this).toByteArray()
}

/* Native */

fun IdeaKpmSerializationContext.ProtoIdeaKpmNativePlatform(platform: IdeaKpmNativePlatform): ProtoIdeaKpmNativePlatform {
    return protoIdeaKpmNativePlatform {
        if (platform.extras.isNotEmpty()) extras = ProtoIdeaKpmExtras(platform.extras)
        konanTarget = platform.konanTarget
    }
}

fun IdeaKpmSerializationContext.IdeaKpmNativePlatform(proto: ProtoIdeaKpmNativePlatform): IdeaKpmNativePlatform {
    return IdeaKpmNativePlatformImpl(
        konanTarget = proto.konanTarget,
        extras = Extras(proto.extras)
    )
}

fun IdeaKpmSerializationContext.IdeaKpmNativePlatform(data: ByteArray): IdeaKpmNativePlatform {
    return IdeaKpmNativePlatform(ProtoIdeaKpmNativePlatform.parseFrom(data))
}

fun IdeaKpmNativePlatform.toByteArray(context: IdeaKpmSerializationContext): ByteArray {
    return context.ProtoIdeaKpmNativePlatform(this).toByteArray()
}

/* Js */

fun IdeaKpmSerializationContext.ProtoIdeaKpmJsPlatform(platform: IdeaKpmJsPlatform): ProtoIdeaKpmJsPlatform {
    return protoIdeaKpmJsPlatform {
        if (platform.extras.isNotEmpty()) extras = ProtoIdeaKpmExtras(platform.extras)
        isIr = platform.isIr
    }
}

fun IdeaKpmSerializationContext.IdeaKpmJsPlatform(proto: ProtoIdeaKpmJsPlatform): IdeaKpmJsPlatform {
    return IdeaKpmJsPlatformImpl(
        isIr = proto.isIr,
        extras = Extras(proto.extras)
    )
}

fun IdeaKpmSerializationContext.IdeaKpmJsPlatform(data: ByteArray): IdeaKpmJsPlatform {
    return IdeaKpmJsPlatform(ProtoIdeaKpmJsPlatform.parseFrom(data))
}

fun IdeaKpmJsPlatform.toByteArray(context: IdeaKpmSerializationContext): ByteArray {
    return context.ProtoIdeaKpmJsPlatform(this).toByteArray()
}

/* Wasm */

fun IdeaKpmSerializationContext.ProtoIdeaKpmWasmPlatform(platform: IdeaKpmWasmPlatform): ProtoIdeaKpmWasmPlatform {
    return protoIdeaKpmWasmPlatform {
        if (platform.extras.isNotEmpty()) extras = ProtoIdeaKpmExtras(platform.extras)
    }
}

fun IdeaKpmSerializationContext.IdeaKpmWasmPlatform(proto: ProtoIdeaKpmWasmPlatform): IdeaKpmWasmPlatform {
    return IdeaKpmWasmPlatformImpl(
        extras = Extras(proto.extras)
    )
}

fun IdeaKpmSerializationContext.IdeaKpmWasmPlatform(data: ByteArray): IdeaKpmWasmPlatform {
    return IdeaKpmWasmPlatform(ProtoIdeaKpmWasmPlatform.parseFrom(data))
}

fun IdeaKpmWasmPlatform.toByteArray(context: IdeaKpmSerializationContext): ByteArray {
    return context.ProtoIdeaKpmWasmPlatform(this).toByteArray()
}

/* Unknown */

fun IdeaKpmSerializationContext.ProtoIdeaKpmUnknownPlatform(platform: IdeaKpmUnknownPlatform): ProtoIdeaKpmUnknownPlatform {
    return protoIdeaKpmUnknownPlatform {
        if (platform.extras.isNotEmpty()) extras = ProtoIdeaKpmExtras(platform.extras)
    }
}

fun IdeaKpmSerializationContext.IdeaKpmUnknownPlatform(proto: ProtoIdeaKpmUnknownPlatform): IdeaKpmUnknownPlatform {
    return IdeaKpmUnknownPlatformImpl(
        extras = Extras(proto.extras)
    )
}

fun IdeaKpmSerializationContext.IdeaKpmUnknownPlatform(data: ByteArray): IdeaKpmUnknownPlatform {
    return IdeaKpmUnknownPlatform(ProtoIdeaKpmUnknownPlatform.parseFrom(data))
}

fun IdeaKpmUnknownPlatform.toByteArray(context: IdeaKpmSerializationContext): ByteArray {
    return context.ProtoIdeaKpmUnknownPlatform(this).toByteArray()
}
