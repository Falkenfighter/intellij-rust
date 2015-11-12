package org.rust.lang.core.psi.util

import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import org.rust.lang.core.psi.RustCompositeElement
import org.rust.lang.core.psi.RustCrateItem
import org.rust.lang.core.psi.RustModItem


sealed class ModuleFile {
    class NotFound : ModuleFile()
    class Ok(val mod: RustCrateItem) : ModuleFile()
    class Ambigious(val paths: Collection<PsiFile>) : ModuleFile()
}

private val RustModItem.modDir: PsiDirectory?
    get() {
        val parent = parentModInFile ?: return containingFile.parent

        // FIXME(matklad): mod name should be not null
        val name = this.name ?: return null

        return parent.modDir?.findSubdirectory(name)
    }


val RustCompositeElement.parentModInFile: RustModItem?
    get() {
        var current = parent
        while(current != null) {
            when (current) {
                is RustModItem -> return current
                else -> current = current.parent
            }
        }
        return null
    }

private val RustModItem.isCrateRoot: Boolean
    get() = parentModInFile == null &&
        (containingFile.name == "main.rs" || containingFile.name == "lib.rs")

private val RustModItem.ownsDirectory: Boolean
    get() = parentModInFile != null ||
        containingFile.name == "mod.rs" ||
        isCrateRoot


public fun RustModItem.submodulePath(modName: String): ModuleFile {
    if (!ownsDirectory) {
        return ModuleFile.NotFound()
    }

    val dir = modDir
    val dirMod = dir?.findSubdirectory(modName)?.findFile("mod.rs")
    val fileMod = dir?.findFile(modName + ".rs")

    if (dirMod == null && fileMod == null) {
        return ModuleFile.NotFound()
    }
    if (dirMod != null && fileMod != null) {
        return ModuleFile.Ambigious(listOf(dirMod, fileMod))
    }

    val file = dirMod ?: fileMod!!

    return ModuleFile.Ok(file.firstChild as RustCrateItem)
}


