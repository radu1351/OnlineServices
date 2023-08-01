package onlineservices.services.CarClimatization;

import onlineservices.OnlineService;
import onlineservices.handlers.MqttClientHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;

public class CarClimatizationService implements OnlineService {

    private static final Logger LOGGER = LogManager.getLogger(CarClimatizationService.class);
    private static final String MQTT_BROKER_URL = "tcp://broker.emqx.io:1883";
    private static final String MQTT_CLIENT_ID = "CarClimatizationService";
    private static final String MQTT_INSIDE_TEMPERATURE_SENSOR_TOPIC = "INSIDE_TEMPERATURE_SENSOR";
    private MqttClientHandler mqttClientHandler;

    @Override
    public void onCreate() {
        try {
            mqttClientHandler = new MqttClientHandler(MQTT_BROKER_URL, MQTT_CLIENT_ID);
            mqttClientHandler.connect();
            mqttClientHandler.subscribeToTopic(MQTT_INSIDE_TEMPERATURE_SENSOR_TOPIC, new CarClimatizationRequestCallback());
        } catch (Exception exception) {
            LOGGER.error("Error initialising CarClimatizationService MQTT Client", exception);
        }
    }

    @Override
    public void onDestroy() {
        try {
            mqttClientHandler.unsubcribeFromTopic(MQTT_INSIDE_TEMPERATURE_SENSOR_TOPIC);
            mqttClientHandler.disconnect();
        } catch (MqttException exception) {
            LOGGER.error("Error closing CarClimatizationService MQTT Client", exception);
        }
    }
}
