import Agenda.AgendaModel;
import Agenda.AgendaSystem;
import User.UserModel;
import User.UserSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class AgendaService {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean quit = false;
    private static AgendaSystem agendaSystem = new AgendaSystem();
    private static UserSystem userSystem = new UserSystem();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        System.out.println("========Agenda Service========");
        while (!quit) {
            System.out.print("$ ");
            String cmd = scanner.nextLine();
            parseCommand(cmd);
        }
        System.out.println("========Agenda Service Quit========");
    }

    private static void parseCommand(String cmd) {
        String[] args = cmd.split(" ");
        // case-insensitive
        switch (args[0].toLowerCase()) {
            case "register" -> register(args);
            case "add" -> add(args);
            case "query" -> query(args);
            case "delete" -> delete(args);
            case "clear" -> clear(args);
            case "batch" -> batch(args);
            case "quit" -> quit(args);
            default -> System.out.println("Invalid command");
        }
    }

    private static void register(String[] args) {
        if(args.length != 3) {
            System.out.println("Invalid command");
            return;
        }

        String username = args[1];
        String password = args[2];
        if(userSystem.registerUser(username, password)){
            System.out.println("User registered successfully");
        } else {
            System.out.println("Username already exists");
        }
    }

    private static void add(String[] args) {
        if(args.length != 9) {
            System.out.println("Invalid command");
            return;
        }

        UserModel user1 = validateUser(args[1], args[2]);
        if(user1 == null){
            return;
        }
        String otherUsername = args[3];
        UserModel user2 = userSystem.getUserByName(otherUsername);
        if(user2 == null){
            System.out.println("other User not found");
            return;
        }

        Date[] dates = parseDate(args[4] + " " + args[5], args[6] + " " + args[7]);

        String label = args[8];
        AgendaModel agenda = new AgendaModel(user1, user2, dates[0], dates[1], label);
        if(agendaSystem.addAgenda(user1, user2, agenda)) {
             System.out.println("Agenda added successfully");
        } else {
            System.out.println("Agenda already exists");
        }
    }

    private static void query(String[] args) {
        if(args.length != 7) {
            System.out.println("Invalid command");
            return;
        }

        UserModel user1 = validateUser(args[1], args[2]);
        if(user1 == null){
            return;
        }

        Date[] dates = parseDate(args[3] + " " + args[4], args[5] + " " + args[6]);

        ArrayList<AgendaModel> agendas = agendaSystem.queryAgenda(user1, dates[0], dates[1]);
        System.out.println("agenda count: " + agendas.size());
        System.out.println("start\t\t\t\tend\t\t\t\t\tlabel\totherUser");
        for (AgendaModel agenda : agendas) {
            System.out.format("%s\t%s\t%s\t%s\n", dateFormat.format(agenda.getStartTime()),
                    dateFormat.format(agenda.getEndTime()), agenda.getLabel(), agenda.getOtherUser(user1));
        }
    }

    private static void delete(String[] args) {
        if(args.length != 4) {
            System.out.println("Invalid command");
            return;
        }

        UserModel user1 = validateUser(args[1], args[2]);
        if(user1 == null){
            return;
        }

        String label = args[3];
        if(agendaSystem.deleteAgenda(user1, label)){
            System.out.println("Agenda deleted successfully");
        } else {
            System.out.println("delete failed");
        }
    }

    private static void clear(String[] args) {
        if(args.length != 3) {
            System.out.println("Invalid command");
            return;
        }

        UserModel user1 = validateUser(args[1], args[2]);
        if(user1 == null){
            return;
        }

        int count = agendaSystem.clearAgenda(user1);
        System.out.format("delete agenda count: %d\n", count);
    }

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

    private static void quit(String[] args) {
        if(args.length != 1) {
            System.out.println("Invalid command");
            return;
        }
        quit = true;
    }

    private static UserModel validateUser(String username, String password) {
        if(userSystem.validateUser(username, password)){
            return userSystem.getUserByName(username);
        } else {
            System.out.println("Invalid username or password");
            return null;
        }
    }

    private static Date[] parseDate(String start, String end) {
        Date startDate;
        Date endDate;
        try {
            startDate = dateFormat.parse(start);
            endDate = dateFormat.parse(end);
        } catch (ParseException e) {
            System.out.println("Invalid date format(correctly formatted date: yyyy-MM-dd HH:mm)");
            throw new RuntimeException(e);
        }
        return new Date[]{startDate, endDate};
    }
}