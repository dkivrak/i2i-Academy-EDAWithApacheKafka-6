package com.i2i.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class OrderConsumer {
    private static final String TOPIC_NAME = "java-object-topic";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", StringDeserializer.class.getName());
        properties.put("group.id", "order-consumer-group");
        properties.put("auto.offset.reset", "earliest");

        ObjectMapper objectMapper = new ObjectMapper();

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(Collections.singletonList(TOPIC_NAME));

            System.out.println("Consumer started. Waiting for order events...");

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

                for (ConsumerRecord<String, String> record : records) {
                    OrderEvent orderEvent = objectMapper.readValue(record.value(), OrderEvent.class);

                    System.out.println("------------------------------");
                    System.out.println("Order event received.");
                    System.out.println("Key: " + record.key());
                    System.out.println("Topic: " + record.topic());
                    System.out.println("Partition: " + record.partition());
                    System.out.println("Offset: " + record.offset());
                    System.out.println("Object: " + orderEvent);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to consume order event.");
            e.printStackTrace();
        }
    }
}