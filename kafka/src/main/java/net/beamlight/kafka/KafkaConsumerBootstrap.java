package net.beamlight.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by gaofeihang on 2017/10/10.
 */
public class KafkaConsumerBootstrap {

    private static final String TOPIC = "my-topic";

    public void start() {
        ExecutorService executorService = Executors.newCachedThreadPool();

        int threadNum = 4;
        final CountDownLatch latch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Properties props = new Properties();
                    props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
                    props.put("group.id", "test");
                    props.put("enable.auto.commit", "true");
                    props.put("auto.commit.interval.ms", "1000");
                    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
                    consumer.subscribe(Arrays.asList(TOPIC));

                    while (!Thread.currentThread().isInterrupted()) {
                        ConsumerRecords<String, String> records = consumer.poll(100);
                        for (ConsumerRecord<String, String> record : records)
                            System.out.printf("thread = %s, partition = %s, offset = %d, key = %s, value = %s%n", Thread.currentThread().getName(), record.partition(), record.offset(), record.key(), record.value());
                    }
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new KafkaConsumerBootstrap().start();
    }

}
