package onlineservices.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TripReport {
    private List<String> tripCoordinates;
    private Date startTripDate;
    private Date endTripDate;
    private Status status;
    private boolean sosEmailSent;

    public TripReport(List<String> tripCoordinates, Date startTripDate, Date endTripDate, Status status, boolean sosEmailSent) {
        this.tripCoordinates = tripCoordinates;
        this.startTripDate = startTripDate;
        this.endTripDate = endTripDate;
        this.status = status;
        this.sosEmailSent = sosEmailSent;
    }

    public TripReport(){
        this.tripCoordinates = new ArrayList<>();
        this.startTripDate = new Date();
        this.endTripDate = new Date();
        this.status = Status.COMPLETE;
        this.sosEmailSent = false;
    }

    public List<String> getTripCoordinates() {
        return tripCoordinates;
    }

    public void setTripCoordinates(List<String> tripCoordinates) {
        this.tripCoordinates = tripCoordinates;
    }

    public Date getStartTripDate() {
        return startTripDate;
    }

    public void setStartTripDate(Date startTripDate) {
        this.startTripDate = startTripDate;
    }

    public Date getEndTripDate() {
        return endTripDate;
    }

    public void setEndTripDate(Date endTripDate) {
        this.endTripDate = endTripDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isSosEmailSent() {
        return sosEmailSent;
    }

    public void setSosEmailSent(boolean sosEmailSent) {
        this.sosEmailSent = sosEmailSent;
    }

    @Override
    public String toString() {
        return "TripReport \n" +
                "status: " + status + "\n" +
                "startTripDate: " + startTripDate + "\n" +
                "endTripDate: " + endTripDate + "\n" +
                "coordinates: " + tripCoordinates + "\n" +
                "sosEmailSent: " + sosEmailSent;
    }
}
