package com.epam.learning.backendservices;

import org.apache.kafka.clients.producer.*;
import com.epam.learning.backendservices.kafka.BuiRegistration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class AvroProducer {

    private static final Logger logger = Logger.getLogger(AvroProducer.class.getName());

    public static void main(String[] args) throws IOException {
        InputStream inputStream = AvroProducer.class.getClassLoader().getResourceAsStream("avroproducer.properties");
        Properties properties = new Properties();
        properties.load(inputStream);

        KafkaProducer<String, BuiRegistration> kafkaProducer = new KafkaProducer<>(properties);
        String topic = "bui_kafka_topic";
        BuiRegistration buiRegistration = BuiRegistration.newBuilder()
                .setNumber("3759 AA-3")
                .setColor("orange")
                .setType("sedan")
                .setLeftSideDrive(true)
                .setDoorsNumber(4).build();

        ProducerRecord<String, BuiRegistration> producerRecord = new ProducerRecord<>(topic, buiRegistration);

        kafkaProducer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e == null) {
                    logger.info("Success! Metadata: " + recordMetadata.toString());
                } else {
                    logger.info("Error! Exception: " + e);
                }
            }
        });
        kafkaProducer.flush();
        kafkaProducer.close();
    }
}
