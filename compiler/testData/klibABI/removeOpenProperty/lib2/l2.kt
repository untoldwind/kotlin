class B : A {
    override val foo: String get() = "OK" // does not call super
}
