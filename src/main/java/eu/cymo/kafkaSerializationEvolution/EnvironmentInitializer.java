package eu.cymo.kafkaSerializationEvolution;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class EnvironmentInitializer {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        Admin admin = Admin.create(properties);

        var topics = TopicCollection.ofTopicNames(Arrays.asList(
                        PublishSchemasApp.PROTO_MS_ORDER_EVENTS_TOPIC,
                        PublishSchemasApp.PROTO_SS_ORDER_EVENTS_TOPIC,
                        PublishSchemasApp.AVRO_SS_ORDER_EVENTS_TOPIC,
                        PublishSchemasApp.AVRO_MS_ORDER_EVENTS_TOPIC
                ));

       admin.createTopics(topics.topicNames().stream()
                        .map(topic -> new NewTopic(topic, Optional.of(6), Optional.empty())).toList()
                ).all()
        .whenComplete((c,d) -> System.out.println("Recreation done"))
        .get();
    }
}
