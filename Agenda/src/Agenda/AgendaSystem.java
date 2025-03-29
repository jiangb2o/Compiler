package Agenda;

import User.UserModel;

import java.util.ArrayList;
import java.util.Date;

public class AgendaSystem {
    /**
     * try to add agenda for users
     * @param user1 require user
     * @param user2 target user
     * @param agenda agenda to add
     * @return true if add successfully
     */
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
     * query agendas for user in date interval
     * @param start start date
     * @param end end date
     * @return agendas in interval
     */
    public ArrayList<AgendaModel> queryAgenda(UserModel user, Date start, Date end) {
        ArrayList<AgendaModel> agendas = user.getAgendas();
        ArrayList<AgendaModel> result = new ArrayList<>();
        for(AgendaModel agenda: agendas) {
            if(agendaInInterval(agenda, start, end)) {
                result.add(agenda);
            }
        }
        return result;
    }

    /**
     * delete agenda that user register
     * @param user register agenda user
     * @param label agenda label
     * @return true if delete successfully
     */
    public boolean deleteAgenda(UserModel user, String label) {
        ArrayList<AgendaModel> agendas = user.getAgendas();
        for(AgendaModel agenda: agendas) {
            if(agenda.getLabel().equals(label) && agenda.getUser1().equals(user)) {
                deleteAgenda(agenda);
                return true;
            }
        }
        return false;
    }

    /**
     * clear all agendas for user that user register
     * @param user register user
     * @return count of clear agendas
     */
    public int clearAgenda(UserModel user) {
        ArrayList<AgendaModel> agendas = user.getAgendas();
        ArrayList<AgendaModel> agendasToClear = new ArrayList<>();
        for(AgendaModel agenda: agendas) {
            if(agenda.getUser1().equals(user)) {
                agendasToClear.add(agenda);
            }
        }

        int clearCount = agendasToClear.size();
        for(AgendaModel agenda: agendasToClear) {
            deleteAgenda(agenda);
        }
        return clearCount;
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

    /**
     * check an agenda in given date interval or not
     * @param agenda agenda to check
     * @param start start date
     * @param end end date
     * @return true if agenda in interval
     */
    private boolean agendaInInterval(AgendaModel agenda, Date start, Date end) {
        return agenda.getEndTime().after(start) && agenda.getStartTime().before(end);
    }

    /**
     * delete target agenda for both users
     */
    private void deleteAgenda(AgendaModel agenda) {
        UserModel user1 = agenda.getUser1();
        UserModel user2 = agenda.getUser2();
        user1.getAgendas().remove(agenda);
        user2.getAgendas().remove(agenda);
    }
}
