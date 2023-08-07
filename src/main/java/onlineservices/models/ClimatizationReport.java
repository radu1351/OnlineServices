package onlineservices.models;

import java.util.Date;

public class ClimatizationReport {
    private static short numberOfReports = 0;
    private short id;
    private Date date;
    private short power;
    private short actionCode;

    public ClimatizationReport(Date date, short power, short action) {
        this.id = ++numberOfReports;
        this.date = date;
        this.power = power;
        this.actionCode = action;
    }

    public ClimatizationReport() {
        this.id = 0;
        this.date = new Date();
        this.power = 0;
        this.actionCode = 0;
    }

    public static short getNumberOfReports() {
        return numberOfReports;
    }

    public static void setNumberOfReports(short numberOfReports) {
        ClimatizationReport.numberOfReports = numberOfReports;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public short getPower() {
        return power;
    }

    public void setPower(short power) {
        this.power = power;
    }

    public short getActionCode() {
        return actionCode;
    }

    public void setActionCode(short actionCode) {
        this.actionCode = actionCode;
    }

    @Override
    public String toString() {
        return "ReportId: " + id + "\n Date: " + date +
                "\n Air Conditioning Power: " + power + "\n Action Code: " + actionCode;
    }
}
