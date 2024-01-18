package org.example;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

public class word_count_producer {
    public static void main(String[] args) {
        // Kafka producer configuration
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "kafka.streams.svc.cluster.local:9092"); // Change this to your Kafka bootstrap servers
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Create Kafka producer
        Producer<String, String> producer = new KafkaProducer<>(properties);

        // Kafka topic to produce messages to
        String topic = "word-count-input-topic"; // Change this to your desired topic name

        try {
            // Infinite loop for continuous message production
            int i = 0;
            while (true) {
                String key = "key" + i;
                String message = "message " + i;

                // Sending a message to Kafka topic
                producer.send(new ProducerRecord<>(topic, key, message), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        if (exception == null) {
                            System.out.println("Message sent successfully! Key: " + key + ", Value: " + message + ", Offset: " + metadata.offset());
                        } else {
                            exception.printStackTrace();
                        }
                    }
                });

                // Introduce a small delay between messages
                Thread.sleep(500);

                // Increment the counter outside the Callback implementation
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the producer (this code may not be reached due to the infinite loop)
            producer.close();
        }
    }
}
