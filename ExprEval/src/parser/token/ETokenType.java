package parser.token;

/**
 * Define tokens type
 */
public enum ETokenType {
    DECIMAL,     // decimal
    BOOL,       // true, false
    LPAREN,     // (
    RPAREN,     // )
    FUNCTION,   // min max sin cos
    NEG,        // unary operator -
    POWER,      // ^
    MUL_DIV,    // * /
    ADD_SUB,    // + -
    RELATION,   // > >= < <= <> =
    NOT,        // !
    AND,        // &
    OR,         // |
    QUESTION,   // ?
    COLON,      // :
    COMMA,      // ,
    DOLLAR,     // $
}
