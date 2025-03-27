package User;

import Agenda.AgendaModel;

import java.util.ArrayList;

public class UserModel {
    private final String name;
    private final String password;

    private ArrayList<AgendaModel> agendas;

    public UserModel(String name, String password) {
        this.name = name;
        this.password = password;
        this.agendas = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public boolean validatePassword(String password) {
        return password.equals(this.password);
    }

    public ArrayList<AgendaModel> getAgendas() {
        return agendas;
    }

    public void addAgenda(AgendaModel agenda) {
            agendas.add(agenda);
    }
}
