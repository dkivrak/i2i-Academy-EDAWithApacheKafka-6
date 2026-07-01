package com.i2i.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;

public class OrderProducer {
    private static final String TOPIC_NAME = "java-object-topic";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());

        ObjectMapper objectMapper = new ObjectMapper();

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
            OrderEvent orderEvent = new OrderEvent(
                    UUID.randomUUID().toString(),
                    "Devrim Mert Kıvrak",
                    "Kafka Training Package",
                    1,
                    499.99
            );

            String orderJson = objectMapper.writeValueAsString(orderEvent);

            ProducerRecord<String, String> record = new ProducerRecord<>(
                    TOPIC_NAME,
                    orderEvent.getOrderId(),
                    orderJson
            );

            RecordMetadata metadata = producer.send(record).get();

            System.out.println("Order event sent successfully.");
            System.out.println("Topic: " + metadata.topic());
            System.out.println("Partition: " + metadata.partition());
            System.out.println("Offset: " + metadata.offset());
            System.out.println("Message: " + orderJson);
        } catch (Exception e) {
            System.err.println("Failed to send order event.");
            e.printStackTrace();
        }
    }
}