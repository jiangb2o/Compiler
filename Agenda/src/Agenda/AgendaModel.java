package Agenda;

import java.util.Date;

public class AgendaModel {
    private Date startTime;
    private Date endTime;
    private String label;
    private String scheduledUser1;
    private String scheduledUser2;

    public AgendaModel(String scheduledUser1, String scheduledUser2, Date startTime, Date endTime, String label) {
        this.scheduledUser1 = scheduledUser1;
        this.scheduledUser2 = scheduledUser2;
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
    public String getScheduledUser1() {
        return scheduledUser1;
    }
    public String getScheduledUser2() {
        return scheduledUser2;
    }
}
