package parser;

import parser.token.*;

class Scanner {
    private final String input;
    private int index;
    private ETokenType prevTokenType;

    public Scanner(String input) {
        this.input = input.toLowerCase();
        this.index = 0;
        this.prevTokenType = null;
    }

    /**
     * Get next token from input
     * @return Next token
     * @throws LexicalException Lexical Exception
     */
    public Token nextToken() throws LexicalException {
        skipWhiteSpace();

        // $
        if(end()) {
            prevTokenType = ETokenType.DOLLAR;
            return new Dollar();
        }

        char headChar = input.charAt(index);

        // decimal
        if(Character.isDigit(headChar)) {
            prevTokenType = ETokenType.DECIMAL;
            return scanDecimal();
        }

        // bool
        if(headChar == 't' || headChar == 'f') {
            prevTokenType = ETokenType.BOOL;
            return scanBool();
        }

        // function
        if(headChar == 's' || headChar == 'c' || headChar == 'm') {
            prevTokenType = ETokenType.FUNCTION;
            return scanFunction();
        }

        // operator
        return scanOperator();
    }

    /**
     * Scan token as decimal
     * @return Decimal token
     * @throws IllegalDecimalException illegal decimal exception
     */
    private Decimal scanDecimal() throws IllegalDecimalException {
        String tokenString = "";
        // integer part
        tokenString += scanDigit();

        // fraction part
        if(!end() && input.charAt(index) == '.') {
            index++;
            String fraction = scanDigit();
            // fraction part empty
            if(fraction.isEmpty()) {
                throw new IllegalDecimalException;
            }
            tokenString += '.' + fraction;
        }

        // exponent part
        if(!end() && input.charAt(index) == 'e') {
            index++;
            String exponent = "";
            char operator = '+';

            if(!end() && (input.charAt(index) == '+' || input.charAt(index) == '-')) {
                operator = input.charAt(index);
                index++;
            }

            exponent = scanDigit();
            // exponent part empty
            if(exponent.isEmpty()) {
                throw new IllegalDecimalException;
            }
            tokenString += 'e' + operator + exponent;
        }

        return new Decimal(ETokenType.DECIMAL, tokenString);
    }

    /**
     * Scan token as bool
     * @return Bool token
     * @throws IllegalIdentifierException illegal identifier exception
     */
    private Bool scanBool() throws IllegalIdentifierException {
        if(input.startsWith("true", index)) {
            index += 4;
            return new Bool(ETokenType.BOOL, "true");
        } else if(input.startsWith("false", index)) {
            index += 5;
            return new Bool(ETokenType.BOOL, "false");
        } else {
            throw new IllegalIdentifierException;
        }
    }

    /**
     * Scan token as function
     * @return Function token
     * @throws IllegalIdentifierException illegal identifier exception
     */
    private Function scanFunction() throws IllegalIdentifierException {
        if(input.startsWith("sin", index)) {
            index += 3;
            return new Function(ETokenType.FUNCTION, "sin");
        } else if(input.startsWith("cos", index)) {
            index += 3;
            return new Function(ETokenType.FUNCTION, "cos");
        } else if(input.startsWith("min", index)) {
            index += 3;
            return new Function(ETokenType.FUNCTION, "min");
        } else if(input.startsWith("max", index)) {
            index += 3;
            return new Function(ETokenType.FUNCTION, "max");
        } else {
            throw new IllegalIdentifierException;
        }
    }

    /**
     * Scan token as operator
     * @return Operator token
     * @throws IllegalSymbolException illegal symbol exception
     */
    private Operator scanOperator() throws IllegalSymbolException {
        char opChar = input.charAt(index);
        Operator op = null;
        if(input.startsWith(">=", index)) {
            index += 2;
            op = new Operator(ETokenType.RELATION, ">=");
        }
        else if(input.startsWith("<=", index)) {
            index += 2;
            op = new Operator(ETokenType.RELATION, "<=");
        }
        else if(input.startsWith("<>", index)) {
            index += 2;
            op = new Operator(ETokenType.RELATION, "<>");
        } else {
            index++;
            op = switch (opChar) {
                case '+' -> new Operator(ETokenType.ADD_SUB, "+");
                case '-' -> {
                    if(prevTokenType == ETokenType.DECIMAL || prevTokenType == ETokenType.RPAREN) {
                        yield new Operator(ETokenType.ADD_SUB, "-");
                    } else {
                        yield new Operator(ETokenType.NEG, "-");
                    }
                }
                case '*' -> new Operator(ETokenType.MUL_DIV, "*");
                case '/' -> new Operator(ETokenType.MUL_DIV, "/");
                case '^' -> new Operator(ETokenType.POWER, "^");
                case '(' -> new Operator(ETokenType.LPAREN, "(");
                case ')' -> new Operator(ETokenType.RPAREN, ")");
                case ',' -> new Operator(ETokenType.COMMA, ",");
                case '?' -> new Operator(ETokenType.QUESTION, "?");
                case ':' -> new Operator(ETokenType.COLON, ":");
                case '!' -> new Operator(ETokenType.NOT, " ");
                case '&' -> new Operator(ETokenType.AND, "&");
                case '|' -> new Operator(ETokenType.OR, "|");
                case '>' -> new Operator(ETokenType.RELATION, ">");
                case '<' -> new Operator(ETokenType.RELATION, "<");
                case '=' -> new Operator(ETokenType.RELATION, "=");
                default -> throw new IllegalSymbolException;
            };
        }
        prevTokenType = op.getTokenType();
        return op;
    }

    /**
     * Get digits from index position of input
     * @return digit string
     */
    private String scanDigit() {
        StringBuilder digit = new StringBuilder();
        while(!end() && Character.isDigit(input.charAt(index))) {
            digit.append(input.charAt(index));
            index++;
        }
        return digit.toString();
    }

    /**
     * Skip white space between tokens
     */
    private void skipWhiteSpace() {
        if(!end() && input.charAt(index) == ' ') {
            index++;
        }
    }

    /**
     * Index at end of input
     * @return true if end
     */
    private boolean end() {
        return index >= input.length();
    }
}
