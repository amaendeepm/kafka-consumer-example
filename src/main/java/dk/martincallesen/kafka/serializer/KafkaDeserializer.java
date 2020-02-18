package dk.martincallesen.kafka.serializer;

import dk.martincallesen.datamodel.event.Account;
import dk.martincallesen.datamodel.event.SpecificRecordAdapter;
import dk.martincallesen.datamodel.event.SpecificRecordDeserializer;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Arrays;
import java.util.Map;

public class KafkaDeserializer implements Deserializer<SpecificRecordAdapter> {
    private Map<String, SpecificRecordDeserializer> topicDeserializer;

    public KafkaDeserializer(Map<String, SpecificRecordDeserializer> topicDeserializer) {
        this.topicDeserializer = topicDeserializer;
    }

    @Override
    public SpecificRecordAdapter deserialize(String topic, byte[] data) {
        try {
            final SpecificRecordDeserializer deserializer = topicDeserializer.get(topic);
            return new SpecificRecordAdapter(deserializer.deserialize(topic, data));
        } catch (Exception ex) {
            throw new SerializationException(
                    "Can't deserialize data '" + Arrays.toString(data) + "' from topic '" + topic + "'", ex);
        }
    }
}
