package org.rust.lang.highlight

import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import org.rust.lang.colorscheme.RustColors
import org.rust.lang.core.lexer.RustKeywordTokenType
import org.rust.lang.core.lexer.RustLexer
import org.rust.lang.core.lexer.RustTokenElementTypes.*

public class RustHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer() = RustLexer()

    override fun getTokenHighlights(tokenType: IElementType?) = pack(map(tokenType))

    private fun map(tokenType: IElementType?) = when (tokenType) {
        is RustKeywordTokenType -> RustColors.KEYWORD

        IDENTIFIER -> RustColors.IDENTIFIER

        LIFETIME -> RustColors.LIFETIME
        STATIC_LIFETIME -> RustColors.LIFETIME

        CHAR_LITERAL -> RustColors.CHAR
        BYTE_LITERAL -> RustColors.CHAR
        STRING_LITERAL -> RustColors.STRING
        BYTE_STRING_LITERAL -> RustColors.STRING
        INTEGER_LITERAL -> RustColors.NUMBER
        FLOAT_LITERAL -> RustColors.NUMBER

        BLOCK_COMMENT -> RustColors.BLOCK_COMMENT
        EOL_COMMENT -> RustColors.EOL_COMMENT

        INNER_DOC_COMMENT -> RustColors.DOC_COMMENT
        OUTER_DOC_COMMENT -> RustColors.DOC_COMMENT

        LPAREN, RPAREN -> RustColors.PARENTHESIS
        LBRACE, RBRACE -> RustColors.BRACES
        LBRACK, RBRACK -> RustColors.BRACKETS

        SEMICOLON -> RustColors.SEMICOLON
        DOT -> RustColors.DOT
        COMMA -> RustColors.COMMA

        else -> null
    }
}
