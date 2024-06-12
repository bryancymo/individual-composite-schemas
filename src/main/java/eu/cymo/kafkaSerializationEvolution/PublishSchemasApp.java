package eu.cymo.kafkaSerializationEvolution;

import com.google.protobuf.Message;
import eu.cymo.kafkaSerializationEvolution.order.composite.OrderEvent;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.time.Duration;
import java.util.List;

public class PublishSchemasApp {

    public static final String PROTO_SS_ORDER_EVENTS_TOPIC = "proto.singleSchema.order.events";
    public static final String PROTO_MS_ORDER_EVENTS_TOPIC = "proto.multiSchema.order.events";
    public static final String AVRO_SS_ORDER_EVENTS_TOPIC = "avro.singleSchema.order.events";
    public static final String AVRO_MS_ORDER_EVENTS_TOPIC = "avro.multiSchema.order.events";


    public static void main(String[] args){
        var app = new PublishSchemasApp();

        app.publishSingleSchemaProtoEvents();
//        consumeSingleSchemaProtoEvents();
        app.publishSingleSchemaAvroEvents();
//        consumeSingleSchemaAvroEvents();
        app.publishMultiSchemaProtoEvents();
        app.publishMultiSchemaAvroEvents();
    }

    private void publishSingleSchemaProtoEvents(){
        KafkaProducer<String, OrderEvent> producer = new KafkaProducer<>(PublishProto.getPropertiesSingleSchema("localhost:9092", "http://localhost:8081"));
        var order = EventGenerator.Proto.randomOrder();

        PublishProto.publishEvent(producer,PROTO_SS_ORDER_EVENTS_TOPIC, order.getId(), EventGenerator.Proto.Composite.confirmedEventFrom(order));
        PublishProto.publishEvent(producer,PROTO_SS_ORDER_EVENTS_TOPIC, order.getId(), EventGenerator.Proto.Composite.paidEventFrom(order));
        PublishProto.publishEvent(producer,PROTO_SS_ORDER_EVENTS_TOPIC, order.getId(), EventGenerator.Proto.Composite.shippedEventFrom(order, "DP1"));
        PublishProto.publishEvent(producer,PROTO_SS_ORDER_EVENTS_TOPIC, order.getId(), EventGenerator.Proto.Composite.deliveredEventFrom(order, "DP1"));
    }

    private static void consumeSingleSchemaProtoEvents(){
        KafkaConsumer<String, OrderEvent> consumer = new KafkaConsumer<>(ConsumeProto.getPropertiesSingleSchema("localhost:9092", "http://localhost:8081"));
        consumer.subscribe(List.of(PROTO_SS_ORDER_EVENTS_TOPIC));
        consumer.poll(Duration.ofSeconds(2)).forEach(rec -> {
            System.out.printf("----------------%nOffset: [%s]%nEvent: [%s]%nKey: [%s]%nValue: [%s]%n----------------%n",
                    rec.offset(),
                    rec.value().getEventCase(),
                    rec.key(),
                    rec.value().toString());
        });
    }

    private static void consumeSingleSchemaAvroEvents(){
        try{
            KafkaConsumer<String, eu.cymo.kafkaSerializationEvolution.composite.OrderEvent> consumer = new KafkaConsumer<>(ConsumeAvro.getPropertiesSingleSchema("localhost:9092", "http://localhost:8081"));
        consumer.subscribe(List.of(AVRO_SS_ORDER_EVENTS_TOPIC));

           consumer.poll(Duration.ofSeconds(2))
           .forEach(rec -> {
               System.out.printf("----------------%nOffset: [%s]%nEvent: [%s]%nKey: [%s]%nValue: [%s]%n----------------%n",
                       rec.offset(),
                       rec.value().getEvent().getClass(),
                       rec.key(),
                       rec.value().toString());
           });
       } catch (Exception e){
           e.printStackTrace();
       }
    }
    private void publishSingleSchemaAvroEvents(){

        KafkaProducer<String, eu.cymo.kafkaSerializationEvolution.composite.OrderEvent> producer = new KafkaProducer<>(PublishAvro.getPropertiesSingleSchema("localhost:9092", "http://localhost:8081"));
        var order = EventGenerator.Avro.randomOrder();

        PublishAvro.publishEvent(producer,AVRO_SS_ORDER_EVENTS_TOPIC, order.getId(), EventGenerator.Avro.Composite.confirmedEventFrom(order));
        PublishAvro.publishEvent(producer,AVRO_SS_ORDER_EVENTS_TOPIC, order.getId(), EventGenerator.Avro.Composite.paidEventFrom(order));
        PublishAvro.publishEvent(producer,AVRO_SS_ORDER_EVENTS_TOPIC, order.getId(), EventGenerator.Avro.Composite.shippedEventFrom(order, "DP1"));
        PublishAvro.publishEvent(producer,AVRO_SS_ORDER_EVENTS_TOPIC, order.getId(), EventGenerator.Avro.Composite.deliveredEventFrom(order, "DP1"));
    }

    private void publishMultiSchemaProtoEvents(){
        KafkaProducer<String, Message> producer = new KafkaProducer<>(PublishProto.getPropertiesMultiSchema("localhost:9092", "http://localhost:8081"));
        var order = EventGenerator.Proto.randomOrder();

        PublishProto.publishEvent(producer,PROTO_MS_ORDER_EVENTS_TOPIC, order.getId(), EventGenerator.Proto.Individual.confirmedEventFrom(order));
        PublishProto.publishEvent(producer,PROTO_MS_ORDER_EVENTS_TOPIC, order.getId(), EventGenerator.Proto.Individual.paidEventFrom(order));
        PublishProto.publishEvent(producer,PROTO_MS_ORDER_EVENTS_TOPIC, order.getId(), EventGenerator.Proto.Individual.shippedEventFrom(order, "DP1"));
        PublishProto.publishEvent(producer,PROTO_MS_ORDER_EVENTS_TOPIC, order.getId(), EventGenerator.Proto.Individual.deliveredEventFrom(order, "DP1"));
    }
    private void publishMultiSchemaAvroEvents(){

        KafkaProducer<String, SpecificRecord> producer = new KafkaProducer<>(PublishAvro.getPropertiesMultiSchema("localhost:9092", "http://localhost:8081"));
        var order = EventGenerator.Avro.randomOrder();

        PublishAvro.publishEvent(producer,AVRO_MS_ORDER_EVENTS_TOPIC, order.getId(), EventGenerator.Avro.Individual.confirmedEventFrom(order));
        PublishAvro.publishEvent(producer,AVRO_MS_ORDER_EVENTS_TOPIC, order.getId(), EventGenerator.Avro.Individual.paidEventFrom(order));
        PublishAvro.publishEvent(producer,AVRO_MS_ORDER_EVENTS_TOPIC, order.getId(), EventGenerator.Avro.Individual.shippedEventFrom(order, "DP1"));
        PublishAvro.publishEvent(producer,AVRO_MS_ORDER_EVENTS_TOPIC, order.getId(), EventGenerator.Avro.Individual.deliveredEventFrom(order, "DP1"));
    }

}
