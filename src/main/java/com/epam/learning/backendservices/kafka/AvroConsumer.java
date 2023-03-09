package com.epam.learning.backendservices.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

public class AvroConsumer {

    private static final Logger logger = Logger.getLogger(AvroConsumer.class.getName());

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("acks", "1");
        properties.setProperty("retries", "10");
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", BuiDeserializer.class.getName());

        KafkaConsumer<String, BuiRegistration> kafkaConsumer = new KafkaConsumer<String, BuiRegistration>(properties);
        String topic = "bui_kafka_topic";
        kafkaConsumer.subscribe(List.of(topic));

        AtomicReference<BuiRegistration> msgCons = new AtomicReference<>();

        ConsumerRecords<String, BuiRegistration> records = kafkaConsumer.poll(5);
        records.forEach(record -> {
            msgCons.set(record.value());
            logger.info("Message received " + record.value());
        });

        kafkaConsumer.close();
    }
}
