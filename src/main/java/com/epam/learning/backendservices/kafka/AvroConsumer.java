package com.epam.learning.backendservices.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
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
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, "bui");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "bui_group");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, BuiDeserializer.class.getName());

        KafkaConsumer<String, BuiRegistration> kafkaConsumer = new KafkaConsumer<>(properties);
        String topic = "bui_kafka_topic";
        kafkaConsumer.subscribe(List.of(topic));

        AtomicReference<BuiRegistration> msgCons = new AtomicReference<>();

        ConsumerRecords<String, BuiRegistration> records = kafkaConsumer.poll(5000);

        records.forEach(record -> {
            msgCons.set(record.value());
            logger.info("Message received " + record.value());
        });

        kafkaConsumer.close();
    }
}
