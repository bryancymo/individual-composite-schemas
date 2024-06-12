package eu.cymo.kafkaSerializationEvolution;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.subject.TopicNameStrategy;
import io.confluent.kafka.serializers.subject.TopicRecordNameStrategy;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class PublishAvro {

    public static <V> void publishEvent(Producer<String, V> producer, String topic, String key, V event) {
        ProducerRecord<String, V> record = new ProducerRecord<>(topic, key, event);
        try {
            producer.send(record).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Properties getPropertiesSingleSchema(String bootstrapServers, String schemaRegistryUrl) {
        Properties props = new Properties(4);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        props.put("schema.registry.url", schemaRegistryUrl);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put("value.subject.name.strategy", TopicNameStrategy.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        return props;
    }

    public static Properties getPropertiesMultiSchema(String bootstrapServers, String schemaRegistryUrl) {
        Properties props = new Properties(4);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        props.put("schema.registry.url", schemaRegistryUrl);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put("value.subject.name.strategy", TopicRecordNameStrategy.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        return props;
    }
}
