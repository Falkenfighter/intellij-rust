package org.rust.lang.structure

import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase
import org.rust.lang.core.psi.RustImplMethod

class RustImplMethodTreeElement(element: RustImplMethod) : PsiTreeElementBase<RustImplMethod>(element) {

    override fun getPresentableText(): String? {
        var text = element?.name

        val params = element?.paramList?.map { it.typeSum.text }?.joinToString()
        if (params != null)
            text += "($params)"

        val retType = element?.retType;
        if (retType != null)
            text += " ${retType.text}"

        return text;
    }

    override fun getChildrenBase() = arrayListOf<StructureViewTreeElement>()
}
