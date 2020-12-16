package com.company;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Subscribe {

    static String topic = "KYH20IOT/+";
    static String broker = "tcp://broker.hivemq.com:1883";
    static String clientId = "5PH0QWQRXb";

    static void subscribeData() {
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
            client.connect(connOpts);
            System.out.println("Connected");
            client.subscribe(topic, new MqttMessageListener());
            while(client.isConnected()) {

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

    static class MqttMessageListener implements IMqttMessageListener {

        public void messageArrived(String topic, MqttMessage content) throws IOException {
            Date date = new Date();
            String receivedContent = topic + ", " + content.toString();
            System.out.println(date + ", " + receivedContent);
            FileWriter fileWriter = new FileWriter("TestFolder/TestFile", true);
            fileWriter.write(date + ", " + receivedContent + "\n");
            fileWriter.close();
        }
    }

}
