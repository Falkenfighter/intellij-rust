package org.rust.lang.documentation

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.psi.PsiElement
import org.rust.lang.core.psi.RustFnItem
import org.rust.lang.core.psi.RustPatBinding
import org.rust.lang.core.psi.util.isMut

class RustDocumentationProvider : AbstractDocumentationProvider() {

    override fun getQuickNavigateInfo(element: PsiElement?, originalElement: PsiElement?) = when (element) {
        is RustPatBinding -> getQuickNavigateInfo(element)
        is RustFnItem     -> getQuickNavigateInfo(element)
        else              -> null
    }

    private fun getQuickNavigateInfo(element: RustPatBinding): String {
        val location = getLocationString(element)
        val bindingMode = if (element.isMut) "mut " else ""

        return "let $bindingMode<b>${element.identifier.text}</b>$location"
    }

    private fun getQuickNavigateInfo(element: RustFnItem): String {
        val text = element.text

        val identifier = element.identifier
        val identStart = identifier.startOffsetInParent
        val identEnd = identStart + identifier.textLength
        val signatureLength = element.block?.startOffsetInParent ?: element.textLength

        val beforeIdent = text.subSequence(0, identStart)
        val identText = text.subSequence(identStart, identEnd)
        val afterIdent = text.subSequence(identEnd, signatureLength).toString().trimEnd()

        val location = getLocationString(element)
        return "$beforeIdent<b>$identText</b>$afterIdent$location"
    }

    private fun getLocationString(element: PsiElement?): String {
        return element?.containingFile?.let { " [${it.name}]" }.orEmpty()
    }
}
