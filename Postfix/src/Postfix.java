import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser class, parsing an infix expression and converting it into postfix.
 */
class Parser {
    //static int lookahead;
    int lookahead;
    //index of current lookahead
    int index;
    ArrayList<String> errors;
    StringBuffer infix;
    StringBuffer answer;

    /**
     * Constructor initializes the lookahead variable by reading the first character of input.
     * @throws IOException if an I/O error occurs.
     */
    public Parser() throws IOException {
        index = 0;
        infix = new StringBuffer();
        answer = new StringBuffer();
        errors = new ArrayList<>();
        lookahead = System.in.read();
    }

    /**
     * Parses an expression by processing a term followed by the rest of the expression.
     * Print errors
     * @throws IOException if an I/O error occurs.
     */
    void expr() throws IOException {
        term();
        rest();
        if(!errors.isEmpty()) {
            System.out.println();
            for(String e : errors) {
                System.out.println(e);
            }
            System.out.println("[] is operand");
            System.out.println("() is operator");
            System.out.println("Expect expression: " + infix);
        }
        System.out.println("Postfix: " + answer);
    }

    /**
     * Parses the rest of the expression, handling '+' and '-' operators.
     * Then processes the remaining terms.
     * handle errors
     * @throws IOException if an I/O error occurs.
     */
    void rest() throws IOException {
        while(lookahead != '\n') {
            if(lookahead == '+' || lookahead == '-') {
                int operator = lookahead;
                match(operator);
                infix.append((char)operator);
                term();
                answer.append((char)operator);
            } else {
                if(Character.isDigit((char) lookahead)) {
                    errors.add(String.format("Syntax error at index %d: Lack operator", index));
                    infix.append("()");
                    term();
                    answer.append("()");
                    continue;
                } else if(lookahead == ' ') {
                    errors.add(String.format("Lexical error at index %d: Space in expression", index));
                } else {
                    errors.add(String.format("Lexical error at index %d: Invalid operator", index));
                }
                match(lookahead);
            }
        }
    }

    /**
     * Parses a term, which is expected to be a single digit.
     * Writes the digit to the output in postfix order.
     * handle errors
     * @throws IOException if an I/O error occurs.
     * @throws Error if the input is not a digit.
     */
    void term() throws IOException {
        while (!Character.isDigit((char) lookahead)) {
            if(lookahead == '+' || lookahead == '-') {
                infix.append("[]");
                answer.append("[]");
                errors.add(String.format("Syntax error at index %d: Lack left operand", index));
                return;
            } else if(lookahead == '\n') {
                infix.append("[]");
                answer.append("[]");
                errors.add(String.format("Syntax error at index %d: Lack right operand", index));
                return;
            }
            else if(lookahead == ' ') {
                errors.add(String.format("Lexical error at index %d: Space in expression", index));
            }  else {
                errors.add(String.format("Lexical error at index %d: Invalid operand", index));
            }
            match(lookahead);
        }
        infix.append((char)lookahead);
        answer.append((char)lookahead);
        match(lookahead);
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
            index++;
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
    /**
     * main method
     * @param args run args
     * @throws IOException if an I/O error occurs
     */
    public static void main(String[] args) throws IOException {
        System.out.println(
                "Input an infix expression and output its postfix notation:"
        );
        // long startTime = System.currentTimeMillis();
        new Parser().expr();
        // long endTime = System.currentTimeMillis();
        // System.out.println("\nExecution time: " + (endTime - startTime) + " ms");
        System.out.println("\nEnd of program.");
    }
}

