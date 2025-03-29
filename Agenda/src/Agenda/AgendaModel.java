package Agenda;

import User.UserModel;

import java.util.Date;

public class AgendaModel {
    private Date startTime;
    private Date endTime;
    private String label;
    private UserModel user1;
    private UserModel user2;

    public AgendaModel(UserModel user1, UserModel user2, Date startTime, Date endTime, String label) {
        this.user1 = user1;
        this.user2 = user2;
        this.startTime = startTime;
        this.endTime = endTime;
        this.label = label;
    }

    public Date getStartTime() {
        return startTime;
    }
    public Date getEndTime() {
        return endTime;
    }

    public String getLabel() {
        return label;
    }
    public UserModel getUser1() {
        return user1;
    }
    public UserModel getUser2() {
        return user2;
    }

    public String getOtherUser(UserModel user) {
        return user.equals(user1) ? user2.getName() : user1.getName();
    }
}
