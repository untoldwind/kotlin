/*
 * Copyright 2010-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jetbrains.gradle.plugins.tools.lib
import org.jetbrains.gradle.plugins.tools.solib
import org.jetbrains.kotlin.*
import org.jetbrains.kotlin.konan.target.*
import org.jetbrains.kotlin.konan.target.ClangArgs
import org.jetbrains.kotlin.konan.target.Family.*
import org.jetbrains.kotlin.konan.target.HostManager.Companion.hostIsMac

plugins {
    `kotlin`
    `native-interop-plugin`
    `native`
}


val libclangextProject = project(":kotlin-native:libclangext")
val libclangextTask = libclangextProject.path + ":build"
val libclangextDir = libclangextProject.buildDir
val libclangextIsEnabled = libclangextProject.findProperty("isEnabled")!! as Boolean
val llvmDir = project.findProperty("llvmDir")


val libclang =
    if (HostManager.hostIsMingw) {
        "lib/libclang.lib"
    } else {
        "lib/${System.mapLibraryName("clang")}"
    }

val cflags = mutableListOf( "-I$llvmDir/include",
        "-I${project(":kotlin-native:libclangext").projectDir.absolutePath}/src/main/include",
                            *platformManager.hostPlatform.clang.hostCompilerArgsForJni)

val ldflags = mutableListOf("$llvmDir/$libclang", "-L${libclangextDir.absolutePath}", "-lclangext")

if (libclangextIsEnabled) {
    assert(HostManager.hostIsMac)
    ldflags.addAll(listOf("-Wl,--no-demangle", "-Wl,-search_paths_first", "-Wl,-headerpad_max_install_names", "-Wl,-U,_futimens",
                        // Let some symbols be undefined to avoid linking unnecessary parts
                       "-Wl,-U,__ZN4llvm7remarks11parseFormatENS_9StringRefE",
                       "-Wl,-U,__ZN4llvm7remarks22createRemarkSerializerENS0_6FormatENS0_14SerializerModeERNS_11raw_ostreamE",
                       "-Wl,-U,__ZN4llvm7remarks14YAMLSerializerC1ERNS_11raw_ostreamENS0_14UseStringTableE",
                       "-Wl,-U,__ZN4llvm3omp22getOpenMPDirectiveNameENS0_9DirectiveE",
                       "-Wl,-U,__ZN4llvm7remarks14RemarkStreamer13matchesFilterENS_9StringRefE",
                       "-Wl,-U,__ZN4llvm7remarks14RemarkStreamer9setFilterENS_9StringRefE",
                       "-Wl,-U,__ZN4llvm7remarks14RemarkStreamerC1ENSt3__110unique_ptrINS0_16RemarkSerializerENS2_14default_deleteIS4_EEEENS_8OptionalINS_9StringRefEEE",
                        "-Wl,-U,__ZN4llvm3omp19getOpenMPClauseNameENS0_6ClauseE",
            "-Wl,-U,__ZN4llvm3omp28getOpenMPContextTraitSetNameENS0_8TraitSetE",
            "-Wl,-U,__ZN4llvm3omp31isValidTraitSelectorForTraitSetENS0_13TraitSelectorENS0_8TraitSetERbS3_",
            "-Wl,-U,__ZN4llvm3omp31isValidTraitSelectorForTraitSetENS0_13TraitSelectorENS0_8TraitSetERbS3_",
            "-Wl,-U,__ZN4llvm3omp33getOpenMPContextTraitPropertyNameENS0_13TraitPropertyE",
            "-Wl,-U,__ZN4llvm3omp33getOpenMPContextTraitSelectorNameENS0_13TraitSelectorE",
            "-Wl,-U,__ZN4llvm3omp35getOpenMPContextTraitSetForPropertyENS0_13TraitPropertyE",
            "-Wl,-U,__ZN4llvm3omp33getOpenMPContextTraitPropertyKindENS0_8TraitSetENS_9StringRefE"

    ))

    val llvmLibs = listOf(
            "clangAST", "clangASTMatchers", "clangAnalysis", "clangBasic", "clangDriver", "clangEdit",
            "clangFrontend", "clangFrontendTool", "clangLex", "clangParse", "clangSema", "clangEdit",
            "clangRewrite", "clangRewriteFrontend", "clangStaticAnalyzerFrontend",
            "clangStaticAnalyzerCheckers", "clangStaticAnalyzerCore", "clangSerialization",
            "clangToolingCore",
            "clangTooling", "clangFormat", "LLVMTarget", "LLVMMC", "LLVMLinker", "LLVMTransformUtils",
            "LLVMBitWriter", "LLVMBitReader", "LLVMAnalysis", "LLVMProfileData", "LLVMCore",
            "LLVMSupport", "LLVMBinaryFormat", "LLVMDemangle"
    ).map { "$llvmDir/lib/lib${it}.a" }

    ldflags.addAll(llvmLibs)
    ldflags.addAll(listOf("-lpthread", "-lz", "-lm", "-lcurses"))
}

val solib = when{
    HostManager.hostIsMingw -> "dll"
    HostManager.hostIsMac -> "dylib"
    else -> "so"
}
val lib = if (HostManager.hostIsMingw) "lib" else "a"


native {
    val obj = if (HostManager.hostIsMingw) "obj" else "o"
    val cxxflags = listOf("-std=c++11", *cflags.toTypedArray())
    suffixes {
        (".c" to ".$obj") {
            tool(*platformManager.hostPlatform.clang.clangCForJni("").toTypedArray())
            flags(*cflags.toTypedArray(),
                  "-c", "-o", ruleOut(), ruleInFirst())
        }
        (".cpp" to ".$obj") {
            tool(*platformManager.hostPlatform.clang.clangCXXForJni("").toTypedArray())
            flags(*cxxflags.toTypedArray(), "-c", "-o", ruleOut(), ruleInFirst())
        }

    }
    sourceSet {
        "main-c" {
            dir("prebuilt/nativeInteropStubs/c")
        }
        "main-cpp" {
            dir("src/nativeInteropStubs/cpp")
        }
    }
    val objSet = arrayOf(sourceSets["main-c"]!!.transform(".c" to ".$obj"),
                         sourceSets["main-cpp"]!!.transform(".cpp" to ".$obj"))

    target(solib("clangstubs"), *objSet) {
        tool(*platformManager.hostPlatform.clang.clangCXXForJni("").toTypedArray())
        flags(
            "-shared",
            "-o", ruleOut(), *ruleInAll(),
            *ldflags.toTypedArray())
    }
}

tasks.named(solib("clangstubs")).configure {
    dependsOn(":kotlin-native:libclangext:${lib("clangext")}")
}

sourceSets {
    "main" {
        java {
            srcDirs("prebuilt/nativeInteropStubs/kotlin")
        }
        kotlin{
            target {

            }
        }
    }
}

dependencies {
    compile(project(":kotlin-stdlib"))
    compile(project(":kotlin-native:Interop:Runtime"))
}

val nativelibs = project.tasks.create<Copy>("nativelibs") {
    val clangstubsSolib = solib("clangstubs")
    dependsOn(clangstubsSolib)

    from("$buildDir/$clangstubsSolib")
    into("$buildDir/nativelibs/")
}

kotlinNativeInterop {
    this.create("clang") {
        defFile("clang.def")
        compilerOpts(cflags)
        linkerOpts = ldflags
        genTask.dependsOn(libclangextTask)
        genTask.inputs.dir(libclangextDir)
    }
}
val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        allWarningsAsErrors = true
        freeCompilerArgs = listOf("-Xskip-prerelease-check")
    }
}

tasks.matching { it.name == "linkClangstubsSharedLibrary" }.all {
    dependsOn(libclangextTask)
    inputs.dir(libclangextDir)
}

// Please note that list of headers should be fixed manually.
// See KT-46231 for details.
tasks.create("updatePrebuilt") {
    dependsOn("genClangInteropStubs")

    doLast {
        copy {
            from("$buildDir/nativeInteropStubs/clang/kotlin") {
                include("clang/clang.kt")
            }
            into("prebuilt/nativeInteropStubs/kotlin")
        }

        copy {
            from("$buildDir/interopTemp") {
                include("clangstubs.c")
            }
            into("prebuilt/nativeInteropStubs/c")
        }
    }
}
