package onlineservices.services.WindowControl;

import onlineservices.handlers.MqttClientHandler;
import onlineservices.OnlineService;
import org.eclipse.paho.client.mqttv3.MqttException;

public class WindowControlService implements OnlineService {

    private static final String MQTT_BROKER_URL = "tcp://broker.emqx.io:1883";
    private static final String MQTT_CLIENT_ID = "WindowControlService";
    private static final String MQTT_WINDOW_SERVICE_TOPIC = "WINDOW_CONTROL_SERVICE_REQUEST";
    MqttClientHandler mqttClientHandler;

    @Override
    public void onCreate() {
        try {
            mqttClientHandler = new MqttClientHandler(MQTT_BROKER_URL, MQTT_CLIENT_ID);
            mqttClientHandler.connect();
            mqttClientHandler.subscribeToTopic(MQTT_WINDOW_SERVICE_TOPIC, new WindowControlRequestCallback());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        try {
            mqttClientHandler.unsubcribeFromTopic(MQTT_WINDOW_SERVICE_TOPIC);
            mqttClientHandler.disconnect();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
