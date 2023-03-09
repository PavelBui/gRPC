package com.epam.learning.backendservices.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.logging.Logger;

public class AvroProducer {

    private static final Logger logger = Logger.getLogger(AvroProducer.class.getName());

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("acks", "1");
        properties.setProperty("retries", "10");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", BuiSerializer.class.getName());

        KafkaProducer<String, BuiRegistration> kafkaProducer = new KafkaProducer<String, BuiRegistration>(properties);
        String topic = "bui_kafka_topic";
        BuiRegistration buiRegistration = BuiRegistration.newBuilder()
                .setNumber("3759 AA-3")
                .setColor("red")
                .setType("sedan")
                .setLeftSideDrive(true)
                .setDoorsNumber(4).build();

        ProducerRecord<String, BuiRegistration> producerRecord = new ProducerRecord<String, BuiRegistration>(topic, buiRegistration);

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
