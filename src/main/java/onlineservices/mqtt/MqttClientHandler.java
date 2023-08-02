package onlineservices.mqtt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttClientHandler {

    private static final Logger LOGGER = LogManager.getLogger(MqttClientHandler.class);
    private MqttClient mqttClient;

    public MqttClientHandler(String brokerUrl, String clientId) {
        try {
            mqttClient = new MqttClient(brokerUrl, clientId);
        } catch (MqttException exception) {
            LOGGER.error("Error initialising {} MQTT Client:\n{}", mqttClient.getClientId(), exception);
        }
    }

    public void connect() throws MqttException {
        LOGGER.info(String.format("Connecting %1$s MQTT client...", mqttClient.getClientId()));
        mqttClient.connect();
        LOGGER.info(String.format("Connected %1$s MQTT client.", mqttClient.getClientId()));
    }

    public void disconnect() throws MqttException {
        LOGGER.info(String.format("Disconnecting %1$s MQTT client...", mqttClient.getClientId()));
        mqttClient.disconnect();
        LOGGER.info(String.format("Disconnected %1$s MQTT client.", mqttClient.getClientId()));
    }

    public void subscribeToTopic(String topic, MqttCallback callback) throws MqttException {
        LOGGER.info(String.format("Subscribing %1$s to %2$s topic... ", mqttClient.getClientId(), topic));
        mqttClient.setCallback(callback);
        mqttClient.subscribe(topic);
        LOGGER.info(String.format("Subscribed %1$s to %2$s topic.", mqttClient.getClientId(), topic));
    }

    public void unsubscribeFromTopic(String topic) throws MqttException {
        LOGGER.info(String.format("Unsubscribing %1$s from %2$s topic...", mqttClient.getClientId(), topic));
        mqttClient.unsubscribe(topic);
        LOGGER.info(String.format("Unsubscribed %1$s from %2$s topic.", mqttClient.getClientId(), topic));
    }

}
