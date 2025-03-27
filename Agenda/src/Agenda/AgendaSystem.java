package Agenda;

import User.UserModel;

import java.util.ArrayList;

public class AgendaSystem {
    public boolean addAgenda(UserModel user1, UserModel user2, AgendaModel agenda) {
        boolean checkUser1 = checkAgenda(user1, agenda);
        boolean checkUser2 = checkAgenda(user2, agenda);
        if(checkUser1 && checkUser2) {
            user1.addAgenda(agenda);
            user2.addAgenda(agenda);
            return true;
        } else {
            return false;
        }
    }

    /**
     * check if agenda is overlap with user's agendas
     * @param user user model
     * @param agenda agenda model
     * @return if overlap return false
     */
    private boolean checkAgenda(UserModel user, AgendaModel agenda) {
        ArrayList<AgendaModel> userAgendas = user.getAgendas();
        boolean overlap = false;
        for(AgendaModel a : userAgendas ) {
            if(!checkAgenda(a, agenda)) {
                overlap = true;
                break;
            }
        }
        return !overlap;
    }

    /**
     * check two agendas is date overlap or not
     * @param agenda1 agenda1
     * @param agenda2 agenda2
     * @return if overlap return false
     */
    private boolean checkAgenda(AgendaModel agenda1, AgendaModel agenda2) {
        return agenda1.getEndTime().before(agenda2.getStartTime()) ||
                agenda1.getStartTime().after(agenda2.getEndTime());
    }
}
