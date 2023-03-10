package com.epam.learning.backendservices.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.logging.Logger;

public class AvroProducer {

    private static final Logger logger = Logger.getLogger(AvroProducer.class.getName());

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "bui");
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "1");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, "10");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, BuiSerializer.class.getName());

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
