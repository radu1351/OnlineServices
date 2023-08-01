package onlineservices.services.CarClimatization;

import onlineservices.services.WindowControl.WindowControlRequestCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class CarClimatizationRequestCallback implements MqttCallback {
    private CarClimatizationHandler carClimatizationHandler = new CarClimatizationHandler(20);
    private static final Logger LOGGER = LogManager.getLogger(CarClimatizationRequestCallback.class);

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        LOGGER.info("Received car climatization value:" + mqttMessage.toString());
        carClimatizationHandler.processTemperature(mqttMessage.toString());
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
