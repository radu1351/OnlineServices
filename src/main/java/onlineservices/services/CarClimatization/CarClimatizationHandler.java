package onlineservices.services.CarClimatization;

import onlineservices.models.ClimatizationState;
import onlineservices.models.ClimatizationReport;
import onlineservices.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarClimatizationHandler {
    private int setTemperature;
    private List<Integer> lastMinuteTemperature = new ArrayList<>(0);
    private ClimatizationState climatizationState = ClimatizationState.OFF;
    private static final Logger LOGGER = LogManager.getLogger(CarClimatizationHandler.class);

    public CarClimatizationHandler(int setTemperature) {
        this.setTemperature = setTemperature;
    }

    public void processTemperature(String temperature) {
        if (temperature.contains("set")) {
            this.setTemperature = Integer.parseInt(temperature.split(" ")[1]);
            LOGGER.info("Set temperature to " + setTemperature);
        } else {
            lastMinuteTemperature.add(Integer.parseInt(temperature));
            if (lastMinuteTemperature.size() == 4) {
                if (Utils.areNumbersEqual(lastMinuteTemperature) && lastMinuteTemperature.get(0).equals(setTemperature)) {
                    climatizationState = ClimatizationState.OFF;
                    LOGGER.info("Climatization has been turned OFF.");
                } else {
                    int difference = setTemperature - lastMinuteTemperature.get(3);
                    if (difference >= 0) {
                        //Temperature decreased => start heating
                        if (difference == 0) climatizationState = ClimatizationState.HEATING_0;
                        if (difference > 0 && difference <= 2) climatizationState = ClimatizationState.HEATING_1;
                        if (difference >= 3 && difference <= 4) climatizationState = ClimatizationState.HEATING_2;
                        if (difference >= 5 && difference <= 6) climatizationState = ClimatizationState.HEATING_3;
                        if (difference >= 7 && difference <= 8) climatizationState = ClimatizationState.HEATING_4;
                        if (difference > 9) climatizationState = ClimatizationState.HEATING_5;
                        LOGGER.info("Heating ON - Level: " + climatizationState);
                    } else {
                        //Temperature increased => start cooling
                        if (difference == 0) climatizationState = ClimatizationState.COOLING_0;
                        if (difference >= -2 && difference < 0) climatizationState = ClimatizationState.COOLING_1;
                        if (difference >= -4 && difference <= -3) climatizationState = ClimatizationState.COOLING_2;
                        if (difference >= -6 && difference <= -5) climatizationState = ClimatizationState.COOLING_3;
                        if (difference >= -8 && difference <= -7) climatizationState = ClimatizationState.COOLING_4;
                        if (difference <= -9) climatizationState = ClimatizationState.COOLING_5;
                        LOGGER.info("Cooling ON - Level: " + climatizationState);
                    }
                }
                ClimatizationReport climatizationReport = new ClimatizationReport(new Date(),
                        Short.valueOf(climatizationState.equals(ClimatizationState.OFF) ? "-1"
                                : climatizationState.toString().substring(climatizationState.toString().length() - 1)),
                        (short) (climatizationState.toString().contains("OFF") ? 0
                                : climatizationState.toString().contains("HEATING") ? 1 : 0));
                System.out.println(climatizationReport);
                lastMinuteTemperature.clear();
            }
        }

    }

}
