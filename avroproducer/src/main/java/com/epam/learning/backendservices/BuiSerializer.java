package com.epam.learning.backendservices;

import com.epam.learning.backendservices.kafka.BuiRegistration;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;
import java.util.logging.Logger;

public class BuiSerializer implements Serializer<BuiRegistration> {

    private static final Logger logger = Logger.getLogger(BuiSerializer.class.getName());

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, BuiRegistration data) {
        try {
            if (data == null){
                logger.info("Null received at serializing");
                return null;
            }
            logger.info("Serializing...");
            return data.toByteBuffer().array();
        } catch (Exception e) {
            throw new SerializationException("Error when serializing BuiRegistration to byte[]");
        }
    }

    @Override
    public void close() {
    }
}
