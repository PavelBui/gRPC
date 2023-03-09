package com.epam.learning.backendservices.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Logger;

@Slf4j
public class BuiDeserializer implements Deserializer<BuiRegistration> {

    private static final Logger logger = Logger.getLogger(BuiDeserializer.class.getName());

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public BuiRegistration deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                logger.info("Null received at deserializing");
                return null;
            }
            logger.info("Deserializing...");
            String contentAsString = new String(data, StandardCharsets.UTF_8);
            String[] segments = contentAsString.split(",");
            return new BuiRegistration(segments[0], segments[1], segments[2], Boolean.parseBoolean(segments[3]), Integer.parseInt(segments[4]));
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
    }

    @Override
    public void close() {
    }
}
