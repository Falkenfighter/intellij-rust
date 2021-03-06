package org.rust.lang.core.resolve.ref

import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import org.rust.lang.core.lexer.RustTokenElementTypes
import org.rust.lang.core.psi.RustQualifiedReferenceElement
import org.rust.lang.core.resolve.RustResolveEngine


internal class RustReferenceImpl<T : RustQualifiedReferenceElement>(element: T,
                                                                   range: TextRange = element.textRange,
                                                                   soft: Boolean = false)
    : PsiReferenceBase<T>(element, range, soft)
    , RustReference {

    override fun resolve(): PsiElement? =
            RustResolveEngine().resolve(element).element

    override fun getVariants(): Array<out Any> = EMPTY_ARRAY

    override fun isReferenceTo(element: PsiElement): Boolean {
        val target = resolve()
        return element.manager.areElementsEquivalent(target, element)
    }

    override fun getCanonicalText(): String =
        element.let { qualRef ->
            var qual = qualRef.getQualifier()
                            ?.let { qual -> qual.reference?.canonicalText }
                             .orEmpty()

            if (qual.isNotEmpty())
                qual += RustTokenElementTypes.COLONCOLON.s;

            qual + qualRef.name
        }

    protected override fun calculateDefaultRangeInElement(): TextRange? =
            TextRange.from(0, myElement.textLength)

    override fun getRangeInElement(): TextRange? =
        element.getSeparator().let {
            sep ->
            when (sep) {
                null -> TextRange.from(0, element.textLength)
                else -> TextRange(sep.startOffsetInParent + sep.textLength, element.textLength)
            }
        }

    override fun bindToElement(element: PsiElement): PsiElement? {
        throw UnsupportedOperationException()
    }
}

