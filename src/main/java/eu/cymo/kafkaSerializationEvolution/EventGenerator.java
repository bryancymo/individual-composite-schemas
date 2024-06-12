package eu.cymo.kafkaSerializationEvolution;

import eu.cymo.kafkaSerializationEvolution.event.individual.OrderPaid;
import eu.cymo.kafkaSerializationEvolution.order.composite.OrderEvent;
import eu.cymo.kafkaSerializationEvolution.order.event.OrderConfirmed;
import eu.cymo.kafkaSerializationEvolution.order.event.OrderDelivered;
import eu.cymo.kafkaSerializationEvolution.order.event.OrderShipped;
import eu.cymo.kafkaSerializationEvolution.order.id.Order;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;

public class EventGenerator {

    static class Proto {
        public static Order randomOrder() {
            return Order.newBuilder()
                    .setId(RandomStringUtils.randomAlphanumeric(20))
//                    .setCategory("Random")
                    .build();
        }

        static class Composite {
            public static OrderEvent paidEventFrom(Order order){
                return OrderEvent.newBuilder()
                        .setOrder(order)
//                    .setPublisher("Me")
                        .setTimestamp(Instant.now().getEpochSecond())
                        .build();
            }
            public static OrderEvent confirmedEventFrom(Order order){
                return OrderEvent.newBuilder()
                        .setOrder(order)
//                    .setPublisher("Me")
                        .setTimestamp(Instant.now().getEpochSecond())
                        .setConfirmed(OrderConfirmed.newBuilder())
                        .build();
            }
            public static OrderEvent shippedEventFrom(Order order, String deliveryPartner){
                return OrderEvent.newBuilder()
                        .setOrder(order)
//                    .setPublisher("Me")
                        .setTimestamp(Instant.now().getEpochSecond())
                        .setShipped(OrderShipped.newBuilder()
                                .setDeliveryPartner(deliveryPartner))
                        .build();
            }

            public static OrderEvent deliveredEventFrom(Order order, String deliveryPartner){
                return OrderEvent.newBuilder()
                        .setOrder(order)
//                    .setPublisher("Me")
                        .setTimestamp(Instant.now().getEpochSecond())
                        .setDelivered(OrderDelivered.newBuilder()
                                .setDeliveryPartner(deliveryPartner))
                        .build();
            }
        }
        static class Individual {
            public static eu.cymo.kafkaSerializationEvolution.order.event.sd.OrderPaid paidEventFrom(Order order){
                return eu.cymo.kafkaSerializationEvolution.order.event.sd.OrderPaid.newBuilder()
                        .setOrder(order)
                        .setTimestamp(Instant.now().getEpochSecond())
                        .build();
            }
            public static eu.cymo.kafkaSerializationEvolution.order.event.sd.OrderConfirmed confirmedEventFrom(Order order){
                return eu.cymo.kafkaSerializationEvolution.order.event.sd.OrderConfirmed.newBuilder()
                        .setOrder(order)
                        .setTimestamp(Instant.now().getEpochSecond())
                        .build();
            }
            public static eu.cymo.kafkaSerializationEvolution.order.event.sd.OrderShipped shippedEventFrom(Order order, String deliveryPartner){
                return eu.cymo.kafkaSerializationEvolution.order.event.sd.OrderShipped.newBuilder()
                        .setOrder(order)
                        .setTimestamp(Instant.now().getEpochSecond())
                        .setDeliveryPartner(deliveryPartner)
                        .build();
            }
            public static eu.cymo.kafkaSerializationEvolution.order.event.sd.OrderDelivered deliveredEventFrom(Order order, String deliveryPartner){
                return eu.cymo.kafkaSerializationEvolution.order.event.sd.OrderDelivered.newBuilder()
                        .setOrder(order)
                        .setTimestamp(Instant.now().getEpochSecond())
                        .setDeliveryPartner(deliveryPartner)
                        .build();
            }
        }

    }

    static class Avro {
        public static eu.cymo.kafkaSerializationEvolution.id.Order randomOrder() {
            return eu.cymo.kafkaSerializationEvolution.id.Order.newBuilder()
                    .setId(RandomStringUtils.randomAlphanumeric(20))
//                    .setType("test")
                    .build();
        }

