import Commands.CommandFactory;
import Commands.ICommand;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Agenda Service
 */
public class AgendaService {
    AgendaService(){}

    private static Scanner scanner = new Scanner(System.in);
    private static boolean quit = false;

    /**
     * program entrance
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println("========Agenda Service========");
        while (!quit) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            parseCommand(input);
        }
        System.out.println("========Agenda Service Quit========");
    }

    /**
     * parse input
     * @param input user input
     */
    private static void parseCommand(String input) {
        String[] args = input.split(" ");

        switch (args[0].toLowerCase()) {
            case "quit" -> {
                quit(args);
                return;
            }
            case "batch" -> {
                batch(args);
                return;
            }
        }

        ICommand cmd = CommandFactory.getCommand(args);
        if(cmd == null) {
            System.out.println("Invalid command");
            return;
        }
        String message = cmd.exec(args);
        System.out.print(message);
    }

    /**
     * execute batch command
     * @param args command args
     */
    private static void batch(String[] args) {
        if(args.length != 2) {
            System.out.println("Invalid command");
            return;
        }
        String fileName = args[1];
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("$ " + line);
                parseCommand(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * execute quit command
     * @param args command args
     */
    private static void quit(String[] args) {
        if(args.length != 1) {
            System.out.println("Invalid command");
            return;
        }
        quit = true;
    }
}