package onlineservices.services.CarGps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class CarGpsRequestCallback implements MqttCallback {
    private final CarGpsHandler carGpsHandler = new CarGpsHandler();
    private static final Logger LOGGER = LogManager.getLogger(CarGpsHandler.class);

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage){
        LOGGER.info("Received GPS coordinates values:" + mqttMessage.toString());
        carGpsHandler.processCoordinates(mqttMessage.toString());
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
