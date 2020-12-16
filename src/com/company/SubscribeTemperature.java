package com.company;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.util.Scanner;

public class SubscribeTemperature implements MqttCallback {

    static String readTopic = "KYH20IOT/TEMPERATURE";
    String message = "";
    String writeTopic = "KYH20IOT/CTRL";
    String broker = "tcp://broker.hivemq.com:1883";
    String clientId = "5PH0QWQRXa";
    MqttClient client;

    public SubscribeTemperature() {
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(false);
            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);
            System.out.println("Connected");
            client.setCallback(this);
            client.subscribe(readTopic);
            while(client.isConnected()) {

            }
            client.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        }
        catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

    String checkTemperature(String temp) {
        Scanner scanner = new Scanner(temp);
        int temperature =scanner.nextInt();
        if(temperature < 22) {
            return "+";
        }
        else {
            return "-";
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println("RECEIVED: " + mqttMessage);
        message = checkTemperature(mqttMessage.toString());
        MqttMessage newMessage = new MqttMessage(message.getBytes());
        client.publish(writeTopic, newMessage);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
