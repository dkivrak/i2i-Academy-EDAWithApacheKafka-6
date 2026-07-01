# i2i Academy - EDA With Apache kafka

This project was developed as part of the i2i Academy homework assignment: **EDA with Apache Kafka**.

The main goal of the project is to understand the basics of Event-Driven Architecture and gain hands-on experience with Apache Kafka by building a simple Java Producer and Consumer application.

## Project Overview

This project demonstrates a basic event-driven system using Apache Kafka.

The Producer application creates a custom Java object called `OrderEvent`, converts it into JSON format, and publishes it to a Kafka topic. The Consumer application listens to the same topic, reads the JSON message, converts it back into an `OrderEvent` object, and prints the result to the console.

## Technologies Used

- Java 17
- Apache Kafka
- Docker
- Docker Compose
- Maven
- Jackson Databind
- Kafka Clients

## Project Structure

```text
i2i-Academy-EDAWithApachekafka-6/
├── docker-compose.yml
├── producer/
│   ├── pom.xml
│   └── src/main/java/com/i2i/kafka/producer/
│       ├── OrderEvent.java
│       └── OrderProducer.java
├── consumer/
│   ├── pom.xml
│   └── src/main/java/com/i2i/kafka/consumer/
│       ├── OrderEvent.java
│       └── OrderConsumer.java
└── README.md
```

## Kafka Setup with Docker

Apache Kafka is started locally using Docker Compose.

To start Kafka:

```bash
docker compose up -d
```

To check if Kafka is running:

```bash
docker ps
```

Expected container:

```text
kafka-local
Kafka Topic
```

The project uses the following Kafka topic:

```text
java-object-topic
```

To create the topic manually:

```bash
docker exec -it kafka-local /opt/kafka/bin/kafka-topics.sh \
  --create \
  --topic java-object-topic \
  --bootstrap-server localhost:9092 \
  --partitions 1 \
  --replication-factor 1
```

To list topics:

```bash
docker exec -it kafka-local /opt/kafka/bin/kafka-topics.sh \
  --list \
  --bootstrap-server localhost:9092
Producer Application
```

The Producer application creates an OrderEvent object and sends it to the Kafka topic as a JSON message.

To run the Producer:

```bash
cd producer
mvn clean compile exec:java
```

Example output:

```text
Order event sent successfully.
Topic: java-object-topic
Partition: 0
Offset: 0
Message: {"orderId":"...","customerName":"Devrim Kivrak","productName":"Kafka Training Package","quantity":1,"totalPrice":499.99}
Consumer Application
```

The Consumer application listens to the Kafka topic, reads the message, converts it back into an OrderEvent object, and prints it to the console.

To run the Consumer:

```bash
cd consumer
mvn clean compile exec:java
```

Example output:

```text
Consumer started. Waiting for order events...

Order event received.
Key: ...
Topic: java-object-topic
Partition: 0
Offset: 0
Object: OrderEvent{orderId='...', customerName='Devrim Mert Kivrak', productName='Kafka Training Package', quantity=1, totalPrice=499.99}
Running the Project
```

First, start Kafka:

```bash
docker compose up -d
```

Then, create the Kafka topic:

```bash
docker exec -it kafka-local /opt/kafka/bin/kafka-topics.sh \
  --create \
  --topic java-object-topic \
  --bootstrap-server localhost:9092 \
  --partitions 1 \
  --replication-factor 1
```

Start the Consumer in one terminal:

```bash
cd consumer
mvn clean compile exec:java
```

Start the Producer in another terminal:

```bash
cd producer
mvn clean compile exec:java
```

The Consumer should receive and print the OrderEvent object sent by the Producer.


## Notes

-  Kafka runs locally on localhost:9092.
-  The project uses a single Kafka broker.
-  The message value is serialized as JSON.
-  The Producer and Consumer are implemented as separate Java Maven applications.
-  Docker is used only for running Apache Kafka.
