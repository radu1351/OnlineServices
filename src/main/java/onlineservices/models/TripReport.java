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
