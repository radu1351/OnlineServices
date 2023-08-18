package onlineservices.services.CarGps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;

public class CarGpsRequestCallback implements MqttCallback {

    private static final Logger LOGGER = LogManager.getLogger(CarGpsHandler.class);

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws IOException {
        LOGGER.info("Received GPS coordinates values:" + mqttMessage.toString());
        CarGpsHandlerSingleton.getInstance().processCoordinates(mqttMessage.toString());
    }

    @Override
    public void connectionLost(Throwable throwable) {
        LOGGER.info("CarGpsService lost connection.");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        LOGGER.info("Message delivery completed.");
    }
}
