package Agenda;

import User.UserModel;

import java.util.Date;

/**
 * agenda model
 */
public class AgendaModel {
    private Date startTime;
    private Date endTime;
    private String label;
    private UserModel user1;
    private UserModel user2;

    /**
     * constructor
     * @param user1 user 1 in agenda
     * @param user2 user 2 in agenda
     * @param startTime agenda start time
     * @param endTime agenda end time
     * @param label agenda label
     */
    public AgendaModel(UserModel user1, UserModel user2, Date startTime, Date endTime, String label) {
        this.user1 = user1;
        this.user2 = user2;
        this.startTime = startTime;
        this.endTime = endTime;
        this.label = label;
    }

    /**
     * get agenda start time
     * @return agenda start time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * get agenda end time
     * @return agenda end time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * get agenda label
     * @return agenda label
     */
    public String getLabel() {
        return label;
    }

    /**
     * get user model of agenda user1
     * @return user1 model
     */
    public UserModel getUser1() {
        return user1;
    }

    /**
     * get user model of agenda user2
     * @return user2 model
     */
    public UserModel getUser2() {
        return user2;
    }

    /**
     * get other user of this agenda
     * @param user current user
     * @return username of other user
     */
    public String getOtherUser(UserModel user) {
        return user.equals(user1) ? user2.getName() : user1.getName();
    }
}
