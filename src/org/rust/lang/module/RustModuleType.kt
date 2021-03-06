package org.rust.lang.module

import com.intellij.openapi.module.ModuleType
import org.rust.lang.icons.RustIcons
import javax.swing.Icon


public class RustModuleType() : ModuleType<RustModuleBuilder>(RustModuleType.RUST_MODULE) {

    override fun createModuleBuilder(): RustModuleBuilder {
        return RustModuleBuilder()
    }

    override fun getName(): String {
        return "Rust Module"
    }

    override fun getDescription(): String {
        return "Rust Module"
    }

    override fun getBigIcon(): Icon {
        return RustIcons.FILE_BIG
    }

    override fun getNodeIcon(isOpened: Boolean): Icon {
        return RustIcons.FILE
    }

    companion object {
        public val RUST_MODULE: String = "RUST_MODULE"
        public val INSTANCE: RustModuleType = RustModuleType()
    }
}
