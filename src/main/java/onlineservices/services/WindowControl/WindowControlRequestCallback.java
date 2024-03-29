package onlineservices.services.WindowControl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class WindowControlRequestCallback implements MqttCallback {

    private static final Logger LOGGER = LogManager.getLogger(WindowControlRequestCallback.class);

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage){
        LOGGER.info("Received window control request value:" + mqttMessage.toString());
    }

    @Override
    public void connectionLost(Throwable throwable) {
        LOGGER.info("WindowControlService lost connection.");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        LOGGER.info("Message delivery completed.");
    }
}
