package org.rust.lang.core.resolve

import org.rust.lang.core.psi.RustDeclaringElement
import org.rust.lang.core.psi.RustNamedElement
import org.rust.lang.core.resolve.ref.RustQualifiedValue
import org.rust.lang.core.resolve.ref.RustReference

class RustCompletionEngine<T : RustQualifiedValue>(val ref: RustReference<T>) {
    fun complete(): Array<RustNamedElement> {
        val processor = CompletionScopeProcessor()

        ref.element.accept(RustScopeVisitor(processor))
        return processor.candidates.toTypedArray()
    }

    private class CompletionScopeProcessor : RustScopeProcessor() {
        val candidates = arrayListOf<RustNamedElement>()
        override fun processScope(declarations: Collection<RustDeclaringElement>): Search {
            declarations.flatMapTo(candidates, { it.getBoundElements() })
            return Search.GO_UP
        }
    }
}

