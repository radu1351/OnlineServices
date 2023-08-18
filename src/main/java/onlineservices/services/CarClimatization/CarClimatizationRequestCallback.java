package onlineservices.services.CarClimatization;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;

public class CarClimatizationRequestCallback implements MqttCallback {

    private static final Logger LOGGER = LogManager.getLogger(CarClimatizationRequestCallback.class);

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws IOException {
        LOGGER.info("Received car climatization value:" + mqttMessage.toString());
        CarClimatizationHandlerSingleton.getInstance().processTemperature(mqttMessage.toString());
    }

    @Override
    public void connectionLost(Throwable throwable) {
        LOGGER.info("CarClimatizationService lost connection.");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        LOGGER.info("Message delivery completed.");
    }
}
