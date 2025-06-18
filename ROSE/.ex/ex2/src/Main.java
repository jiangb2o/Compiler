import exceptions.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        if(args.length == 0) {
            Usage();
        }
        for (String file : args) {
            try {
                boolean error = false;
                OberonScanner scanner = new OberonScanner(new java.io.FileReader(file));
                System.out.println(">>>Scanning file: " + file);
                while (!scanner.yyatEOF()) {
                    try {
                        String token = scanner.yylex();
                        System.out.println(token + ":" + scanner.yytext());
                    } catch (LexicalException e) {
                        error = true;
                        System.out.println("=====Error in file: " + file + "=====");
                        System.out.println("Line: " + scanner.getLine() + ", Column: " + scanner.getColumn());
                        System.out.println(scanner.yytext());
                        System.out.println(e.getMessage());
                        System.out.println("\n");
                    } catch (java.io.IOException e) {
                        System.out.println("IOException in file: " + file);
                    }
                }
                if (!error) {
                    System.out.println("\n=====No lexical exception in file: " + file + "=====");
                } else {
                    System.out.println("\n=====Find lexical exception in file: " + file + "=====");
                }
            } catch (FileNotFoundException e) {
                System.out.println("Not found file: " + file);
            }
        }
    }
    public static void Usage() {
        System.out.println("Usage: java Main <inputfile> [<inputfile2> ...]");
    }
}
