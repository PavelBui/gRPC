package com.epam.learning.backendservices;

import com.epam.learning.backendservices.kafka.BuiRegistration;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

public class AvroConsumer {

    private static final Logger logger = Logger.getLogger(AvroConsumer.class.getName());

    public static void main(String[] args) throws IOException {
        InputStream inputStream = AvroConsumer.class.getClassLoader().getResourceAsStream("avroconsumer.properties");
        Properties properties = new Properties();
        properties.load(inputStream);

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
