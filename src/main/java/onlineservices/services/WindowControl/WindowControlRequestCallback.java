package onlineservices.services.WindowControl;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class WindowControlRequestCallback implements MqttCallback {

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println("Received window control request value: " + mqttMessage.toString());
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
