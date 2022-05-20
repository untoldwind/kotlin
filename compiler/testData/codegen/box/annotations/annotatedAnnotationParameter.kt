// TARGET_BACKEND: JVM
// WITH_STDLIB
// FULL_JDK

import java.util.concurrent.Executors

fun box(): String {
    val pool = Executors.newCachedThreadPool()
    return "OK"
}
