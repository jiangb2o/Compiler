package parser.token;

/**
 * Define tokens type
 */
public enum ETokenType {
    DECIMAL,     // decimal
    BOOL,       // true, false
    ADD_SUB,    // + -
    MUL_DIV,    // * /
    POWER,      // ^
    NEG,        // unary operator -
    LPAREN,     // (
    RPAREN,     // )
    COMMA,      // ,
    QUESTION,   // ?
    COLON,      // :
    FUNCTION,   // min max sin cos
    NOT,        // !
    AND,        // &
    OR,         // |
    RELATION,   // > >= < <= <> =
    DOLLAR,     // $
}