        static class Composite {
            public static eu.cymo.kafkaSerializationEvolution.composite.OrderEvent paidEventFrom(eu.cymo.kafkaSerializationEvolution.id.Order order){
                return eu.cymo.kafkaSerializationEvolution.composite.OrderEvent.newBuilder()
                        .setOrder(order)
                        .setTimestamp(Instant.now().getEpochSecond())
                        .setEvent(eu.cymo.kafkaSerializationEvolution.event.OrderPaid.newBuilder()
                                .setBroker("Worldline")
                                .build())
                        .build();
            }

            public static eu.cymo.kafkaSerializationEvolution.composite.OrderEvent confirmedEventFrom(eu.cymo.kafkaSerializationEvolution.id.Order order){
                return eu.cymo.kafkaSerializationEvolution.composite.OrderEvent.newBuilder()
                        .setOrder(order)
                        .setTimestamp(Instant.now().getEpochSecond())
                        .setEvent(eu.cymo.kafkaSerializationEvolution.event.OrderConfirmed.newBuilder()
//                            .setSalesman("Me")
                                .build())
                        .build();
            }
            public static eu.cymo.kafkaSerializationEvolution.composite.OrderEvent shippedEventFrom(eu.cymo.kafkaSerializationEvolution.id.Order order, String deliveryPartner){
                return eu.cymo.kafkaSerializationEvolution.composite.OrderEvent.newBuilder()
                        .setOrder(order)
                        .setTimestamp(Instant.now().getEpochSecond())
                        .setEvent(eu.cymo.kafkaSerializationEvolution.event.OrderShipped.newBuilder()
                                .setDeliveryPartner(deliveryPartner).build())
                        .build();
            }
            public static eu.cymo.kafkaSerializationEvolution.composite.OrderEvent deliveredEventFrom(eu.cymo.kafkaSerializationEvolution.id.Order order, String deliveryPartner){
                return eu.cymo.kafkaSerializationEvolution.composite.OrderEvent.newBuilder()
                        .setOrder(order)
                        .setTimestamp(Instant.now().getEpochSecond())
                        .setEvent(eu.cymo.kafkaSerializationEvolution.event.OrderDelivered.newBuilder()
                                .setDeliveryPartner(deliveryPartner).build())
                        .build();
            }
        }

        static class Individual {
            public static OrderPaid paidEventFrom(eu.cymo.kafkaSerializationEvolution.id.Order order){
                return OrderPaid.newBuilder()
                        .setOrder(order)
                        .setTimestamp(Instant.now().getEpochSecond())
                        .build();
            }
            public static eu.cymo.kafkaSerializationEvolution.event.individual.OrderConfirmed confirmedEventFrom(eu.cymo.kafkaSerializationEvolution.id.Order order){
                return eu.cymo.kafkaSerializationEvolution.event.individual.OrderConfirmed.newBuilder()
                        .setOrder(order)
                        .setTimestamp(Instant.now().getEpochSecond())
                        .build();
            }
            public static eu.cymo.kafkaSerializationEvolution.event.individual.OrderShipped shippedEventFrom(eu.cymo.kafkaSerializationEvolution.id.Order order, String deliveryPartner){
                return eu.cymo.kafkaSerializationEvolution.event.individual.OrderShipped.newBuilder()
                        .setOrder(order)
                        .setTimestamp(Instant.now().getEpochSecond())
                        .setDeliveryPartner(deliveryPartner)
                        .build();
            }
            public static eu.cymo.kafkaSerializationEvolution.event.individual.OrderDelivered deliveredEventFrom(eu.cymo.kafkaSerializationEvolution.id.Order order, String deliveryPartner){
                return eu.cymo.kafkaSerializationEvolution.event.individual.OrderDelivered.newBuilder()
                        .setOrder(order)
                        .setTimestamp(Instant.now().getEpochSecond())
                        .setDeliveryPartner(deliveryPartner)
                        .build();
            }
        }
    }

}
