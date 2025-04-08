package User;

import Agenda.AgendaModel;

import java.util.ArrayList;

/**
 * user model
 */
public class UserModel {
    private String name;
    private String password;

    private ArrayList<AgendaModel> agendas;

    /**
     * constructor
     * @param name user name
     * @param password user password
     */
    public UserModel(String name, String password) {
        this.name = name;
        this.password = password;
        this.agendas = new ArrayList<>();
    }

    /**
     * get username
     * @return user name
     */
    public String getName() {
        return name;
    }

    /**
     * validate user's password
     * @param password input password
     * @return true if validate successfully
     */
    public boolean validatePassword(String password) {
        return password.equals(this.password);
    }

    /**
     * get all agendas of user
     * @return all agendas
     */
    public ArrayList<AgendaModel> getAgendas() {
        return agendas;
    }

    /**
     * add an agenda for user
     * @param agenda target agenda
     */
    public void addAgenda(AgendaModel agenda) {
        agendas.add(agenda);
    }
}
