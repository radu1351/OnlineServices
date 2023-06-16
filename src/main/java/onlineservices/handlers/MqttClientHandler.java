package onlineservices.handlers;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttClientHandler {

    MqttClient mqttClient;

    public MqttClientHandler(String brokerUrl, String clientId) {
        try {
            mqttClient = new MqttClient(brokerUrl, clientId);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public void connect() throws MqttException {
        mqttClient.connect();
    }

    public void disconnect() throws MqttException {
        mqttClient.disconnect();
    }

    public void subscribeToTopic(String topic, MqttCallback callback) throws MqttException {
        mqttClient.setCallback(callback);
        mqttClient.subscribe(topic);
    }

    public void unsubcribeFromTopic(String topic) throws MqttException {
        mqttClient.unsubscribe(topic);
    }

}
