inline fun bar() {
    val foo: Foo? = null
    check(foo == null)
}

inline fun baz() {
    check(Foo().toString() != "Bar")
}
