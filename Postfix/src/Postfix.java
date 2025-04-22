import java.io.*;

/**
 * Parser class, parsing an infix expression and converting it into postfix.
 */
class Parser {
    static int lookahead;

    /**
     * Constructor initializes the lookahead variable by reading the first character of input.
     * @throws IOException if an I/O error occurs.
     */
    public Parser() throws IOException {
        lookahead = System.in.read();
    }

    /**
     * Parses an expression by processing a term followed by the rest of the expression.
     * @throws IOException if an I/O error occurs.
     */
    void expr() throws IOException {
        term();
        rest();
    }

    /**
     * Parses the rest of the expression, handling '+' and '-' operators.
     * Then processes the remaining terms.
     * @throws IOException if an I/O error occurs.
     */
    void rest() throws IOException {
        while(lookahead == '+' || lookahead == '-') {
            int operator = lookahead;
            match(operator);
            term();
            System.out.write(operator);
        }
    }

    /**
     * Parses a term, which is expected to be a single digit.
     * Writes the digit to the output in postfix order.
     * @throws IOException if an I/O error occurs.
     * @throws Error if the input is not a digit.
     */
    void term() throws IOException {
        if (Character.isDigit((char) lookahead)) {
            System.out.write((char) lookahead);
            match(lookahead);
        } else {
            throw new Error("syntax error");
        }
    }

    /**
     * Matches the current lookahead character with the expected character.
     * lookahead the next input character if they match.
     * @param t the expected character.
     * @throws IOException if an I/O error occurs.
     * @throws Error if the characters do not match.
     */
    void match(int t) throws IOException {
        if (lookahead == t) {
            lookahead = System.in.read();
        } else {
            throw new Error("syntax error");
        }
    }
}

/**
 * The Postfix class contains the main method to run the program.
 */
public class Postfix {
    public static void main(String[] args) throws IOException {
        System.out.println(
                "Input an infix expression and output its postfix notation:"
        );
        new Parser().expr();
        System.out.println("\nEnd of program.");
    }
}
