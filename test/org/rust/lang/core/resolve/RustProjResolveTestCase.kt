package org.rust.lang.core.resolve

import org.assertj.core.api.Assertions.assertThat
import org.rust.lang.RustTestCase
import org.rust.lang.core.resolve.ref.RustReference


class RustProjResolveTestCase : RustTestCase() {
    override fun getTestDataPath() = "testData/org/rust/lang/core/resolve/fixtures"

    fun testChildMod() {
        myFixture.configureByFiles("child_mod/main.rs", "child_mod/child.rs");

        val usage = file.findReferenceAt(myFixture.caretOffset)!! as RustReference
        val declaration = usage.resolve()

        assertThat(declaration).isNotNull()
    }

}
