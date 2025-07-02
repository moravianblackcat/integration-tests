package cz.dan.integrationtests.kafka;

import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.time.temporal.ChronoUnit.SECONDS;

public class TestKafkaConsumer {

    private final Map<String, Integer> topics = new HashMap<>();

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TestKafkaConsumer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getNextMessagesFromTopicWithTimeout(int numberOfMessages, String topic,
                                                           int timeoutInSeconds) {
        Integer nextTopicOffset = topics.getOrDefault(topic, 0);

        List<T> result = IntStream.range(nextTopicOffset, nextTopicOffset + numberOfMessages)
                .mapToObj(offset ->
                        kafkaTemplate.receive(topic, 0, offset, Duration.of(timeoutInSeconds, SECONDS))
                                .value())
                .map(message -> (T) message)
                .toList();

        topics.put(topic, nextTopicOffset + numberOfMessages);

        return result;
    }

    @SuppressWarnings("unchecked")
    public <T> T getNextMessageFromTopicWithTimeout(String topic, int timeoutInSeconds) {
        Integer nextTopicOffset = topics.getOrDefault(topic, 0);

        topics.put(topic, nextTopicOffset + 1);

        return (T) kafkaTemplate.receive(topic, 0, nextTopicOffset, Duration.of(timeoutInSeconds, SECONDS)).value();

    }

}
