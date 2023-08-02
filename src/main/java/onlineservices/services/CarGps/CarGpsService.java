package onlineservices.services.CarGps;

import onlineservices.services.OnlineService;
import onlineservices.mqtt.MqttClientHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;

public class CarGpsService implements OnlineService {
    private static final Logger LOGGER = LogManager.getLogger(CarGpsService.class);
    private static final String MQTT_BROKER_URL = "tcp://broker.emqx.io:1883";
    private static final String MQTT_CLIENT_ID = "CarGpsService";
    private static final String MQTT_CAR_GPS_SENSOR_TOPIC = "CAR_GPS_SENSOR";
    private MqttClientHandler mqttClientHandler;

    @Override
    public void onCreate() {
        try {
            mqttClientHandler = new MqttClientHandler(MQTT_BROKER_URL, MQTT_CLIENT_ID);
            mqttClientHandler.connect();
            mqttClientHandler.subscribeToTopic(MQTT_CAR_GPS_SENSOR_TOPIC, new CarGpsRequestCallback());
        } catch (Exception exception) {
            LOGGER.error("Error initialising CarGpsService MQTT Client", exception);
        }
    }

    @Override
    public void onDestroy() {
        try {
            mqttClientHandler.unsubscribeFromTopic(MQTT_CAR_GPS_SENSOR_TOPIC);
            mqttClientHandler.disconnect();
        } catch (MqttException exception) {
            LOGGER.error("Error closing CarGpsService MQTT Client", exception);
        }
    }
}
