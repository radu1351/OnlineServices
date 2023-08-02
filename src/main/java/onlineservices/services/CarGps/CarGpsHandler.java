package onlineservices.services.CarGps;

import onlineservices.models.Status;
import onlineservices.models.TripReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarGpsHandler {
    private static final String startingSignal = "-10000, -10000";
    private static final String endingSignal = "-10001, -10001";
    private static final String sosSignal = "-11111, -11111";

    private boolean hasTripStarted = false;
    private List<String> tripCoordinates = new ArrayList<>();
    private Date startTripDate;
    private Date endTripDate;
    private Date lastCoordinatesDate;
    private Status status;
    private boolean sosEmailSent = false;
    private String lastCoordinates;
    private int sosTrigger = 0;
    private static final Logger LOGGER = LogManager.getLogger(CarGpsService.class);

    public void processCoordinates(String coordinates) {
        if (coordinates.equals(startingSignal)) {
            if (!hasTripStarted) {
                hasTripStarted = true;
                startTripDate = new Date();
            } else {
                startTripDate = endTripDate = new Date();
                status = Status.INCOMPLETE;
                TripReport tripReport = new TripReport(tripCoordinates,startTripDate,endTripDate,status,sosEmailSent);
                tripCoordinates.clear();
            }
            sosTrigger = 0;
        } else if (coordinates.equals(endingSignal) && hasTripStarted) {
            endTripDate = new Date();
            status = Status.COMPLETE;
            hasTripStarted = false;
            sosTrigger = 0;
            TripReport tripReport = new TripReport(tripCoordinates,startTripDate,endTripDate,status,sosEmailSent);
            tripCoordinates.clear();
        } else if (coordinates.equals(sosSignal)) {
            sosTrigger++;
            if(sosTrigger==10){
                sosEmailSent=true;
                status = Status.SOS;
                TripReport tripReport = new TripReport(tripCoordinates,startTripDate,lastCoordinatesDate,status,sosEmailSent);
            }
        } else {
            if (hasTripStarted) {
                tripCoordinates.add(coordinates);
                lastCoordinates = coordinates;
                lastCoordinatesDate = new Date();
            }
            sosTrigger = 0;
        }
    }
}
