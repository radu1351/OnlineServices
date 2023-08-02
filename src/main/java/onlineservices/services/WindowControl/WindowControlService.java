package onlineservices.services.WindowControl;

import onlineservices.mqtt.MqttClientHandler;
import onlineservices.services.OnlineService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;

public class WindowControlService implements OnlineService {

    private static final Logger LOGGER = LogManager.getLogger(WindowControlService.class);
    private static final String MQTT_BROKER_URL = "tcp://broker.emqx.io:1883";
    private static final String MQTT_CLIENT_ID = "WindowControlService";
    private static final String MQTT_WINDOW_SERVICE_TOPIC = "WINDOW_CONTROL_SERVICE_REQUEST";
    private MqttClientHandler mqttClientHandler;

    @Override
    public void onCreate() {
        try {
            mqttClientHandler = new MqttClientHandler(MQTT_BROKER_URL, MQTT_CLIENT_ID);
            mqttClientHandler.connect();
            mqttClientHandler.subscribeToTopic(MQTT_WINDOW_SERVICE_TOPIC, new WindowControlRequestCallback());
        } catch (Exception exception) {
            LOGGER.error("Error initialising WindowControlService MQTT Client", exception);
        }
    }

    @Override
    public void onDestroy() {
        try {
            mqttClientHandler.unsubscribeFromTopic(MQTT_WINDOW_SERVICE_TOPIC);
            mqttClientHandler.disconnect();
        } catch (MqttException exception) {
            LOGGER.error("Error closing WindowControlService MQTT Client", exception);
        }
    }
}
