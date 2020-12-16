package com.company;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.util.concurrent.ThreadLocalRandom;

public class PublishTemperature {

    static String topic = "KYH20IOT/TEMPERATURE";
    static String content = "";
    static String broker = "tcp://broker.hivemq.com:1883";
    static String clientId = "5PH0QWQRXc";

    static void publishData()throws InterruptedException {
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
            client.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: "+content);

            while (client.isConnected()){
                content = getTemperature();
                MqttMessage message = new MqttMessage(content.getBytes());
                client.publish(topic, message);
                System.out.println("Message published");
                Thread.sleep(60000);
            }
            client.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }

    static String getTemperature(){
        int randomNum = ThreadLocalRandom.current().nextInt(15, 25 + 1);
        return "" + randomNum;
    }
}
