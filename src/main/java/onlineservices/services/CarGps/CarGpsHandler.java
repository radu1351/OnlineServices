package onlineservices.services.CarGps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import onlineservices.models.Status;
import onlineservices.models.TripReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class CarGpsHandler {

    private static final Logger LOGGER = LogManager.getLogger(CarGpsHandler.class);
    private static final String startingSignal = "-10000, -10000";
    private static final String endingSignal = "-10001, -10001";
    private static final String sosSignal = "-11111, -11111";
    private boolean hasTripStarted = false;
    private final List<String> tripCoordinates = new ArrayList<>();
    private Date startTripDate;
    private Date endTripDate;
    private Date lastCoordinatesDate;
    private Status status;
    private boolean sosEmailSent = false;
    private String lastCoordinates;
    private int sosTrigger = 0;

    public void processCoordinates(String coordinates) throws IOException {
        if (coordinates.equals(startingSignal)) {
            if (!hasTripStarted) {
                hasTripStarted = true;
                startTripDate = new Date();
            } else {
                startTripDate = endTripDate = new Date();
                status = Status.INCOMPLETE;
                TripReport tripReport = new TripReport(tripCoordinates, startTripDate, endTripDate, status, sosEmailSent);
                sendTripReport(tripReport);
                tripCoordinates.clear();
            }
            sosTrigger = 0;
        } else if (coordinates.equals(endingSignal) && hasTripStarted) {
            endTripDate = new Date();
            status = Status.COMPLETE;
            hasTripStarted = false;
            sosTrigger = 0;
            TripReport tripReport = new TripReport(tripCoordinates, startTripDate, endTripDate, status, sosEmailSent);
            sendTripReport(tripReport);
            tripCoordinates.clear();
        } else if (coordinates.equals(sosSignal)) {
            sosTrigger++;
            if (sosTrigger == 10) {
                sosEmailSent = true;
                status = Status.SOS;
                TripReport tripReport = new TripReport(tripCoordinates, startTripDate, lastCoordinatesDate, status, sosEmailSent);
                sendSosData(lastCoordinates, lastCoordinatesDate);
                sendTripReport(tripReport);
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

    private void sendSosData(String lastCoordinates, Date lastCoordinatesDate) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString;

        Map<String, Object> sosData = new HashMap<>();
        sosData.put("lastCoordinates", lastCoordinates);
        sosData.put("lastCoordinatesDate", lastCoordinatesDate);

        try {
            jsonString = mapper.writeValueAsString(sosData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        URL url = new URL("http://localhost:8080/receive_sos_data_from_gps_service");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Content-Length", String.valueOf(jsonString.getBytes(StandardCharsets.UTF_8).length));
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            osw.write(jsonString);
            osw.flush();
        }

        int responseCode = conn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            LOGGER.info("POST Response: " + response);
        } else {
            LOGGER.info("POST Request failed. ");
        }
        conn.disconnect();
    }

    private void sendTripReport(TripReport tripReport) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString;
        try {
            jsonString = mapper.writeValueAsString(tripReport);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        URL url = new URL("http://localhost:8080/receive_reports_from_gps_service");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Content-Length", String.valueOf(jsonString.getBytes(StandardCharsets.UTF_8).length));
        conn.setDoOutput(true);
        try (OutputStream os = conn.getOutputStream()) {
            OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            osw.write(jsonString);
            osw.flush();
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            LOGGER.info("POST Response: " + response);
        } else {
            LOGGER.info("POST Request failed. ");
        }
        conn.disconnect();
    }
}
