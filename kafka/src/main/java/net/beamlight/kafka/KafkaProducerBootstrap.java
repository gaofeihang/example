package net.beamlight.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * Created by gaofeihang on 2017/10/10.
 */
public class KafkaProducerBootstrap {

    private static final String TOPIC = "my-topic";

    public void start() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 5);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        while (!Thread.currentThread().isInterrupted()) {
            for (int i = 0; i < 100; i++)
                producer.send(new ProducerRecord<String, String>(TOPIC, Integer.toString(i), Integer.toString(i)));
        }

        producer.close();
    }

    public static void main(String[] args) {
        new KafkaProducerBootstrap().start();
    }

}
