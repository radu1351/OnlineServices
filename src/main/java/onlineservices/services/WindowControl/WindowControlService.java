package onlineservices.services.WindowControl;

import org.eclipse.paho.client.mqttv3.*;
import onlineservices.OnlineService;

public class WindowControlService implements OnlineService {

    private static final String MQTT_BROKER_URL = "tcp://broker.emqx.io:1883";
    private static final String MQTT_CLIENT_ID = "WindowControlService";
    private static final String MQTT_WINDOW_SERVICE_TOPIC = "WindowControlService";
    private MqttClient client;

    @Override
    public void onCreate() {
        openMqttConnection();
    }

    @Override
    public void onDestroy() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void openMqttConnection() {
        try {
            client = new MqttClient(MQTT_BROKER_URL, MQTT_CLIENT_ID);
            client.connect();
            client.subscribe(MQTT_WINDOW_SERVICE_TOPIC);
            client.setCallback(new MqttCallback() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Received window control request value: " + message.toString());
                }

                @Override
                public void connectionLost(Throwable cause) {
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
