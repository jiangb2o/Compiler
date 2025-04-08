package Commands;

import Agenda.AgendaModel;
import Agenda.AgendaSystem;
import User.UserModel;
import User.UserSystem;

import java.util.ArrayList;
import java.util.Date;

/**
 * command factory
 */
public class CommandFactory {
    CommandFactory(){}

    /**
     * get command by args
     * @param args command args
     * @return target command class
     */
    public static ICommand getCommand(String[] args) {
        return switch (args[0].toLowerCase()) {
            case "register" -> new RegisterCmd();
            case "add" -> new AddCmd();
            case "query" -> new QueryCmd();
            case "delete" -> new DeleteCmd();
            case "clear" -> new ClearCmd();
            default -> null;
        };
    }
}

/**
 * register user command
 */
class RegisterCmd implements ICommand {

    @Override
    public String exec(String[] args) {
        String msg;
        if(args.length != 3) {
            msg = "Invalid command\n";
            return msg;
        }

        String username = args[1];
        String password = args[2];
        if(UserSystem.getInstance().registerUser(username, password)){
            msg = "User registered successfully\n";
        } else {
            msg = "Username already exists\n";
        }

        return msg;
    }
}

/**
 * add agenda command
 */
class AddCmd implements ICommand {

    @Override
    public String exec(String[] args) {
        String msg;
        if(args.length != 9) {
            msg = "Invalid command\n";
            return msg;
        }

        UserModel user1 = UserSystem.getInstance().getValidateUser(args[1], args[2]);
        if(user1 == null) {
            msg = "Invalid username or password\n";
            return msg;
        }

        UserModel user2 = UserSystem.getInstance().getUserByName(args[3]);
        if(user2 == null){
            msg = "other User not found\n";
            return msg;
        }

        Date[] dates = AgendaSystem.parseStringToDate(args[4] + " " + args[5], args[6] + " " + args[7]);

        String label = args[8];
        AgendaModel agenda = new AgendaModel(user1, user2, dates[0], dates[1], label);
        if(AgendaSystem.getInstance().addAgenda(user1, user2, agenda)) {
            msg = "Agenda added successfully\n";
        } else {
            msg = "Agenda conflict\n";
        }
        return msg;
    }
}

/**
 * query agenda command
 */
class QueryCmd implements ICommand {
    @Override
    public String exec(String[] args) {
        StringBuilder msg = new StringBuilder();
        if(args.length != 7) {
            msg.append("Invalid command\n");
            return msg.toString();
        }

        UserModel user1 = UserSystem.getInstance().getValidateUser(args[1], args[2]);
        if(user1 == null){
            msg.append("Invalid username or password\n");
            return msg.toString();
        }

        Date[] dates = AgendaSystem.parseStringToDate(args[3] + " " + args[4], args[5] + " " + args[6]);

        ArrayList<AgendaModel> agendas = AgendaSystem.getInstance().queryAgenda(user1, dates[0], dates[1]);
        msg.append(String.format("agenda count: %d\n", agendas.size()));
        msg.append(String.format("%-16s\t%-16s\t%-20s\totherUser\n", "start", "end", "label"));
        for (AgendaModel agenda : agendas) {
            msg.append(String.format("%-16s\t%-16s\t%-20s\t%s\n", AgendaSystem.parseDateToString(agenda.getStartTime()),
                    AgendaSystem.parseDateToString(agenda.getEndTime()), agenda.getLabel(), agenda.getOtherUser(user1)));
        }
        return msg.toString();
    }
}

/**
 * delete agenda command
 */
class DeleteCmd implements ICommand {
    @Override
    public String exec(String[] args) {
        String msg;
        if(args.length != 4) {
            msg = "Invalid command\n";
            return msg;
        }

        UserModel user1 = UserSystem.getInstance().getValidateUser(args[1], args[2]);
        if(user1 == null) {
            msg = "Invalid username or password\n";
            return msg;
        }

        String label = args[3];
        if(AgendaSystem.getInstance().deleteAgenda(user1, label)){
            msg = "Agenda deleted successfully\n";
        } else {
            msg = "delete failed\n";
        }
        return msg;
    }
}

/**
 * clear agenda command
 */
class ClearCmd implements ICommand {
    @Override
    public String exec(String[] args) {
        String msg;
        if(args.length != 3) {
            msg = "Invalid command\n";
            return msg;
        }

        UserModel user1 = UserSystem.getInstance().getValidateUser(args[1], args[2]);
        if(user1 == null) {
            msg = "Invalid username or password\n";
            return msg;
        }

        int count = AgendaSystem.getInstance().clearAgenda(user1);
        msg = String.format("delete agenda count: %d\n", count);
        return msg;
    }
}
