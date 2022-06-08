fun box(): String {
    try {
        bar()
        return "FAIL1"
    } catch (e: Throwable) {
        if (!e.isUnlinkedTypeOfExpression()) return "FAIL2"
    }

    try {
        baz()
        return "FAIL3"
    } catch(e: Throwable) {
        if (!e.isUnlinkedSymbolLinkageError("/Foo.<init>")) return "FAIL4"
    }

    return "OK"
}

private fun Throwable.isUnlinkedTypeOfExpression(): Boolean =
    this::class.simpleName == "IrLinkageError" && message == "Unlinked type of IR expression"

private fun Throwable.isUnlinkedSymbolLinkageError(symbolName: String): Boolean =
    this::class.simpleName == "IrLinkageError" && message?.startsWith("Unlinked IR symbol $symbolName|") == true
