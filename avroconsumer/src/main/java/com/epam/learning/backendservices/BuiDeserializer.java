package com.epam.learning.backendservices;

import com.epam.learning.backendservices.kafka.BuiRegistration;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;
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
            return BuiRegistration.fromByteBuffer(ByteBuffer.wrap(data));
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
    }

    @Override
    public void close() {
    }
}
